import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AdminService } from '../services/admin.service';
import { FormGroup, NgForm, FormControl } from '@angular/forms';
import { RegistrationAdmin } from '../model/registration-admin.model';
import { BloodBank } from '../../../model/blood-bank.model';
import { BloodBankService } from '../../blood-bank/services/blood-bank.service';
import { HeadAdminService } from '../services/head-admin.service';
import { HeadAdmin } from '../model/head-admin.model';


@Component({
  selector: 'app-register-head-admin',
  templateUrl: './register-head-admin.component.html',
  styleUrls: ['./register-head-admin.component.css']
})
export class RegisterHeadAdminComponent implements OnInit {
  public admin: HeadAdmin = new HeadAdmin();
  public errorMessage: Error = new Error;
  public errorMap: Map<string, string> = new Map();
  private pastDate: Date = new Date(1900,1,1);
  public todaysDate: Date = new Date();
  constructor(private headAdminService: HeadAdminService, private router: Router,private toastr: ToastrService) { }

  ngOnInit(): void {
    if(localStorage.getItem('ForbiddenAccessToHeadAdmin') == 'true')
      this.router.navigate(['/password-change']);
  }

  public registerHeadAdmin(){

    this.headAdminService.registerHeadAdmin(this.admin).subscribe( res => 
    {
      if(res == true)
        this.toastr.success("You successfully created a head admin!");
      else
        this.toastr.info("Something went wrong, please try again later!");
      this.router.navigate(["/"]);
    }, (error) => {
      console.log(error)
      this.errorMessage = error;
      this.toastError();
    });

  }

  saveHeadAdmin(registrationForm: NgForm): void {
    if (registrationForm.dirty && registrationForm.valid) {
      this.registerHeadAdmin();
    }
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
}
