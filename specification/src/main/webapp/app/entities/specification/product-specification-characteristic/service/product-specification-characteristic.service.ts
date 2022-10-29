import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductSpecificationCharacteristic, NewProductSpecificationCharacteristic } from '../product-specification-characteristic.model';

export type PartialUpdateProductSpecificationCharacteristic = Partial<IProductSpecificationCharacteristic> &
  Pick<IProductSpecificationCharacteristic, 'id'>;

type RestOf<T extends IProductSpecificationCharacteristic | NewProductSpecificationCharacteristic> = Omit<
  T,
  'validForFrom' | 'validForTo'
> & {
  validForFrom?: string | null;
  validForTo?: string | null;
};

export type RestProductSpecificationCharacteristic = RestOf<IProductSpecificationCharacteristic>;

export type NewRestProductSpecificationCharacteristic = RestOf<NewProductSpecificationCharacteristic>;

export type PartialUpdateRestProductSpecificationCharacteristic = RestOf<PartialUpdateProductSpecificationCharacteristic>;

export type EntityResponseType = HttpResponse<IProductSpecificationCharacteristic>;
export type EntityArrayResponseType = HttpResponse<IProductSpecificationCharacteristic[]>;

@Injectable({ providedIn: 'root' })
export class ProductSpecificationCharacteristicService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-specification-characteristics', 'specification');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productSpecificationCharacteristic: NewProductSpecificationCharacteristic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecificationCharacteristic);
    return this.http
      .post<RestProductSpecificationCharacteristic>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(productSpecificationCharacteristic: IProductSpecificationCharacteristic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecificationCharacteristic);
    return this.http
      .put<RestProductSpecificationCharacteristic>(
        `${this.resourceUrl}/${this.getProductSpecificationCharacteristicIdentifier(productSpecificationCharacteristic)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(productSpecificationCharacteristic: PartialUpdateProductSpecificationCharacteristic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecificationCharacteristic);
    return this.http
      .patch<RestProductSpecificationCharacteristic>(
        `${this.resourceUrl}/${this.getProductSpecificationCharacteristicIdentifier(productSpecificationCharacteristic)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestProductSpecificationCharacteristic>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProductSpecificationCharacteristic[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductSpecificationCharacteristicIdentifier(
    productSpecificationCharacteristic: Pick<IProductSpecificationCharacteristic, 'id'>
  ): string {
    return productSpecificationCharacteristic.id;
  }

  compareProductSpecificationCharacteristic(
    o1: Pick<IProductSpecificationCharacteristic, 'id'> | null,
    o2: Pick<IProductSpecificationCharacteristic, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getProductSpecificationCharacteristicIdentifier(o1) === this.getProductSpecificationCharacteristicIdentifier(o2)
      : o1 === o2;
  }

  addProductSpecificationCharacteristicToCollectionIfMissing<Type extends Pick<IProductSpecificationCharacteristic, 'id'>>(
    productSpecificationCharacteristicCollection: Type[],
    ...productSpecificationCharacteristicsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productSpecificationCharacteristics: Type[] = productSpecificationCharacteristicsToCheck.filter(isPresent);
    if (productSpecificationCharacteristics.length > 0) {
      const productSpecificationCharacteristicCollectionIdentifiers = productSpecificationCharacteristicCollection.map(
        productSpecificationCharacteristicItem =>
          this.getProductSpecificationCharacteristicIdentifier(productSpecificationCharacteristicItem)!
      );
      const productSpecificationCharacteristicsToAdd = productSpecificationCharacteristics.filter(
        productSpecificationCharacteristicItem => {
          const productSpecificationCharacteristicIdentifier = this.getProductSpecificationCharacteristicIdentifier(
            productSpecificationCharacteristicItem
          );
          if (productSpecificationCharacteristicCollectionIdentifiers.includes(productSpecificationCharacteristicIdentifier)) {
            return false;
          }
          productSpecificationCharacteristicCollectionIdentifiers.push(productSpecificationCharacteristicIdentifier);
          return true;
        }
      );
      return [...productSpecificationCharacteristicsToAdd, ...productSpecificationCharacteristicCollection];
    }
    return productSpecificationCharacteristicCollection;
  }

  protected convertDateFromClient<
    T extends IProductSpecificationCharacteristic | NewProductSpecificationCharacteristic | PartialUpdateProductSpecificationCharacteristic
  >(productSpecificationCharacteristic: T): RestOf<T> {
    return {
      ...productSpecificationCharacteristic,
      validForFrom: productSpecificationCharacteristic.validForFrom?.toJSON() ?? null,
      validForTo: productSpecificationCharacteristic.validForTo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restProductSpecificationCharacteristic: RestProductSpecificationCharacteristic
  ): IProductSpecificationCharacteristic {
    return {
      ...restProductSpecificationCharacteristic,
      validForFrom: restProductSpecificationCharacteristic.validForFrom
        ? dayjs(restProductSpecificationCharacteristic.validForFrom)
        : undefined,
      validForTo: restProductSpecificationCharacteristic.validForTo ? dayjs(restProductSpecificationCharacteristic.validForTo) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestProductSpecificationCharacteristic>
  ): HttpResponse<IProductSpecificationCharacteristic> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestProductSpecificationCharacteristic[]>
  ): HttpResponse<IProductSpecificationCharacteristic[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
