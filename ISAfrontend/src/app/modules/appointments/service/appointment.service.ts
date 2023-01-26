import {
    HttpClient,
    HttpErrorResponse,
    HttpHeaders,
  } from '@angular/common/http';
  import { Injectable } from '@angular/core';
  import { catchError, Observable, throwError } from 'rxjs';
import { Customer } from 'src/app/model/Users/customer';
import { Appointment } from '../model/appointment.model';
  
  @Injectable({
    providedIn: 'root',
  })

  export class AppointmentService1{

    selectedAppointment : Appointment;
    apiHost: string = 'http://localhost:8086/';
    headers: HttpHeaders = new HttpHeaders({
        'Content-Type': 'application/json',
      });
  constructor(private http: HttpClient) {}

    getAppointmentsByCustomerId(id : number): Observable<Appointment[]> {
        console.log(this.apiHost + 'api/appointment/customers?id=' + id, {headers: this.headers});
        
        var nesto = this.http.get<any>(this.apiHost + 'api/appointment/customer?id=' + id , {headers: this.headers});
        console.log(nesto);
        return nesto;
    }
    updateAppointment(appointment : Appointment): Observable<any>{
        return this.http.post<any>(this.apiHost + 'api/appointment/byAdmin',appointment,{headers: this.headers});
    }

    getAppointmentById(id: any): Observable<Appointment>{
      return this.http.get<any>(this.apiHost + 'api/appointment/' + id , {headers: this.headers});
    }
  }