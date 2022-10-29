import { IResourceSpecificationRef, NewResourceSpecificationRef } from './resource-specification-ref.model';

export const sampleWithRequiredData: IResourceSpecificationRef = {
  id: 'fb44c057-3f33-458d-9b8b-275ff26417c3',
};

export const sampleWithPartialData: IResourceSpecificationRef = {
  id: '31f86a41-eb91-4379-8c67-4eb5f9dc1e48',
  type: 'auxiliary',
};

export const sampleWithFullData: IResourceSpecificationRef = {
  href: 'hack time-frame Applications',
  id: '6df513cd-07e4-483e-9ef4-2adc8838f35e',
  name: 'uniform Generic redefine',
  version: 'Intelligent',
  schemaLocation: 'hack',
  type: 'bypassing',
};

export const sampleWithNewData: NewResourceSpecificationRef = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
