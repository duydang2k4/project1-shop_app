import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CartService } from '../service/cart.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{
  products: any[] = [];
  categories: any[] = [];
  keyword: string = '';
  categoryId: number | null = null;
  apiUrl = 'http://localhost:8088/api/v1/products';


  constructor(private http: HttpClient, private cartService: CartService, private router: Router) {}

  ngOnInit(): void {
    this.getCategories();
    this.getProducts();
  }

  getProducts(): void {
    debugger
    const params: any = {
      page: 0,
      limit: 10,
    };
    if (this.keyword) {
      params.keyword = this.keyword;
    }
    if (this.categoryId) {
      params.category_id = this.categoryId;
    }

    this.http.get<any[]>(this.apiUrl, { params }).subscribe({
      next: (response) => {
        debugger
        this.products = response;
      },
      error: (err) => {
        console.error('Error fetching products:', err);
      },
    });
  }

  onSearchInput(event: Event): void {
    const target = event.target as HTMLInputElement;
    this.keyword = target.value;
    this.getProducts(); 
  }

  onCategoryChange(event: Event): void {
    const target = event.target as HTMLSelectElement;
    this.categoryId = target.value ? parseInt(target.value, 10) : null;
    this.getProducts(); 
  }
  
  getCategories(): void {
    const params: any = {
      page: 0,
      limit: 10,
    };
    this.http.get<any[]>('http://localhost:8088/api/v1/categories', { params}).subscribe({
      next: (response) => {
        debugger
        this.categories = [{ id: null, name: 'Tất cả' }, ...response];
      },
      error: (err) => {
        console.error('Error fetching categories:', err);
      },
    });
  }

  addToCart(product: any): void {
    this.cartService.addToCart(product);
    alert('Sản phẩm đã được thêm vào giỏ hàng!');
  }

  addToCart2(product: any): void {
    this.cartService.addToCart(product);
    this.router.navigate(['/cart']);
  }
}
