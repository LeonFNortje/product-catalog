import dayjs from 'dayjs/esm';

import {
  IProductSpecificationCharacteristicRelationship,
  NewProductSpecificationCharacteristicRelationship,
} from './product-specification-characteristic-relationship.model';

export const sampleWithRequiredData: IProductSpecificationCharacteristicRelationship = {
  id: 'cb79153f-0bd2-435d-98fb-7171643788cd',
};

export const sampleWithPartialData: IProductSpecificationCharacteristicRelationship = {
  id: '69b4f816-3c77-4a7b-9766-a66e0c2f225d',
  name: 'fuchsia Developer grow',
  schemaLocation: 'rich Common',
  type: 'Shoes',
};

export const sampleWithFullData: IProductSpecificationCharacteristicRelationship = {
  href: 'Car withdrawal Account',
  id: 'e891918c-d1d8-4720-aea1-8300823a2c8a',
  name: 'Account lime payment',
  relationshipType: 'green RAM',
  validForFrom: dayjs('2022-10-29T10:13'),
  validForTo: dayjs('2022-10-29T02:51'),
  schemaLocation: 'Account',
  type: 'protocol',
};

export const sampleWithNewData: NewProductSpecificationCharacteristicRelationship = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
