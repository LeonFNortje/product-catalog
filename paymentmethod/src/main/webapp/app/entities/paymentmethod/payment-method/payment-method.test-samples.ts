import dayjs from 'dayjs/esm';

import { IPaymentMethod, NewPaymentMethod } from './payment-method.model';

export const sampleWithRequiredData: IPaymentMethod = {
  id: 'b6adcb13-a3bf-435c-90d1-4bb66b326f2f',
};

export const sampleWithPartialData: IPaymentMethod = {
  id: '401c29d5-133b-4035-9837-305b57f98939',
  href: 'Borders SCSI Music',
  name: 'teal',
  statusDate: dayjs('2022-10-30T18:47'),
  statusReason: 'transmitting',
  validForFrom: dayjs('2022-10-30T17:05'),
};

export const sampleWithFullData: IPaymentMethod = {
  id: 'bbbfe542-1ac6-4766-b366-8a7a023a37ac',
  href: 'mission-critical',
  authorizationCode: 'Rial Mills Ferry',
  description: 'XSS',
  isPreferred: false,
  name: 'foreground parsing',
  status: 'Concrete',
  statusDate: dayjs('2022-10-30T22:36'),
  statusReason: 'granular vertical',
  validForFrom: dayjs('2022-10-31T12:50'),
  validForTo: dayjs('2022-10-30T16:54'),
  schemaLocation: 'hack generate Intuitive',
  type: 'e-markets',
};

export const sampleWithNewData: NewPaymentMethod = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
