import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MatTableDataSource } from '@angular/material/table';
import { BloodBank } from 'src/app/model/blood-bank.model';
import { BloodBankService } from '../services/blood-bank.service';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatSort, Sort } from '@angular/material/sort';
import { Search } from '../model/search';
import { MatPaginator } from '@angular/material/paginator';
import { BloodBankSource } from '../data-sources/blood-bank-source';
import { merge, tap } from 'rxjs';
import { PagableRequest } from '../model/pagable-request';
import { FilterType } from '../model/filter-type';

@Component({
  selector: 'app-list-banks',
  templateUrl: './list-banks.component.html',
  styleUrls: ['./list-banks.component.css'],
})
export class ListBanksComponent implements AfterViewInit, OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  public bloodBankSource: BloodBankSource;
  public errorMessage: any;
  public searchQuery: string = '';
  public filterSelect: string = '0';
  public bottomRange: number = 0;
  public topRange: number = 0;

  public displayedColumns = [
    'name',
    'email',
    'address.country',
    'address.city',
    'address.street',
    'address.number',
    'rating',
  ];

  constructor(
    private bloodBankService: BloodBankService,
    private router: Router,
    private toastr: ToastrService,
    private _liveAnnouncer: LiveAnnouncer
  ) {}

  ngOnInit(): void {
    if(localStorage.getItem('ForbiddenAccessToHeadAdmin') == 'true')
      this.router.navigate(['/password-change']);
    else{
      this.bloodBankSource = new BloodBankSource(this.bloodBankService);
      this.bloodBankSource.loadBloodBanks();
    }
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(tap(() => this.loadBloodBanks()))
      .subscribe();
  }

  loadBloodBanks() {
    this.bloodBankSource.loadBloodBanks(
      new PagableRequest({
        pageIndex: this.paginator.pageIndex,
        pageSize: this.paginator.pageSize,
        filter: this.searchQuery,
        filterType: this.convertSearchEnumToFilterType(),
        sortColumn: this.sort.active,
        sortDirection: this.sort.direction,
      })
    );
  }

  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  findByRange() {
    this.searchQuery = this.bottomRange + '|' + this.topRange;
    this.loadBloodBanks();
  }

  private convertSearchEnumToFilterType(): FilterType {
    if (this.filterSelect == '0') {
      return FilterType.NAME_SEARCH;
    } else if (this.filterSelect == '1') {
      return FilterType.ADDRESS_SEARCH;
    } else if (this.filterSelect == '2') {
      return FilterType.RATING;
    } else {
      return FilterType.NAME_SEARCH;
    }
  }

  public chooseBloodBank(bankId: number) {}
}
