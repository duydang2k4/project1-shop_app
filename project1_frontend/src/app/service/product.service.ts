import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../model/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8088/api/v1/products';

  constructor(private http: HttpClient) {}

  getProducts(page: number, limit: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}?page=${page}&limit=${limit}`);
  }
}