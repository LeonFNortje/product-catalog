import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEvent, NewEvent } from '../event.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEvent for edit and NewEventFormGroupInput for create.
 */
type EventFormGroupInput = IEvent | PartialWithRequiredKeyOf<NewEvent>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEvent | NewEvent> = Omit<T, 'eventTime' | 'timeOccurred'> & {
  eventTime?: string | null;
  timeOccurred?: string | null;
};

type EventFormRawValue = FormValueOf<IEvent>;

type NewEventFormRawValue = FormValueOf<NewEvent>;

type EventFormDefaults = Pick<NewEvent, 'eventTime' | 'id' | 'timeOccurred'>;

type EventFormGroupContent = {
  correlationId: FormControl<EventFormRawValue['correlationId']>;
  description: FormControl<EventFormRawValue['description']>;
  domain: FormControl<EventFormRawValue['domain']>;
  eventId: FormControl<EventFormRawValue['eventId']>;
  eventTime: FormControl<EventFormRawValue['eventTime']>;
  eventType: FormControl<EventFormRawValue['eventType']>;
  href: FormControl<EventFormRawValue['href']>;
  id: FormControl<EventFormRawValue['id'] | NewEvent['id']>;
  priority: FormControl<EventFormRawValue['priority']>;
  timeOccurred: FormControl<EventFormRawValue['timeOccurred']>;
  title: FormControl<EventFormRawValue['title']>;
};

export type EventFormGroup = FormGroup<EventFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventFormService {
  createEventFormGroup(event: EventFormGroupInput = { id: null }): EventFormGroup {
    const eventRawValue = this.convertEventToEventRawValue({
      ...this.getFormDefaults(),
      ...event,
    });
    return new FormGroup<EventFormGroupContent>({
      correlationId: new FormControl(eventRawValue.correlationId),
      description: new FormControl(eventRawValue.description),
      domain: new FormControl(eventRawValue.domain),
      eventId: new FormControl(eventRawValue.eventId),
      eventTime: new FormControl(eventRawValue.eventTime),
      eventType: new FormControl(eventRawValue.eventType),
      href: new FormControl(eventRawValue.href),
      id: new FormControl(
        { value: eventRawValue.id, disabled: eventRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      priority: new FormControl(eventRawValue.priority),
      timeOccurred: new FormControl(eventRawValue.timeOccurred),
      title: new FormControl(eventRawValue.title),
    });
  }

  getEvent(form: EventFormGroup): IEvent | NewEvent {
    return this.convertEventRawValueToEvent(form.getRawValue() as EventFormRawValue | NewEventFormRawValue);
  }

  resetForm(form: EventFormGroup, event: EventFormGroupInput): void {
    const eventRawValue = this.convertEventToEventRawValue({ ...this.getFormDefaults(), ...event });
    form.reset(
      {
        ...eventRawValue,
        id: { value: eventRawValue.id, disabled: eventRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventFormDefaults {
    const currentTime = dayjs();

    return {
      eventTime: currentTime,
      id: null,
      timeOccurred: currentTime,
    };
  }

  private convertEventRawValueToEvent(rawEvent: EventFormRawValue | NewEventFormRawValue): IEvent | NewEvent {
    return {
      ...rawEvent,
      eventTime: dayjs(rawEvent.eventTime, DATE_TIME_FORMAT),
      timeOccurred: dayjs(rawEvent.timeOccurred, DATE_TIME_FORMAT),
    };
  }

  private convertEventToEventRawValue(
    event: IEvent | (Partial<NewEvent> & EventFormDefaults)
  ): EventFormRawValue | PartialWithRequiredKeyOf<NewEventFormRawValue> {
    return {
      ...event,
      eventTime: event.eventTime ? event.eventTime.format(DATE_TIME_FORMAT) : undefined,
      timeOccurred: event.timeOccurred ? event.timeOccurred.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
