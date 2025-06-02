import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrlRegister = "http://localhost:8088/api/v1/users/register";
  private apiUrlLogin = "http://localhost:8088/api/v1/users/login";

  constructor(private http: HttpClient) { }

  register(registerData: any):Observable<any>{
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(this.apiUrlRegister, registerData, {headers});
  }

  login(loginData: any):Observable<any>{
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(this.apiUrlLogin, loginData, {headers});
  }


}
