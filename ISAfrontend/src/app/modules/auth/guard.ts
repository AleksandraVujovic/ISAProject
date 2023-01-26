import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from '../user/services/login.service';

@Injectable({
  providedIn: 'root',
})
export class Guard implements CanActivate {
  constructor(private router: Router, private loginService: LoginService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    if (localStorage.getItem('token') != null) {
      let token = localStorage.getItem('token');
      let isPasswordChanged;
      if (token != null) {
        let decodedJWT = JSON.parse(window.atob(token.split('.')[1]));
        isPasswordChanged = decodedJWT.isPasswordChanged;
      }

      let roles = route.data['permittedRoles'] as Array<string>;
      if (roles) {
        if (this.loginService.roleMatch(roles)){ 
          if (isPasswordChanged == 'false') {
            this.router.navigate(['/password-change']);
            return false;
          }
          return true
        }
        else {
          this.router.navigate(['/forbidden']);
          return false;
        }
      }
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
