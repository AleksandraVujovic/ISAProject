import { Time } from "@angular/common";
import { BloodBank } from "../../../model/blood-bank.model";
import { User } from "../../user/edit-user/model/user";
import { AppointmentStatus } from "./appointment-status.enum";
import { BloodType } from "./blood-type.enum";

export class Appointment{
    id: number = -1;
    appointmentDate: Date = new Date;
    startTime: Time 
    endTime: Time 
    comment: String = "";
    executed : AppointmentStatus = AppointmentStatus.PENDING
    location : BloodBank = new BloodBank();
    version: number = 0;
    typeOfBlood : BloodType = BloodType.AB_MINUS;
    quantityOfBlood: number = 0;
    takenBy : User = new User();
    
    constructor(obj : any){
        this.id = obj.id
        this.appointmentDate = obj.appointmentDate;
        this.startTime = obj.startTime;
        this.endTime = obj.endTime;
        this.comment = obj.comment;
        this.executed = obj.appointmentStatus;
        this.location = obj.bloodBank;
        this.version = obj.version;
        this.typeOfBlood = obj.typeOfBlood;
        this.quantityOfBlood = obj.quantityOfBlood;
        this.takenBy = obj.takenBy;
    }


}