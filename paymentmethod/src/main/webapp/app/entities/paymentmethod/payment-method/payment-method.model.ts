import dayjs from 'dayjs/esm';
import { IRelatedPlace } from 'app/entities/paymentmethod/related-place/related-place.model';
import { IRelatedParty } from 'app/entities/paymentmethod/related-party/related-party.model';

export interface IPaymentMethod {
  id: string;
  href?: string | null;
  authorizationCode?: string | null;
  description?: string | null;
  isPreferred?: boolean | null;
  name?: string | null;
  status?: string | null;
  statusDate?: dayjs.Dayjs | null;
  statusReason?: string | null;
  validForFrom?: dayjs.Dayjs | null;
  validForTo?: dayjs.Dayjs | null;
  schemaLocation?: string | null;
  type?: string | null;
  relatedPlace?: Pick<IRelatedPlace, 'id'> | null;
  relatedParty?: Pick<IRelatedParty, 'id'> | null;
}

export type NewPaymentMethod = Omit<IPaymentMethod, 'id'> & { id: null };
