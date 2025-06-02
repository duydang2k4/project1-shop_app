import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from '../service/token.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(private router: Router, private tokenService: TokenService){}
  
  get isLoggedIn(): boolean {
    return this.tokenService.getToken() !== null;
  }
  
  goToLogin() {
    this.router.navigate(['/login']);
  }


  logout():void {
    this.tokenService.removeToken();
    this.router.navigate(['/']);
  }

  isAuthenticated(): boolean {
    return this.isLoggedIn;
  }
}
