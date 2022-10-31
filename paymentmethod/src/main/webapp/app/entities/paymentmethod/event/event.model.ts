import dayjs from 'dayjs/esm';

export interface IEvent {
  correlationId?: string | null;
  description?: string | null;
  domain?: string | null;
  eventId?: string | null;
  eventTime?: dayjs.Dayjs | null;
  eventType?: string | null;
  href?: string | null;
  id: string;
  priority?: string | null;
  timeOccurred?: dayjs.Dayjs | null;
  title?: string | null;
}

export type NewEvent = Omit<IEvent, 'id'> & { id: null };
