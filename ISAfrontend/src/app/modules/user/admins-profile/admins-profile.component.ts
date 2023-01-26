import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Admin } from 'src/app/model/Users/admin';
import { AdminService } from '../services/admin.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-admins-profile',
  templateUrl: './admins-profile.component.html',
  styleUrls: ['./admins-profile.component.css']
})
export class AdminsProfileComponent implements OnInit {
  public admin: Admin;
  constructor(private adminService: AdminService, private router: Router,private toastr: ToastrService) { }

  ngOnInit(): void {
    this.adminService.getAdminById(localStorage.getItem("loggedUserId")).subscribe(res =>{
      this.admin = res;
      console.log(this.admin);
  })
  }

  saveChanges(registrationForm: NgForm) :void {
    this.adminService.saveAdminChanges(this.admin).subscribe(res =>
      {console.log(res);
      });
   
    //console.log(this.admin);
    
  }
  }
