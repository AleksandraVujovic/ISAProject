import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminCalendarComponent } from './admin-calendar/admin-calendar.component';
import { Routes, RouterModule } from '@angular/router';
import { FullCalendarModule } from '@fullcalendar/angular';
import { MaterialModule } from 'src/app/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { NgxPaginationModule } from 'ngx-pagination';
import { AdminCreateAppointmentComponent } from './admin-create-appointment/admin-create-appointment.component';
import { FindAppointmentComponent } from './find-appointment/find-appointment.component';
import { AnswerQuestionaireComponent } from './answer-questionaire/answer-questionaire.component';

const routes: Routes = [
  { path: 'calendar', component: AdminCalendarComponent },
  { path: 'create-appointment', component: AdminCreateAppointmentComponent },
  { path: 'find-appointment', component: FindAppointmentComponent },
  { path: 'answer-questionaire', component: AnswerQuestionaireComponent },
];

@NgModule({
  declarations: [
    AdminCalendarComponent,
    AdminCreateAppointmentComponent,
    FindAppointmentComponent,
    AnswerQuestionaireComponent
  ],
  imports: [
    CommonModule,
    FullCalendarModule,
    MaterialModule,
    FormsModule, 
    ReactiveFormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,
    MatDatepickerModule,
    NgxPaginationModule,
    RouterModule.forChild(routes),
  ],
})
export class AppointmentsModule { }
