import { IServiceSpecificationRef, NewServiceSpecificationRef } from './service-specification-ref.model';

export const sampleWithRequiredData: IServiceSpecificationRef = {
  id: 'db295d87-183a-44cd-8423-7d7f5e228c23',
};

export const sampleWithPartialData: IServiceSpecificationRef = {
  href: 'Practical withdrawal',
  id: 'b84ed348-a586-4e97-be4f-c4f92bdc539a',
  name: 'ADP black',
  version: 'Solutions actuating Mouse',
  schemaLocation: 'Liaison Applications Games',
};

export const sampleWithFullData: IServiceSpecificationRef = {
  href: 'reciprocal Frozen multi-byte',
  id: '47e75f7a-d85e-44c1-9961-c7281511fed9',
  name: 'orange reboot',
  version: 'action-items mission-critical',
  schemaLocation: 'Ohio Automotive IB',
  type: 'Brand',
};

export const sampleWithNewData: NewServiceSpecificationRef = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
