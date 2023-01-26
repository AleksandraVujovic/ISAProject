export class Complaint {
    description: String = "";
    answer: String = "";
    customerName: String = "";
    complaintType: String = "";
    submissionDate: Date = new Date();
    defendantName: String = "";
    id: number = -1;
    headAdminId: number = -1;

    public constructor(obj?: any) {
        if (obj){
            this.description = obj.description;
            this.answer = obj.answer;
            this.customerName = obj.customerName;
            this.complaintType = obj.complaintType;
            this.submissionDate = obj.submissionDate;
            this.defendantName = obj.defendantName;
            this.id = obj.id;
            this.headAdminId = obj.headAdminId;
        }
    }
}
