import { Component, OnInit } from '@angular/core';
import { Router,ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { ComplaintService } from '../services/complaint.service';
import { NgForm } from '@angular/forms';
import { Complaint } from '../model/complaint.model';

@Component({
  selector: 'app-answer-complaint',
  templateUrl: './answer-complaint.component.html',
  styleUrls: ['./answer-complaint.component.css']
})
export class AnswerComplaintComponent implements OnInit {

  public complaint: Complaint = new Complaint();
  public errorMessage: Error = new Error();
  public errorMap: Map<string, string> = new Map();
  private routeSub: Subscription;

  constructor(private complaintService: ComplaintService, private router: Router,
    private toastr: ToastrService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    if(localStorage.getItem('ForbiddenAccessToHeadAdmin') == 'true')
      this.router.navigate(['/password-change']);
    else{
        this.routeSub = this.route.params.subscribe(params => {
          this.getComplaint(params['id']);
        }, (error) => {
          this.errorMessage = error;
        });
    }   
  }
  public getComplaint(id: number){
    this.complaintService.getComplaint(id).subscribe(res => {
        this.complaint = res;        
      }, (error) => {
        this.errorMessage = error;
      });
  }

  public answerComplaint(){
    this.complaint.headAdminId = Number(localStorage.getItem("loggedUserId"));
    console.log(this.complaint)
    this.complaintService.answerComplaint(this.complaint).subscribe( res => 
      {
        this.toastr.success("You successfully answered a complaint!");
        this.router.navigate(['/admin-view-complaints']);
      }, (error) => {
        console.log(error)
        this.errorMessage = error;
        this.toastError();
      }
    );
  }

  saveComplaint(answerComplaintForm: NgForm): void {
    if (answerComplaintForm.dirty && answerComplaintForm.valid) {
      this.answerComplaint();
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
  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }
}
