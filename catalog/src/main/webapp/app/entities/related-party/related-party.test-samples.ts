import { IRelatedParty, NewRelatedParty } from './related-party.model';

export const sampleWithRequiredData: IRelatedParty = {
  id: '994d6f13-b001-48fc-a2ad-e480800b3b83',
};

export const sampleWithPartialData: IRelatedParty = {
  id: 'bf318f52-a855-4dd4-ab3a-41d09a5a67c9',
  type: 'Greens',
};

export const sampleWithFullData: IRelatedParty = {
  id: '7fade5eb-b449-4d61-b020-e2d5596ed890',
  href: 'Open-source experiences',
  name: 'challenge set',
  role: 'Hungary Handcrafted',
  schemaLocation: 'communities',
  type: 'circuit',
};

export const sampleWithNewData: NewRelatedParty = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
