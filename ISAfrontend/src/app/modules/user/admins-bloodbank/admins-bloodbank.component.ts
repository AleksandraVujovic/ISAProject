import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { BloodBank } from 'src/app/model/blood-bank.model';
import { Admin } from 'src/app/model/Users/admin';
import { User } from 'src/app/model/Users/user';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-admins-bloodbank',
  templateUrl: './admins-bloodbank.component.html',
  styleUrls: ['./admins-bloodbank.component.css']
})
export class AdminsBloodbankComponent implements OnInit {

  public admin : Admin;
  public bloodBank : BloodBank;
  constructor(private adminService: AdminService, private router : Router) { }

  ngOnInit(): void {
    
    this.adminService.getAdminById(localStorage.getItem("loggedUserId")).subscribe(res =>{
      this.admin = res;
      this.bloodBank = res.bloodBank;
      console.log(this.admin);
      });
  }

  saveChanges(registrationForm: NgForm) :void {
    //TODO:
    console.log("sacuvali smo");
     
    this.admin.bloodBank = this.bloodBank;
    this.adminService.saveAdminChanges(this.admin).subscribe(res =>
    {console.log(res);
    }   
    );
    //console.log(this.admin);
    
  }


}
