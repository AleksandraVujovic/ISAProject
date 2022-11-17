import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditUserComponent } from './edit-user/edit-user.component';
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
import { MatDatepickerModule} from '@angular/material/datepicker';
import { RegisterCustomerComponent } from './register-customer/register-customer.component';
import { RegisterAdminComponent } from './register-admin/register-admin.component';
import { AdminHomePageComponent } from './admin-home-page/admin-home-page.component';
import { AdminsProfileComponent } from './admins-profile/admins-profile.component';
import { AdminsBloodbankComponent } from './admins-bloodbank/admins-bloodbank.component';
import { ViewUsersComponent } from './view-users/view-users.component';

const routes: Routes = [
  { path: 'edit-user', component: EditUserComponent },
  { path: 'register-admin', component: RegisterAdminComponent },
  { path: 'admin-home-page', component: AdminHomePageComponent},
  { path: 'admins-profile', component: AdminsProfileComponent},
  { path: 'admins-blood-bank', component: AdminsBloodbankComponent},
  { path: 'edit-user/:id', component: EditUserComponent },
  { path: 'register-customer', component: RegisterCustomerComponent },
  { path: 'register-admin', component: RegisterAdminComponent },
  { path: 'view-users', component: ViewUsersComponent }

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
    ViewUsersComponent
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
    RouterModule.forChild(routes)
  ]
})
export class UserModule { }
