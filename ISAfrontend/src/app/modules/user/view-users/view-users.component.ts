import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MatTableDataSource } from '@angular/material/table';
import { UserService } from '../services/user.service';
import { ViewUser } from '../model/view-user.model';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css']
})
export class ViewUsersComponent implements OnInit {

  public users: ViewUser[] = [];
  public search: string = '';
  public errorMessage: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 5;

  constructor(
    private userService: UserService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    if(localStorage.getItem('ForbiddenAccessToHeadAdmin') == 'true')
      this.router.navigate(['/password-change']);
    this.getUsers(0, this.tableSize, '');
    
  }

  public getUsers(page: number = 0, size: number = 2,searchTerm : string = ''): void{
    this.userService.getUsersWithSearch(page, size, searchTerm).subscribe(
      (res) => {
        this.users = res;
        this.getNumberOfUsers(this.search);
        console.log(res);
        
      },
      (error) => {
        this.errorMessage = error;
      }
    );
  }
  public getNumberOfUsers(searchTerm: string = ''): void{
    this.userService.getNumberOfUsersWithSearch(searchTerm).subscribe(
      (res) => {
        this.count = res;
        if(this.page -1 > (this.count/this.tableSize)){
          this.onTableDataChange(1);
        }
      },
      (error) => {
        this.errorMessage = error;
      }
    );
  }
  public searchUsers(name: string): void {
    this.getUsers(this.page -1, this.tableSize, this.search);
  }
  onTableDataChange(event: any) {
    this.page = event;
    this.getUsers(this.page-1, this.tableSize ,this.search);
  }
  
}
