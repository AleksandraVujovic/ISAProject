import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ComplaintService } from '../services/complaint.service';
import { NgForm } from '@angular/forms';
import { Complaint } from '../model/complaint.model';

@Component({
  selector: 'app-admin-view-complaints',
  templateUrl: './admin-view-complaints.component.html',
  styleUrls: ['./admin-view-complaints.component.css']
})
export class AdminViewComplaintsComponent implements OnInit {

  public complaints: Complaint[] = [];
  public errorMessage: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 5;
  noComplaints: boolean = false;

  constructor(
    private complaintService: ComplaintService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.getUnansweredComplaints(0, this.tableSize);    
  }

  public getUnansweredComplaints(page: number = 0, size: number = 5): void{
    this.complaintService.getUnansweredComplaints(page, size).subscribe(
      (res) => {
        this.complaints = res;
        if(this.complaints.length == 0)
          this.noComplaints = true;
        this.getNumberOfUnansweredComplaintss();
      },
      (error) => {
        this.errorMessage = error;
      }
    );
  }
  public getNumberOfUnansweredComplaintss(): void{
    this.complaintService.getNumberOfUnansweredComplaintss().subscribe(
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
  onTableDataChange(event: any) {
    this.page = event;
    this.getUnansweredComplaints(this.page-1, this.tableSize);
  }

  public answerComplaint(selectedComplaint: Complaint){   
    this.router.navigate(['/answer-complaint', selectedComplaint.id]);
    
  }

}
