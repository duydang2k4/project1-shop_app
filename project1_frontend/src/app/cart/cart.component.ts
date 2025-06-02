import { Component, OnInit } from '@angular/core';
import { CartService } from '../service/cart.service';
import { TokenService } from '../service/token.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  cartItems: any[] = [];
  orderInfo = {
    user_id: 0,
    fullname: '',
    email: '',
    phone_number: '',
    payment_method: '',
  };

  constructor(private cartService: CartService, private tokenService: TokenService) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
    this.orderInfo.user_id = this.getUserIdFromToken(); 
  }

  getUserIdFromToken(): number {
    const token = this.tokenService.getToken();
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.userId; 
    }
    return 0;
  }

  getTotalPrice(): number {
    return this.cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
  }

  checkout(): void {
    if (!this.orderInfo.user_id) {
      alert('Không thể xác định người dùng. Vui lòng đăng nhập lại.');
      return;
    }

    const order = {
      ...this.orderInfo,
      total_money: this.getTotalPrice(),
    };

    this.cartService.createOrder(order).subscribe({
      next: (orderResponse) => {
        debugger
        const orderId = orderResponse.id;
        this.cartItems.forEach((item) => {
          const orderDetail = {
            order_id: orderId,
            product_id: item.id,
            price: item.price,
            number_of_products: item.quantity,
            total_money: item.price * item.quantity
          };
          this.cartService.createOrderDetail(orderDetail).subscribe();
        });

        alert('Đặt hàng thành công!');
        this.cartService.clearCart();
        this.cartItems = [];
      },
      error: (error) => {
        console.error('Đặt hàng thất bại:', error);
        alert('Đặt hàng thất bại!');
      },
    });
  }

  clearCart(): void {
    if (confirm('Bạn có chắc chắn muốn xóa toàn bộ giỏ hàng?')) {
      this.cartService.clearCart();
      this.cartItems = [];
      alert('Giỏ hàng đã được xóa.');
    }
  }
}
