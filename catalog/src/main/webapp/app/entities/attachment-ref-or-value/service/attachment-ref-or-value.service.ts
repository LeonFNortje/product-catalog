import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAttachmentRefOrValue, NewAttachmentRefOrValue } from '../attachment-ref-or-value.model';

export type PartialUpdateAttachmentRefOrValue = Partial<IAttachmentRefOrValue> & Pick<IAttachmentRefOrValue, 'id'>;

type RestOf<T extends IAttachmentRefOrValue | NewAttachmentRefOrValue> = Omit<T, 'validForFrom' | 'validForTo'> & {
  validForFrom?: string | null;
  validForTo?: string | null;
};

export type RestAttachmentRefOrValue = RestOf<IAttachmentRefOrValue>;

export type NewRestAttachmentRefOrValue = RestOf<NewAttachmentRefOrValue>;

export type PartialUpdateRestAttachmentRefOrValue = RestOf<PartialUpdateAttachmentRefOrValue>;

export type EntityResponseType = HttpResponse<IAttachmentRefOrValue>;
export type EntityArrayResponseType = HttpResponse<IAttachmentRefOrValue[]>;

@Injectable({ providedIn: 'root' })
export class AttachmentRefOrValueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/attachment-ref-or-values');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(attachmentRefOrValue: NewAttachmentRefOrValue): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attachmentRefOrValue);
    return this.http
      .post<RestAttachmentRefOrValue>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(attachmentRefOrValue: IAttachmentRefOrValue): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attachmentRefOrValue);
    return this.http
      .put<RestAttachmentRefOrValue>(`${this.resourceUrl}/${this.getAttachmentRefOrValueIdentifier(attachmentRefOrValue)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(attachmentRefOrValue: PartialUpdateAttachmentRefOrValue): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attachmentRefOrValue);
    return this.http
      .patch<RestAttachmentRefOrValue>(`${this.resourceUrl}/${this.getAttachmentRefOrValueIdentifier(attachmentRefOrValue)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestAttachmentRefOrValue>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAttachmentRefOrValue[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAttachmentRefOrValueIdentifier(attachmentRefOrValue: Pick<IAttachmentRefOrValue, 'id'>): string {
    return attachmentRefOrValue.id;
  }

  compareAttachmentRefOrValue(o1: Pick<IAttachmentRefOrValue, 'id'> | null, o2: Pick<IAttachmentRefOrValue, 'id'> | null): boolean {
    return o1 && o2 ? this.getAttachmentRefOrValueIdentifier(o1) === this.getAttachmentRefOrValueIdentifier(o2) : o1 === o2;
  }

  addAttachmentRefOrValueToCollectionIfMissing<Type extends Pick<IAttachmentRefOrValue, 'id'>>(
    attachmentRefOrValueCollection: Type[],
    ...attachmentRefOrValuesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const attachmentRefOrValues: Type[] = attachmentRefOrValuesToCheck.filter(isPresent);
    if (attachmentRefOrValues.length > 0) {
      const attachmentRefOrValueCollectionIdentifiers = attachmentRefOrValueCollection.map(
        attachmentRefOrValueItem => this.getAttachmentRefOrValueIdentifier(attachmentRefOrValueItem)!
      );
      const attachmentRefOrValuesToAdd = attachmentRefOrValues.filter(attachmentRefOrValueItem => {
        const attachmentRefOrValueIdentifier = this.getAttachmentRefOrValueIdentifier(attachmentRefOrValueItem);
        if (attachmentRefOrValueCollectionIdentifiers.includes(attachmentRefOrValueIdentifier)) {
          return false;
        }
        attachmentRefOrValueCollectionIdentifiers.push(attachmentRefOrValueIdentifier);
        return true;
      });
      return [...attachmentRefOrValuesToAdd, ...attachmentRefOrValueCollection];
    }
    return attachmentRefOrValueCollection;
  }

  protected convertDateFromClient<T extends IAttachmentRefOrValue | NewAttachmentRefOrValue | PartialUpdateAttachmentRefOrValue>(
    attachmentRefOrValue: T
  ): RestOf<T> {
    return {
      ...attachmentRefOrValue,
      validForFrom: attachmentRefOrValue.validForFrom?.toJSON() ?? null,
      validForTo: attachmentRefOrValue.validForTo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAttachmentRefOrValue: RestAttachmentRefOrValue): IAttachmentRefOrValue {
    return {
      ...restAttachmentRefOrValue,
      validForFrom: restAttachmentRefOrValue.validForFrom ? dayjs(restAttachmentRefOrValue.validForFrom) : undefined,
      validForTo: restAttachmentRefOrValue.validForTo ? dayjs(restAttachmentRefOrValue.validForTo) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAttachmentRefOrValue>): HttpResponse<IAttachmentRefOrValue> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAttachmentRefOrValue[]>): HttpResponse<IAttachmentRefOrValue[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
