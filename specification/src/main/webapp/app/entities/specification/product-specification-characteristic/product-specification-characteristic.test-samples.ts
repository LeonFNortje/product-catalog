import dayjs from 'dayjs/esm';

import { IProductSpecificationCharacteristic, NewProductSpecificationCharacteristic } from './product-specification-characteristic.model';

export const sampleWithRequiredData: IProductSpecificationCharacteristic = {
  id: '74e76147-3963-430d-984b-af3b1b3f9075',
};

export const sampleWithPartialData: IProductSpecificationCharacteristic = {
  extensible: false,
  id: '3f82ff1d-468e-49c7-91cc-1792e3defc4d',
  maxCardinality: 21760,
  regex: 'Berkshire Skyway',
  validForTo: dayjs('2022-10-29T02:29'),
  schemaLocation: 'lime Gold Avon',
  type: 'payment generate Future-proofed',
  valueSchemaLocation: 'blue calculate',
};

export const sampleWithFullData: IProductSpecificationCharacteristic = {
  configurable: false,
  description: 'Gloves',
  extensible: true,
  id: '75f34bd1-9bf9-4613-9a0c-728f03da2f0a',
  isUnique: false,
  maxCardinality: 22183,
  minCardinality: 14123,
  name: 'Tactics Handmade',
  regex: 'Euro PNG Tools',
  validForFrom: dayjs('2022-10-29T02:48'),
  validForTo: dayjs('2022-10-29T21:34'),
  valueType: 'virtual turquoise',
  schemaLocation: 'transmit Facilitator holistic',
  type: 'Franc Developer copy',
  valueSchemaLocation: 'Boliviano Account Hawaii',
};

export const sampleWithNewData: NewProductSpecificationCharacteristic = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
