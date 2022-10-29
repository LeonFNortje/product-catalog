import dayjs from 'dayjs/esm';

import { IProductSpecification, NewProductSpecification } from './product-specification.model';

export const sampleWithRequiredData: IProductSpecification = {
  id: '15a1b3c2-8084-4124-b73c-617f2305a748',
};

export const sampleWithPartialData: IProductSpecification = {
  description: 'Wooden incremental',
  href: 'Gardens withdrawal',
  id: 'b189556e-94fc-44e6-b9ce-0ed3685a8157',
  name: 'microchip Keyboard GB',
  isBundle: true,
  validForTo: dayjs('2022-10-29T08:17'),
  schemaLocation: 'Cotton SSL hybrid',
};

export const sampleWithFullData: IProductSpecification = {
  brand: 'calculating Assurance Orchestrator',
  description: 'Fantastic',
  href: 'Account',
  id: 'ce03274a-1e66-43cc-b911-370c4ab70cbb',
  name: 'magnetic',
  isBundle: true,
  lastUpdate: dayjs('2022-10-29T19:21'),
  lifecycleStatus: 'Drives Intelligent Gloves',
  productNumber: 'COM digital Loan',
  validForFrom: dayjs('2022-10-29T04:21'),
  validForTo: dayjs('2022-10-29T00:50'),
  version: 'Versatile payment web',
  schemaLocation: 'JBOD Program',
  type: 'Street Bike Manager',
};

export const sampleWithNewData: NewProductSpecification = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
