import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  IProductSpecificationCharacteristicRelationship,
  NewProductSpecificationCharacteristicRelationship,
} from '../product-specification-characteristic-relationship.model';

export type PartialUpdateProductSpecificationCharacteristicRelationship = Partial<IProductSpecificationCharacteristicRelationship> &
  Pick<IProductSpecificationCharacteristicRelationship, 'id'>;

type RestOf<T extends IProductSpecificationCharacteristicRelationship | NewProductSpecificationCharacteristicRelationship> = Omit<
  T,
  'validForFrom' | 'validForTo'
> & {
  validForFrom?: string | null;
  validForTo?: string | null;
};

export type RestProductSpecificationCharacteristicRelationship = RestOf<IProductSpecificationCharacteristicRelationship>;

export type NewRestProductSpecificationCharacteristicRelationship = RestOf<NewProductSpecificationCharacteristicRelationship>;

export type PartialUpdateRestProductSpecificationCharacteristicRelationship =
  RestOf<PartialUpdateProductSpecificationCharacteristicRelationship>;

export type EntityResponseType = HttpResponse<IProductSpecificationCharacteristicRelationship>;
export type EntityArrayResponseType = HttpResponse<IProductSpecificationCharacteristicRelationship[]>;

@Injectable({ providedIn: 'root' })
export class ProductSpecificationCharacteristicRelationshipService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor(
    'api/product-specification-characteristic-relationships',
    'specification'
  );

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(
    productSpecificationCharacteristicRelationship: NewProductSpecificationCharacteristicRelationship
  ): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecificationCharacteristicRelationship);
    return this.http
      .post<RestProductSpecificationCharacteristicRelationship>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecificationCharacteristicRelationship);
    return this.http
      .put<RestProductSpecificationCharacteristicRelationship>(
        `${this.resourceUrl}/${this.getProductSpecificationCharacteristicRelationshipIdentifier(
          productSpecificationCharacteristicRelationship
        )}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(
    productSpecificationCharacteristicRelationship: PartialUpdateProductSpecificationCharacteristicRelationship
  ): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecificationCharacteristicRelationship);
    return this.http
      .patch<RestProductSpecificationCharacteristicRelationship>(
        `${this.resourceUrl}/${this.getProductSpecificationCharacteristicRelationshipIdentifier(
          productSpecificationCharacteristicRelationship
        )}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestProductSpecificationCharacteristicRelationship>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProductSpecificationCharacteristicRelationship[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductSpecificationCharacteristicRelationshipIdentifier(
    productSpecificationCharacteristicRelationship: Pick<IProductSpecificationCharacteristicRelationship, 'id'>
  ): string {
    return productSpecificationCharacteristicRelationship.id;
  }

  compareProductSpecificationCharacteristicRelationship(
    o1: Pick<IProductSpecificationCharacteristicRelationship, 'id'> | null,
    o2: Pick<IProductSpecificationCharacteristicRelationship, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getProductSpecificationCharacteristicRelationshipIdentifier(o1) ===
          this.getProductSpecificationCharacteristicRelationshipIdentifier(o2)
      : o1 === o2;
  }

  addProductSpecificationCharacteristicRelationshipToCollectionIfMissing<
    Type extends Pick<IProductSpecificationCharacteristicRelationship, 'id'>
  >(
    productSpecificationCharacteristicRelationshipCollection: Type[],
    ...productSpecificationCharacteristicRelationshipsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productSpecificationCharacteristicRelationships: Type[] =
      productSpecificationCharacteristicRelationshipsToCheck.filter(isPresent);
    if (productSpecificationCharacteristicRelationships.length > 0) {
      const productSpecificationCharacteristicRelationshipCollectionIdentifiers =
        productSpecificationCharacteristicRelationshipCollection.map(
          productSpecificationCharacteristicRelationshipItem =>
            this.getProductSpecificationCharacteristicRelationshipIdentifier(productSpecificationCharacteristicRelationshipItem)!
        );
      const productSpecificationCharacteristicRelationshipsToAdd = productSpecificationCharacteristicRelationships.filter(
        productSpecificationCharacteristicRelationshipItem => {
          const productSpecificationCharacteristicRelationshipIdentifier = this.getProductSpecificationCharacteristicRelationshipIdentifier(
            productSpecificationCharacteristicRelationshipItem
          );
          if (
            productSpecificationCharacteristicRelationshipCollectionIdentifiers.includes(
              productSpecificationCharacteristicRelationshipIdentifier
            )
          ) {
            return false;
          }
          productSpecificationCharacteristicRelationshipCollectionIdentifiers.push(
            productSpecificationCharacteristicRelationshipIdentifier
          );
          return true;
        }
      );
      return [...productSpecificationCharacteristicRelationshipsToAdd, ...productSpecificationCharacteristicRelationshipCollection];
    }
    return productSpecificationCharacteristicRelationshipCollection;
  }

  protected convertDateFromClient<
    T extends
      | IProductSpecificationCharacteristicRelationship
      | NewProductSpecificationCharacteristicRelationship
      | PartialUpdateProductSpecificationCharacteristicRelationship
  >(productSpecificationCharacteristicRelationship: T): RestOf<T> {
    return {
      ...productSpecificationCharacteristicRelationship,
      validForFrom: productSpecificationCharacteristicRelationship.validForFrom?.toJSON() ?? null,
      validForTo: productSpecificationCharacteristicRelationship.validForTo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restProductSpecificationCharacteristicRelationship: RestProductSpecificationCharacteristicRelationship
  ): IProductSpecificationCharacteristicRelationship {
    return {
      ...restProductSpecificationCharacteristicRelationship,
      validForFrom: restProductSpecificationCharacteristicRelationship.validForFrom
        ? dayjs(restProductSpecificationCharacteristicRelationship.validForFrom)
        : undefined,
      validForTo: restProductSpecificationCharacteristicRelationship.validForTo
        ? dayjs(restProductSpecificationCharacteristicRelationship.validForTo)
        : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestProductSpecificationCharacteristicRelationship>
  ): HttpResponse<IProductSpecificationCharacteristicRelationship> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestProductSpecificationCharacteristicRelationship[]>
  ): HttpResponse<IProductSpecificationCharacteristicRelationship[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
