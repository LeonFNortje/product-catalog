import dayjs from 'dayjs/esm';

import { ICharacteristicValueSpecification, NewCharacteristicValueSpecification } from './characteristic-value-specification.model';

export const sampleWithRequiredData: ICharacteristicValueSpecification = {
  id: 5957,
};

export const sampleWithPartialData: ICharacteristicValueSpecification = {
  id: 90779,
  rangeInterval: 'compressing of Crest',
  valueType: 'Assurance',
  type: 'quantifying hacking',
};

export const sampleWithFullData: ICharacteristicValueSpecification = {
  id: 3722,
  isDefault: false,
  rangeInterval: 'Colorado',
  regex: 'Bacon pixel Center',
  unitOfMeasure: 'Franc Open-architected',
  validForFrom: dayjs('2022-10-29T19:23'),
  validForTo: dayjs('2022-10-29T02:07'),
  valueType: 'Unbranded real-time Steel',
  schemaLocation: 'paradigms Automotive',
  type: 'Balanced',
};

export const sampleWithNewData: NewCharacteristicValueSpecification = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
