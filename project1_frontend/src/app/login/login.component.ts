import { Component, ViewChild, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { NgForm, FormsModule } from '@angular/forms';
import { TokenService } from '../service/token.service';
import { RoleService } from '../service/role.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phone: string = '';
  password: string = '';
  roles: { id: number; name: string }[] = [];
  selectedRole: { id: number; name: string } | undefined;

  constructor(
    private router: Router,
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService
  ){}

  onPhoneChange(){
    console.log('Phone typed: '+ this.phone)
  }

  ngOnInit() {
    debugger
    this.roleService.getRoles().subscribe({
      next: (response: { id: number; name: string }[]) =>{
        debugger
        console.log('Roles received:', response);
        this.roles = response;
        this.selectedRole = response.length > 0 ? response[0] : undefined;
      },
      error: (error: any) => {
        debugger
        console.error('Error: ', error);
      }
    });
  }

  login(){
    // alert("You pressed login");
    debugger
    const loginData = {
      "phonenumber": this.phone,
      "password": this.password,
      "role": this.selectedRole?.id
    }
    this.userService.login(loginData).subscribe({
      next: (response: any) => {
        debugger
        const {token} = response;
        this.tokenService.setToken(token);
        // this.router.navigate(['/login']);
      },
      complete: () => {
        this.router.navigate(['/']);
        debugger
      },
      error: (error: any) => {
        alert('Can not login, error: ' + error.error);
        debugger
      }
    });
  }
}
