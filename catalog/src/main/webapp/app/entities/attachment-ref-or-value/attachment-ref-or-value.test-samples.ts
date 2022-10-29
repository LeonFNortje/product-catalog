import dayjs from 'dayjs/esm';

import { IAttachmentRefOrValue, NewAttachmentRefOrValue } from './attachment-ref-or-value.model';

export const sampleWithRequiredData: IAttachmentRefOrValue = {
  id: 'e19eb0c2-35c6-4e4d-84d6-1e5061e1b748',
};

export const sampleWithPartialData: IAttachmentRefOrValue = {
  attachmentType: 'digital Gorgeous',
  content: 'driver Metrics hub',
  id: '1fc1321d-c8c8-4c26-a23a-64b3a4ef568f',
  mimeType: 'Personal best-of-breed',
  name: 'interface',
  sizeOfBytes: 86956,
  type: 'Focused',
};

export const sampleWithFullData: IAttachmentRefOrValue = {
  attachmentType: 'Administrator Honduras',
  content: 'Fish',
  description: 'Grocery strategic',
  href: 'Technician Handmade Architect',
  id: '11b69c54-2492-47fc-bd3d-5ebd4fa8881d',
  mimeType: 'PNG Nakfa Naira',
  name: 'Soft brand metrics',
  sizeOfBytes: 36743,
  url: 'http://gladyce.org',
  validForFrom: dayjs('2022-10-29T06:50'),
  validForTo: dayjs('2022-10-29T04:35'),
  valueType: 'Synergized',
  schemaLocation: 'invoice Executive heuristic',
  type: 'JSON Garden',
};

export const sampleWithNewData: NewAttachmentRefOrValue = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
