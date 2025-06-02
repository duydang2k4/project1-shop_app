import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
    private apiGetRoles = "http://localhost:8088/api/v1/roles"
    constructor(private http: HttpClient){}

    getRoles(): Observable<{ id: number; name: string }[]> {
      return this.http.get<{ id: number; name: string }[]>(this.apiGetRoles);
  }
}