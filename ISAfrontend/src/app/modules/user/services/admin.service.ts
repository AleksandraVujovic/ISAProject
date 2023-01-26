import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http'
import { Observable, catchError, throwError } from 'rxjs';
import { RegistrationAdmin } from '../model/registration-admin.model';


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  apiHost: string = "http://localhost:8086/";
  headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  constructor(private http: HttpClient) { }

  getAdminById(id : any): Observable<any>{
    console.log(id);
    
    return this.http.get<any>(this.apiHost + 'api/admin/' + id, {headers: this.headers});
  }

  saveAdminChanges(admin: any): Observable<any>{
    return this.http.post<any>(this.apiHost + 'api/admin', admin ,{headers: this.headers})
  }
  
  registerAdmin(admin: any): Observable<RegistrationAdmin>{
    return this.http.post<any>(this.apiHost + 'api/admin/register/', admin, {headers: this.headers}).pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    if(error.status == 406){
      console.log("da")
      var map = new Map<string, string>();
      Object.keys(error.error).forEach(key => {  
        map.set(key, error.error[key] )
      });
      localStorage.setItem('errormap', JSON.stringify(Array.from(map.entries())));
    }
  
    return throwError(() => new Error(error.status +'\n'+ error.error))
  }
}
