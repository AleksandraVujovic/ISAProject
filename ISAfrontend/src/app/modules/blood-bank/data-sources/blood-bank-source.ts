import { DataSource } from '@angular/cdk/table';
import { CollectionViewer } from '@angular/cdk/collections';
import { Observable, BehaviorSubject, of } from "rxjs";
import { catchError, finalize } from "rxjs/operators";
import { BloodBank } from 'src/app/model/blood-bank.model';
import { BloodBankService } from '../services/blood-bank.service';
import { PagableRequest } from '../model/pagable-request';

export class BloodBankSource implements DataSource<BloodBank> {

    private bloodBankSubject = new BehaviorSubject<BloodBank[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private countSubject = new BehaviorSubject<number>(0);

    public loading$ = this.loadingSubject.asObservable();
    public count$ = this.countSubject.asObservable();

    constructor(private bloodBankService: BloodBankService) { }


    connect(collectionViewer: CollectionViewer): Observable<readonly BloodBank[]> {
        return this.bloodBankSubject.asObservable()
    }
    
    disconnect(collectionViewer: CollectionViewer): void {
        this.bloodBankSubject.complete();
        this.loadingSubject.complete();
        this.countSubject.complete();
    }

    loadBloodBanks(request: PagableRequest = new PagableRequest()) {
        this.loadingSubject.next(true)
        this.bloodBankService.getBloodBanksPagable(request)
          .pipe(
            catchError(() => of([])),
            finalize(() => this.loadingSubject.next(false))
          )
          .subscribe((result: any) => {
            this.bloodBankSubject.next(result.content);
            this.countSubject.next(result.totalElements);
        })
    }
}
