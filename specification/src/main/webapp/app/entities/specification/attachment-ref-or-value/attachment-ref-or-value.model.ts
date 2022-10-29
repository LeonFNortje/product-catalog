import dayjs from 'dayjs/esm';

export interface IAttachmentRefOrValue {
  attachmentType?: string | null;
  content?: string | null;
  description?: string | null;
  href?: string | null;
  id: string;
  mimeType?: string | null;
  name?: string | null;
  sizeOfBytes?: number | null;
  url?: string | null;
  validForFrom?: dayjs.Dayjs | null;
  validForTo?: dayjs.Dayjs | null;
  valueType?: string | null;
  schemaLocation?: string | null;
  type?: string | null;
}

export type NewAttachmentRefOrValue = Omit<IAttachmentRefOrValue, 'id'> & { id: null };
