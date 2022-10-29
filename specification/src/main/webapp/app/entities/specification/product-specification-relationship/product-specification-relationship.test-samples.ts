import dayjs from 'dayjs/esm';

import { IProductSpecificationRelationship, NewProductSpecificationRelationship } from './product-specification-relationship.model';

export const sampleWithRequiredData: IProductSpecificationRelationship = {
  id: '3b4b37e3-6563-486f-9fa1-e40137fd3318',
};

export const sampleWithPartialData: IProductSpecificationRelationship = {
  href: 'Investment index Central',
  id: 'd717c050-5fd6-43f1-b58d-5e82e6b72103',
  relationshipType: 'Tunisia array Mouse',
  validForFrom: dayjs('2022-10-28T22:03'),
};

export const sampleWithFullData: IProductSpecificationRelationship = {
  href: 'Mobility transmitter Home',
  id: '8b7db0fe-7bb9-4eb5-82ec-38dba659d245',
  name: 'Congolese UIC-Franc',
  relationshipType: 'Account',
  validForFrom: dayjs('2022-10-29T20:24'),
  validForTo: dayjs('2022-10-29T14:55'),
  schemaLocation: 'platforms Unbranded',
  type: 'Plastic',
};

export const sampleWithNewData: NewProductSpecificationRelationship = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
