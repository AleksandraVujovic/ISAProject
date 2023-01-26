import { OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import {
  Component,
  ChangeDetectionStrategy,
  ViewChild,
  TemplateRef,
} from '@angular/core';
import {
  isSameDay,
  isSameMonth,
} from 'date-fns';
import { Subject } from 'rxjs';
import {
  CalendarEvent,
  CalendarEventAction,
  CalendarEventTimesChangedEvent,
  CalendarView,
} from 'angular-calendar';
import { EventColor } from 'calendar-utils';
import { AppointmentService } from '../services/appointment.service';
import { aD } from '@fullcalendar/core/internal-common';
import { Router } from '@angular/router';

const colors: Record<string, EventColor> = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF',
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
};

@Component({
  selector: 'app-admin-calendar-view',
  templateUrl: './admin-calendar-view.component.html',
  styleUrls: ['./admin-calendar-view.component.css']
})
export class AdminCalendarViewComponent implements OnInit {
  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any> | undefined;

  view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  viewDate: Date = new Date();
  month: Date = new Date();

  modalData: {
    action: string;
    event: CalendarEvent;
  } | undefined;

  refresh = new Subject<void>();
  events!: CalendarEvent[];
  activeDayIsOpen: boolean = true;

  public errorMessage: Error = new Error();
  public errorMap: Map<string, string> = new Map();
  public monthAndYear: Date = new Date();

  constructor(private appointmentService: AppointmentService, private toastr: ToastrService, private router: Router) {}

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  eventClicked(action: string, event: CalendarEvent): void {
    console.log("usla u handleeee sam")
    this.modalData = { event, action };
    console.log(this.modalData.event)
  }

  eventTimesChanged({
    event,
    newStart,
    newEnd,
  }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd,
        };
      }
      return iEvent;
    });
  }

  public changeMonth(): void {
    console.log(this.viewDate)
    console.log(this.month)
    this.viewDate = new Date(this.month)
    console.log(this.viewDate)
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  ngOnInit(): void {
    let adminId = localStorage.getItem('loggedUserId');
    if(adminId != null) {
      this.getDoneAndPendingAppointmentsForBloodBank(Number(adminId));
    } 
  }

  getDoneAndPendingAppointmentsForBloodBank(adminId: number){
    this.appointmentService.getDoneAndPendingAppointmentsForBloodBank(adminId).subscribe(
      (response: CalendarEvent[]) => {
        response.forEach(element => {
          element.start = new Date(element.start);
          element.end = new Date(element.end!);
        });
        this.events = response;
      },
      (error) => {
        this.errorMessage = error;
        this.toastError();
    }
    );
  }

  addNewAppointment(){
    this.router.navigate(['create-appointment']);
  }

  private toastError() {
    if (String(this.errorMessage).includes('406')) {
      var error = localStorage.getItem('errormap')!;
      this.errorMap = new Map(JSON.parse(error));

      for (let entry of this.errorMap.entries()) {
        this.toastr.error('Validation error: ' + entry[1]);
      }
    } else {
      this.toastr.error(this.errorMessage.message);
    }
  }
  public monthChanged(value: any, widget: any): void {
    this.monthAndYear = value;
    this.viewDate = new Date(this.monthAndYear!)
    widget.close();
  }

}
