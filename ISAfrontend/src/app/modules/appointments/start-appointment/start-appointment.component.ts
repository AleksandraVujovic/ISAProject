import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AppointmentStatus } from '../model/appointment-status.enum';
import { Appointment } from '../model/appointment.model';
import { BloodType } from '../model/blood-type.enum';
import { AppointmentService } from '../service/appointment.service';

@Component({
  selector: 'app-start-appointment',
  templateUrl: './start-appointment.component.html',
  styleUrls: ['./start-appointment.component.css']
})
export class StartAppointmentComponent implements OnInit {

 public selectedAppointment: Appointment;
 public bloodTypes : string[] = ["A+","B+","AB+","0+","A-","B-","AB-","0-"];

 public selectedBloodType: string;

  constructor(
    private appointmentService : AppointmentService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.selectedAppointment = this.appointmentService.selectedAppointment;
    this.selectedBloodType = this.bloodTypes[0];
    console.log("Start Appointment");
    
    console.log(this.selectedAppointment);
  }

  UpdateAppointment(){
    this.selectedAppointment.executed = AppointmentStatus.DONE;
    this.selectedAppointment.typeOfBlood;
    
    if(this.selectedBloodType == "A+"){this.selectedAppointment.typeOfBlood = BloodType.A_PLUS}
    else{if(this.selectedBloodType == "B+"){this.selectedAppointment.typeOfBlood = BloodType.B_PLUS}
    else{if(this.selectedBloodType == "AB+"){this.selectedAppointment.typeOfBlood = BloodType.AB_PLUS}
    else{if(this.selectedBloodType == "0+"){this.selectedAppointment.typeOfBlood = BloodType.O_PLUS}
    else{if(this.selectedBloodType == "A-"){this.selectedAppointment.typeOfBlood = BloodType.A_MINUS}
    else{if(this.selectedBloodType == "B-"){this.selectedAppointment.typeOfBlood = BloodType.B_MINUS}
    else{if(this.selectedBloodType == "AB-"){this.selectedAppointment.typeOfBlood = BloodType.AB_MINUS}
    else{this.selectedAppointment.typeOfBlood = BloodType.O_MINUS}
  }}}}}}
  console.log(this.selectedAppointment);
  this.appointmentService.updateAppointment(this.selectedAppointment).subscribe(res => {
    console.log(res);
  });
  
  this.router.navigate(['view-customers']);
}

seeQuestionnaire(){
  this.router.navigate(['answer-form/', this.selectedAppointment.takenBy.id]);
}

}
