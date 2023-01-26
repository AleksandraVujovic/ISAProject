import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BloodBankService } from '../services/blood-bank.service';
import { NgForm } from '@angular/forms';
import { BloodBank } from '../../../model/blood-bank.model';

@Component({
  selector: 'app-register-banks',
  templateUrl: './register-banks.component.html',
  styleUrls: ['./register-banks.component.css'],
})
export class RegisterBanksComponent implements OnInit {
  public bloodBank: BloodBank = new BloodBank();
  public errorMessage: Error = new Error();
  public errorMap: Map<string, string> = new Map();

  constructor(private bloodBankService: BloodBankService, private router: Router,private toastr: ToastrService) { }

  ngOnInit(): void {
    if(localStorage.getItem('ForbiddenAccessToHeadAdmin') == 'true')
      this.router.navigate(['/password-change']);
  }

  public registerBloodBank(){

    this.bloodBankService.registerBloodBank(this.bloodBank).subscribe( res => 
      {
        this.toastr.success("You successfully created a blood bank!");
        this.router.navigate(['/']);
      }, (error) => {
        console.log(error)
        this.errorMessage = error;
        this.toastError();
      }
    );
  }

  saveBank(registrationForm: NgForm): void {
    if (registrationForm.dirty && registrationForm.valid) {
      this.registerBloodBank();
    }
  }

  private toastError() {
    if (String(this.errorMessage).includes('406')) {
      var error = localStorage.getItem('errormap')!;
      this.errorMap = new Map(JSON.parse(error));

      for (let entry of this.errorMap.entries()) {
        this.toastr.error('Validation error: ' + entry[1]);
      }
    } else {
      this.toastr.error(this.errorMessage.message);
    }
  }
}
