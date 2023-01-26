import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { BehaviorSubject, catchError, finalize, Observable, of } from "rxjs";
import { Appointment } from "../model/appointment";
import { AppointmentService } from "../services/appointment.service";
import { PageRequest } from "../requests/page-request";

export class AppointmentSource implements DataSource<Appointment> {

    private appointmentSubject = new BehaviorSubject<Appointment[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private countSubject = new BehaviorSubject<number>(0);

    public loading$ = this.loadingSubject.asObservable();
    public count$ = this.countSubject.asObservable();

    constructor(private appointmentService: AppointmentService) { }

    connect(collectionViewer: CollectionViewer): Observable<readonly Appointment[]> {
        return this.appointmentSubject.asObservable()
    }
    
    disconnect(collectionViewer: CollectionViewer): void {
        this.appointmentSubject.complete();
        this.loadingSubject.complete();
        this.countSubject.complete();
    }

    loadAppointments(request : PageRequest = new PageRequest()) {
        this.loadingSubject.next(true)
        this.appointmentService.getAllAppointmentsPageable(request)
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe((result : any) => {
                this.appointmentSubject.next(result.content);
                this.countSubject.next(result.totalElements);
            })
    }
}