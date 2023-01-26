import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
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
//import { AnswerQuestionaireComponent } from './answer-questionaire/answer-questionaire.component';
import { ViewCustomersAppointmentComponent } from './view-customers-appointment/view-customers-appointment.component';
import { StartAppointmentComponent } from './start-appointment/start-appointment.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule } from '@angular/material/sort';
import { SelectAppointmentComponent } from './select-appointment/select-appointment.component';
import { AnswerFormComponent } from './answer-form/answer-form.component';
import { Guard } from '../auth/guard';
import { AdminCalendarViewComponent } from './admin-calendar-view/admin-calendar-view.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

const routes: Routes = [
  {
    path: 'create-appointment',
    component: AdminCreateAppointmentComponent,
    canActivate: [Guard],
    data: { permittedRoles: ['ROLE_ADMIN'] }
  },
  { path: 'find-appointment', component: FindAppointmentComponent },
  //{ path: 'answer-questionaire', component: AnswerQuestionaireComponent },
  { path: 'view-customers-appiontments', component: ViewCustomersAppointmentComponent },
  { path: 'start-appointment', component: StartAppointmentComponent },
  { path: 'select-appointment/:id', component: SelectAppointmentComponent },
  {
    path: 'answer-form/:id',
    component: AnswerFormComponent,
    canActivate: [Guard],
    data: { permittedRoles: ['ROLE_CUSTOMER','ROLE_ADMIN'] }
  },
  { path: 'admin-calendar', component: AdminCalendarViewComponent, canActivate:[Guard], data: { permittedRoles: ['ROLE_ADMIN']} }
];

@NgModule({
  declarations: [
    AdminCreateAppointmentComponent,
    FindAppointmentComponent,
    ViewCustomersAppointmentComponent,
    StartAppointmentComponent,
    SelectAppointmentComponent,
    AnswerFormComponent,
    AdminCalendarViewComponent
    //AnswerQuestionaireComponent,
  ],
  imports: [
    CommonModule,
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
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSortModule,
    RouterModule.forChild(routes),
    FullCalendarModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }
    ),
  ],
})
export class AppointmentsModule { }
