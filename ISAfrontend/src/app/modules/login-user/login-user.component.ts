import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from '../user/services/login.service';
import { LoginUser } from '../user/model/login-user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-user',
  templateUrl: './login-user.component.html',
  styleUrls: ['./login-user.component.css'],
})
export class LoginUserComponent implements OnInit {
  public loginUser: LoginUser = new LoginUser();
  public token: string = '';
  public role: string = '';
  public id: string = '';

  constructor(
    private toastr: ToastrService,
    private loginService: LoginService,
    private router: Router
  ) {}

  ngOnInit(): void {  }

  Login() {
    this.loginService.login(this.loginUser).subscribe(
      (response: any) => {
          this.token = response;
          localStorage.setItem('token', this.token);
          console.log(this.token);
          let decodedJWT = JSON.parse(window.atob(this.token.split('.')[1]));
          this.role = decodedJWT.role.authority;
          localStorage.setItem('loggedUserRole', this.role);
          console.log(this.role);
          this.id = decodedJWT.id.authority;
          localStorage.setItem('loggedUserId', this.id);
          console.log(this.id);

          localStorage.setItem('isPasswordChanged', decodedJWT.isPasswordChanged);
          if(decodedJWT.isPasswordChanged == "false"){
            localStorage.setItem("HeadAdminUsername", this.loginUser.userName);
            localStorage.setItem("HeadAdminPassword", this.loginUser.password);
            this.toastr.success('Successfully logged in');
            window.location.href = '/password-change';
            return;
          }

          this.toastr.success('Successfully logged in');
          window.location.href = '/';
      },
      (error) => {
        console.log(error.message);
      }
    );
  }
}
