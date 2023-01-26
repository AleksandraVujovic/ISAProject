import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Customer } from 'src/app/model/Users/customer';
import { CustomerService } from '../../user/services/customer.service';
import { AppointmentStatus } from '../model/appointment-status.enum';
import { Appointment } from '../model/appointment.model';
import { AppointmentService1 } from '../service/appointment.service';
import { QuestionnaireService } from '../services/questionnaire.service';

@Component({
  selector: 'app-view-customers-appointment',
  templateUrl: './view-customers-appointment.component.html',
  styleUrls: ['./view-customers-appointment.component.css']
})
export class ViewCustomersAppointmentComponent implements OnInit {
  public customer : Customer;
  public appointments: Appointment[];
  selectedAppointment: Appointment;
  disabled: boolean;

  constructor(
    private appointmentService : AppointmentService1,
    private customerService : CustomerService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.disabled = true;
    this.customer = this.customerService.customer;
    this.appointmentService.getAppointmentsByCustomerId(this.customer.id).subscribe(res => {
      this.appointments = res;
      console.log(this.appointments);
    }
    )
  }

  updateNotCome(){
    this.selectedAppointment.executed = AppointmentStatus.CANCALLED;
    this.appointmentService.updateAppointment(this.selectedAppointment).subscribe(res => {
      console.log(res); 
      this.router.navigate(['view-customers'])
    });
  }

  updateCome(){
    this.appointmentService.selectedAppointment = this.selectedAppointment;
    console.log(this.appointmentService.selectedAppointment);
    this.router.navigate(['start-appointment']);
  }

  selectAppointment(appointment:Appointment){
    if(appointment.executed.toString()   == 'PENDING'){
      this.disabled = false;
      this.selectedAppointment = appointment;
    }else{
      this.disabled = true;
      this.selectedAppointment = appointment;
    }
  }
}
