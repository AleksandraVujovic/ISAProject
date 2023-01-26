import { Address } from './address.model';

export class BloodBank {
  bankID: number = -1;
  name: string = '';
  email: string = '';
  description: string = '';
  address: Address = new Address();
  rating: number = 0.0;
  endTime: number = 23;
  startTime: number = 0;

  public constructor(obj?: any) {
    if (obj) {
      this.bankID = obj?.name ?? 0;
      this.name = obj?.name ?? '';
      this.email = obj?.email ?? '';
      this.description = obj?.description ?? '';
      this.address = obj.address;
      this.rating = obj?.rating ?? 0.0;
      this.endTime = obj?.endTime ?? 23;
      this.startTime = obj?.startTime ?? 0;
    }
  }
}
