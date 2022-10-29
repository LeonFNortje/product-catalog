import { IBundledProductSpecification, NewBundledProductSpecification } from './bundled-product-specification.model';

export const sampleWithRequiredData: IBundledProductSpecification = {
  id: '935bd8a4-d385-4954-8142-5a7c9fc6401d',
};

export const sampleWithPartialData: IBundledProductSpecification = {
  id: '569a3751-8f1e-4ba2-80ce-e04503298ac9',
  schemaLocation: 'Usability',
};

export const sampleWithFullData: IBundledProductSpecification = {
  href: 'Alaska',
  id: '86b95646-8228-471e-90b4-b1967ef3fe80',
  name: 'El Orchestrator',
  lifecycleStatus: 'Fresh Forward Function-based',
  schemaLocation: 'Corporate Incredible',
  type: 'Markets',
};

export const sampleWithNewData: NewBundledProductSpecification = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
