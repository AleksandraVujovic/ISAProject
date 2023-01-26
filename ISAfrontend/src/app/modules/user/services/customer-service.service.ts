import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Customer } from 'src/app/model/Users/customer';
import { User } from '../edit-user/model/user';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  apiHost: string = 'http://localhost:8086/';
  headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  constructor(private http: HttpClient) {}

  updateCustomer(customer: any): Observable<any> {
    return this.http
      .put<any>(this.apiHost + 'api/customer', customer, {
        headers: this.headers,
      })
      .pipe(catchError(this.handleValidationError));
  }

  getAllCustomers(): Observable<User[]> {
    return this.http
      .get<User[]>(this.apiHost + 'api/customer', { headers: this.headers })
      .pipe(catchError(this.handleValidationError));
  }

  getCustomerByID(id: any): Observable<User> {
    return this.http
      .get<User>(this.apiHost + 'api/customer/' + id, { headers: this.headers })
      .pipe(catchError(this.handleValidationError));
  }

  private handleValidationError(error: HttpErrorResponse) {
    var map = new Map<string, string>();
    Object.keys(error.error).forEach((key) => {
      map.set(key, error.error[key]);
    });
    localStorage.setItem('errormap', JSON.stringify(Array.from(map.entries())));

    return throwError(() => new Error(error.status + '\n' + error.error));
  }
}
