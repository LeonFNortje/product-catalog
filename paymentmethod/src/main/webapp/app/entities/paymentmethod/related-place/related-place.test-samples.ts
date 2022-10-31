import { IRelatedPlace, NewRelatedPlace } from './related-place.model';

export const sampleWithRequiredData: IRelatedPlace = {
  id: '89001e9d-27a2-4a07-87d5-2e084eb95fd7',
};

export const sampleWithPartialData: IRelatedPlace = {
  id: '2944507b-f458-4a5e-aed8-136288a53e18',
  href: 'Senior Light',
  name: 'Illinois',
  type: 'Soft Kids invoice',
};

export const sampleWithFullData: IRelatedPlace = {
  id: 'e9def03c-7b61-4bd4-ac97-a48d31f8b108',
  href: 'systems',
  name: 'leverage',
  role: 'Dollar experiences',
  schemaLocation: 'Wisconsin driver Principal',
  type: 'Avon Table',
};

export const sampleWithNewData: NewRelatedPlace = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
