import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, UrlSerializer } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable, Subscription } from 'rxjs';
import { Customer } from 'src/app/model/Users/customer';
import { Gender } from '../../user/edit-user/model/gender';
import { Role } from '../../user/edit-user/model/role';
import { BookAppointment } from '../model/book-appointment';
import { Questionnaire } from '../model/questionnaire';
import { AppointmentService } from '../services/appointment.service';
import { CustomerService } from '../services/customer.service';
import { QuestionnaireService } from '../services/questionnaire.service';
import { UserService } from '../../user/services/user.service';

@Component({
  selector: 'app-answer-form',
  templateUrl: './answer-form.component.html',
  styleUrls: ['./answer-form.component.css'],
})
export class AnswerFormComponent implements OnInit {
  public questionnaire: Questionnaire = new Questionnaire();
  public gender: any;
  public questionairePresent: boolean = false;
  private routeSub: Subscription;

  constructor(
    private serializer : UrlSerializer,
    private userService: UserService,
    private questionnaireService: QuestionnaireService,
    private appointmentService: AppointmentService,
    private customerService: CustomerService,
    private router: Router,
    private toastr: ToastrService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    if(localStorage.getItem("loggedUserRole") == "ROLE_ADMIN"){
      console.log("ROLE_ADMIN");
      
    const url = document.URL;
    var splitted = url.split("/",5)
    console.log("URL JE");
    
    
    console.log(splitted[0]);
    console.log(splitted[1]);
    console.log(splitted[2]);
    console.log(splitted[3]);
    console.log(splitted[4]);
    console.log(splitted[5]);
    console.log(url);

    var userId =splitted[4];
    this.customerService.getCustomerByID(userId).subscribe(
      (res) => {
        this.gender = res.gender;
      },
      (error) => {
        this.toastr.error(error);
      }
    );
    this.questionnaireService.getQuestionnaireForCustomer(userId).subscribe(
      (res) => {
        if (res == null) {
          this.questionnaire.customerId = userId;
          return;
        }
        this.questionnaire = res;
        this.questionairePresent = true;
      },
      (error) => {
        this.toastr.error(error);
      }
    );
    
    }else{

      var userId = localStorage.getItem('loggedUserId') ?? '-1';
      this.customerService.getCustomerByID(userId).subscribe(
        (res) => {
          this.gender = res.gender;
        },
        (error) => {
          this.toastr.error(error);
        }
        );
        this.questionnaireService.getQuestionnaireForCustomer(userId).subscribe(
          (res) => {
            if (res == null) {
              this.questionnaire.customerId = userId;
              return;
            }
            this.questionnaire = res;
            this.questionairePresent = true;
          },
          (error) => {
            this.toastr.error(error);
          }
          );
        }
      }

  public updateQuestionnaireAndReserveAppointment(): void {
    var questionnaireAction: Observable<any>;
    if (this.questionairePresent) {
      questionnaireAction = this.updateQuestionnaire();
    } else {
      questionnaireAction = this.createQuestionnaire();
    }
    questionnaireAction.subscribe(
      (res) => {
        this.reserveAppointment();
      },
      (error) => {
        this.toastr.error(error);
      }
    );
  }

  private createQuestionnaire(): Observable<any> {
    return this.questionnaireService.createQuestionnaire(this.questionnaire);
  }

  private updateQuestionnaire(): Observable<any> {
    return this.questionnaireService.updateQuestionnaire(this.questionnaire);
  }

  public isFemale(customer: Customer) {
    customer.gender.valueOf() === 0;
  }

  reserveAppointment() {
    var userId = localStorage.getItem('loggedUserId') ?? '-1';
    this.routeSub = this.route.params.subscribe(
      (params) => {
        var bookAppointment = new BookAppointment();
        bookAppointment.appointmentId = params['id'];
        bookAppointment.customerId = userId;
        this.appointmentService.reserveAppointment(bookAppointment).subscribe(
          (res) => {
            this.toastr.success('Succsesfuly reserved appointment');
          },
          (error) => {
            this.toastr.error(error);
          }
        );
      },
      (error) => {
        this.toastr.error(error);
      }
    );
  }
}
