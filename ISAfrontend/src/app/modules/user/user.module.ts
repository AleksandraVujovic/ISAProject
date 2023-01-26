import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
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
import { EditUserComponent } from './edit-user/edit-user.component';
import { RegisterCustomerComponent } from './register-customer/register-customer.component';
import { RegisterAdminComponent } from './register-admin/register-admin.component';
import { FillFormComponent } from './fill-form/fill-form.component';
import { AdminsProfileComponent } from './admins-profile/admins-profile.component';
import { AdminsBloodbankComponent } from './admins-bloodbank/admins-bloodbank.component';
import { ViewUsersComponent } from './view-users/view-users.component';
import { ViewCustomersComponent } from './view-customers/view-customers.component';
import { LoginUserComponent } from '../login-user/login-user.component';
import { Guard } from '../auth/guard';
import { AdminHomePageComponent } from './admin-home-page/admin-home-page.component';
import { MyAppointmentsComponent } from './my-appointments/my-appointments.component';
import { RegisterHeadAdminComponent } from './register-head-admin/register-head-admin.component';
import { HeadAdminPasswordChangeComponent } from './head-admin-password-change/head-admin-password-change.component';

const routes: Routes = [
  { path: 'edit-user', component: EditUserComponent },
  { path: 'register-admin', component: RegisterAdminComponent },
  { path: 'admin-home-page', component: AdminHomePageComponent },
  { path: 'admins-profile', component: AdminsProfileComponent },
  { path: 'admins-blood-bank', component: AdminsBloodbankComponent },
  { path: 'edit-user', component: EditUserComponent },
  { path: 'register-customer', component: RegisterCustomerComponent },
  { path: 'register-admin', component: RegisterAdminComponent },
  { path: 'fill-form', component: FillFormComponent },
  { path: 'view-users', component: ViewUsersComponent },
  { path: 'view-customers', component: ViewCustomersComponent},

  {
    path: 'fill-form',
    component: FillFormComponent,
    canActivate: [Guard],
    data: { permittedRoles: ['ROLE_HEADADMIN', 'ROLE_CUSTOMER'] },
  },
  { path: 'view-users', component: ViewUsersComponent },
  { path: 'login', component: LoginUserComponent },
  { path: 'my-appointments', component: MyAppointmentsComponent },
  { path: 'register-head-admin', component: RegisterHeadAdminComponent },
  { path: 'password-change', component: HeadAdminPasswordChangeComponent }
];

@NgModule({
  declarations: [
    EditUserComponent,
    RegisterAdminComponent,
    AdminHomePageComponent,
    AdminsProfileComponent,
    AdminsBloodbankComponent,
    RegisterCustomerComponent,
    RegisterAdminComponent,
    FillFormComponent,
    ViewUsersComponent,
    MyAppointmentsComponent,
    RegisterHeadAdminComponent,
    HeadAdminPasswordChangeComponent,
    ViewCustomersComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    BrowserModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    MaterialModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    NgxPaginationModule,
  ],
})
export class UserModule {}
