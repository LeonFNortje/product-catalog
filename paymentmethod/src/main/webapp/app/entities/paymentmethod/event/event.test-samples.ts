import dayjs from 'dayjs/esm';

import { IEvent, NewEvent } from './event.model';

export const sampleWithRequiredData: IEvent = {
  id: 'a31c6a78-2023-404b-961a-533ca290ed9f',
};

export const sampleWithPartialData: IEvent = {
  description: 'transmit generation fuchsia',
  eventType: 'Dalasi',
  id: '532677cb-b71d-475b-a91f-7117df445bd7',
  priority: 'Fords Planner Shoes',
  title: 'application Pennsylvania',
};

export const sampleWithFullData: IEvent = {
  correlationId: 'neural Home',
  description: 'override Wooden Suriname',
  domain: 'connecting',
  eventId: 'tangible Architect',
  eventTime: dayjs('2022-10-30T21:42'),
  eventType: 'teal Response FTP',
  href: 'Sports',
  id: '1bd7069d-fdbb-4663-abbd-dcd24303207f',
  priority: 'array',
  timeOccurred: dayjs('2022-10-31T11:49'),
  title: 'generation Field calculating',
};

export const sampleWithNewData: NewEvent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
