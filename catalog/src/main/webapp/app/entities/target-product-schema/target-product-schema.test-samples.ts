import { ITargetProductSchema, NewTargetProductSchema } from './target-product-schema.model';

export const sampleWithRequiredData: ITargetProductSchema = {
  id: 47270,
};

export const sampleWithPartialData: ITargetProductSchema = {
  id: 70162,
  schemaLocation: 'Forward',
  type: 'Highway Cotton Uzbekistan',
};

export const sampleWithFullData: ITargetProductSchema = {
  id: 6345,
  schemaLocation: 'Metal',
  type: 'enable Metical',
};

export const sampleWithNewData: NewTargetProductSchema = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
