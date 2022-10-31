import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentMethod, NewPaymentMethod } from '../payment-method.model';

export type PartialUpdatePaymentMethod = Partial<IPaymentMethod> & Pick<IPaymentMethod, 'id'>;

type RestOf<T extends IPaymentMethod | NewPaymentMethod> = Omit<T, 'statusDate' | 'validForFrom' | 'validForTo'> & {
  statusDate?: string | null;
  validForFrom?: string | null;
  validForTo?: string | null;
};

export type RestPaymentMethod = RestOf<IPaymentMethod>;

export type NewRestPaymentMethod = RestOf<NewPaymentMethod>;

export type PartialUpdateRestPaymentMethod = RestOf<PartialUpdatePaymentMethod>;

export type EntityResponseType = HttpResponse<IPaymentMethod>;
export type EntityArrayResponseType = HttpResponse<IPaymentMethod[]>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-methods', 'paymentmethod');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentMethod: NewPaymentMethod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentMethod);
    return this.http
      .post<RestPaymentMethod>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(paymentMethod: IPaymentMethod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentMethod);
    return this.http
      .put<RestPaymentMethod>(`${this.resourceUrl}/${this.getPaymentMethodIdentifier(paymentMethod)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(paymentMethod: PartialUpdatePaymentMethod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentMethod);
    return this.http
      .patch<RestPaymentMethod>(`${this.resourceUrl}/${this.getPaymentMethodIdentifier(paymentMethod)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestPaymentMethod>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPaymentMethod[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPaymentMethodIdentifier(paymentMethod: Pick<IPaymentMethod, 'id'>): string {
    return paymentMethod.id;
  }

  comparePaymentMethod(o1: Pick<IPaymentMethod, 'id'> | null, o2: Pick<IPaymentMethod, 'id'> | null): boolean {
    return o1 && o2 ? this.getPaymentMethodIdentifier(o1) === this.getPaymentMethodIdentifier(o2) : o1 === o2;
  }

  addPaymentMethodToCollectionIfMissing<Type extends Pick<IPaymentMethod, 'id'>>(
    paymentMethodCollection: Type[],
    ...paymentMethodsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paymentMethods: Type[] = paymentMethodsToCheck.filter(isPresent);
    if (paymentMethods.length > 0) {
      const paymentMethodCollectionIdentifiers = paymentMethodCollection.map(
        paymentMethodItem => this.getPaymentMethodIdentifier(paymentMethodItem)!
      );
      const paymentMethodsToAdd = paymentMethods.filter(paymentMethodItem => {
        const paymentMethodIdentifier = this.getPaymentMethodIdentifier(paymentMethodItem);
        if (paymentMethodCollectionIdentifiers.includes(paymentMethodIdentifier)) {
          return false;
        }
        paymentMethodCollectionIdentifiers.push(paymentMethodIdentifier);
        return true;
      });
      return [...paymentMethodsToAdd, ...paymentMethodCollection];
    }
    return paymentMethodCollection;
  }

  protected convertDateFromClient<T extends IPaymentMethod | NewPaymentMethod | PartialUpdatePaymentMethod>(paymentMethod: T): RestOf<T> {
    return {
      ...paymentMethod,
      statusDate: paymentMethod.statusDate?.toJSON() ?? null,
      validForFrom: paymentMethod.validForFrom?.toJSON() ?? null,
      validForTo: paymentMethod.validForTo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPaymentMethod: RestPaymentMethod): IPaymentMethod {
    return {
      ...restPaymentMethod,
      statusDate: restPaymentMethod.statusDate ? dayjs(restPaymentMethod.statusDate) : undefined,
      validForFrom: restPaymentMethod.validForFrom ? dayjs(restPaymentMethod.validForFrom) : undefined,
      validForTo: restPaymentMethod.validForTo ? dayjs(restPaymentMethod.validForTo) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPaymentMethod>): HttpResponse<IPaymentMethod> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPaymentMethod[]>): HttpResponse<IPaymentMethod[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
