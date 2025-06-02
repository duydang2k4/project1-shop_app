import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  phone: string;
  password: string;
  retypePassword: string;
  fullname: string;
  address: string;
  dateOfBirth: Date;

  constructor( private router: Router, private userService: UserService){
    this.phone = '';
    this.password = '';
    this.retypePassword = '';
    this.fullname = '';
    this.address = '';
    this.dateOfBirth = new Date();
  }

  onPhoneChange(){
    console.log('Phone typed: '+ this.phone)
  }

  register(){
    // alert("You pressed register");
    debugger
    const registerData = {
      "fullname": this.fullname,
      "phonenumber": this.phone,
      "address": this.address,
      "password": this.password,
      "retypePassword": this.retypePassword,
      "date_of_birth": this.dateOfBirth,
      "role_id": 1
    }
    this.userService.register(registerData).subscribe({
      next: (response: any) => {
        this.router.navigate(['/login']);
      },
      complete: () => {
        debugger
      },
      error: (error: any) => {
        alert('Can not register, error: ' + error.error);
        debugger
      }
    });
  }

  checkPasswordMatch(){
    if(this.password !== this.retypePassword){
      this.registerForm.form.controls['retypePassword'].setErrors({'passwordMismatch': true});
    }
    else{
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }
}
