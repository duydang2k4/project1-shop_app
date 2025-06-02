import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private CART_KEY = 'cart'; 

  constructor(private http: HttpClient) {}

  addToCart(product: any): void {
    const cart = this.getCartItems(); 
    const existingProduct = cart.find((item) => item.id === product.id);

    if (existingProduct) {
      existingProduct.quantity += 1; 
    } else {
      cart.push({ ...product, quantity: 1 }); 
    }

    this.saveCartItems(cart);
  }

  getCartItems(): any[] {
    const storedCart = localStorage.getItem(this.CART_KEY);
    return storedCart ? JSON.parse(storedCart) : [];
  }


  private saveCartItems(cart: any[]): void {
    localStorage.setItem(this.CART_KEY, JSON.stringify(cart));
  }


  createOrder(order: any): Observable<any> {
    return this.http.post('http://localhost:8088/api/v1/orders', order);
  }


  createOrderDetail(orderDetail: any): Observable<any> {
    return this.http.post('http://localhost:8088/api/v1/order_details', orderDetail);
  }


  clearCart(): void {
    localStorage.removeItem(this.CART_KEY); 
  }
}
