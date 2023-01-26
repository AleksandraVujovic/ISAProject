import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Customer } from 'src/app/model/Users/customer';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-view-customers',
  templateUrl: './view-customers.component.html',
  styleUrls: ['./view-customers.component.css']
})

export class ViewCustomersComponent implements OnInit {

  public customers: Customer[] = [];
  public search: string = '';
  public errorMessage: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 3;

  constructor(
    private customerService : CustomerService,
    private router: Router,
    private toastr: ToastrService
    ) { }

  ngOnInit(): void {
    this.getCustomers(0, this.tableSize, '');
  }

  public getCustomers(page: number =0 , size: number =3,searchTerm : string = ''): void{
    this.customerService.getCustemerWithSearch({ page, size, search: searchTerm }).subscribe(
      (res) => {

        this.customers = res;
        console.log("Dobavljamo");
        this.getNumberOfCustomers(this.search);
      },
      (error) => {
        this.errorMessage = error;
        this.customers = [];
        this.count = 0;
        this.onTableDataChange(1);

      }
    );
  }
  public navigateToProfile(customer: Customer){
    console.log(customer);
    this.customerService.customer = customer;
    this.router.navigate(['view-customers-appointments/']);
  }; 

  public getNumberOfCustomers(searchTerm: string): void{
    this.customerService.getNumberOfCustomersWithSearch(searchTerm).subscribe(
      (res) => {
        this.count = res;
        if(this.page -1 > (this.count/this.tableSize)){
          this.onTableDataChange(1);
        }
      },
      (error) => {
        this.errorMessage = error;
      }
    )
  }

  public searchCustomer(name: string): void {
    this.getCustomers(this.page -1, this.tableSize, this.search);
  }

  onTableDataChange(event: any) {
    this.page = event;
    this.getCustomers(this.page-1, this.tableSize ,this.search);
  }
}
