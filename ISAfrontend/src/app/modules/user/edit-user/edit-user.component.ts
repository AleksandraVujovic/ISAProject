import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { HeadAdmin } from 'src/app/model/Users/head-admin';
import { CustomerService } from '../services/customer-service.service';
import { User } from './model/user';
import { Gender } from './model/gender';
import { Role } from './model/role';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  public genders = Object.values(Gender);
  public roles = Object.values(Role);
  public user : any;
  public errorMessage: Error = new Error;
  public errorMap: Map<string, string> = new Map();
  private sub: any;

  constructor(private customerService: CustomerService, private router: Router,private toastr: ToastrService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    var userId = localStorage.getItem('loggedUserId') ?? '-1';
      this.customerService.getCustomerByID(userId).subscribe(res => {
        this.user = res
      }, (error) => {
        console.log(error)
        this.errorMessage = error;
        this.toastError();
      })
  }

  public editCustomer(){
    this.customerService.updateCustomer(this.user).subscribe( res => 
      {
        this.toastr.success("Succsesfuly changed user")
      }, (error) => {
        console.log(error)
        this.errorMessage = error;
        this.toastError();
      });
  }

  private toastError() {
    if (String(this.errorMessage).includes('406')){
      var error = localStorage.getItem('errormap')!;
      this.errorMap = new Map(JSON.parse(error));

      for (let entry of this.errorMap.entries()) {
        this.toastr.error('Validation error: ' + entry[1]);
      }
    }
    else{
      this.toastr.error(this.errorMessage.message);
    }
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
