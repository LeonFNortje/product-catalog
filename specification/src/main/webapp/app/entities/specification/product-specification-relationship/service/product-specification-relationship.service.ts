import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductSpecificationRelationship, NewProductSpecificationRelationship } from '../product-specification-relationship.model';

export type PartialUpdateProductSpecificationRelationship = Partial<IProductSpecificationRelationship> &
  Pick<IProductSpecificationRelationship, 'id'>;

type RestOf<T extends IProductSpecificationRelationship | NewProductSpecificationRelationship> = Omit<T, 'validForFrom' | 'validForTo'> & {
  validForFrom?: string | null;
  validForTo?: string | null;
};

export type RestProductSpecificationRelationship = RestOf<IProductSpecificationRelationship>;

export type NewRestProductSpecificationRelationship = RestOf<NewProductSpecificationRelationship>;

export type PartialUpdateRestProductSpecificationRelationship = RestOf<PartialUpdateProductSpecificationRelationship>;

export type EntityResponseType = HttpResponse<IProductSpecificationRelationship>;
export type EntityArrayResponseType = HttpResponse<IProductSpecificationRelationship[]>;

@Injectable({ providedIn: 'root' })
export class ProductSpecificationRelationshipService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-specification-relationships', 'specification');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productSpecificationRelationship: NewProductSpecificationRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecificationRelationship);
    return this.http
      .post<RestProductSpecificationRelationship>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(productSpecificationRelationship: IProductSpecificationRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecificationRelationship);
    return this.http
      .put<RestProductSpecificationRelationship>(
        `${this.resourceUrl}/${this.getProductSpecificationRelationshipIdentifier(productSpecificationRelationship)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(productSpecificationRelationship: PartialUpdateProductSpecificationRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecificationRelationship);
    return this.http
      .patch<RestProductSpecificationRelationship>(
        `${this.resourceUrl}/${this.getProductSpecificationRelationshipIdentifier(productSpecificationRelationship)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestProductSpecificationRelationship>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProductSpecificationRelationship[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductSpecificationRelationshipIdentifier(productSpecificationRelationship: Pick<IProductSpecificationRelationship, 'id'>): string {
    return productSpecificationRelationship.id;
  }

  compareProductSpecificationRelationship(
    o1: Pick<IProductSpecificationRelationship, 'id'> | null,
    o2: Pick<IProductSpecificationRelationship, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getProductSpecificationRelationshipIdentifier(o1) === this.getProductSpecificationRelationshipIdentifier(o2)
      : o1 === o2;
  }

  addProductSpecificationRelationshipToCollectionIfMissing<Type extends Pick<IProductSpecificationRelationship, 'id'>>(
    productSpecificationRelationshipCollection: Type[],
    ...productSpecificationRelationshipsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productSpecificationRelationships: Type[] = productSpecificationRelationshipsToCheck.filter(isPresent);
    if (productSpecificationRelationships.length > 0) {
      const productSpecificationRelationshipCollectionIdentifiers = productSpecificationRelationshipCollection.map(
        productSpecificationRelationshipItem => this.getProductSpecificationRelationshipIdentifier(productSpecificationRelationshipItem)!
      );
      const productSpecificationRelationshipsToAdd = productSpecificationRelationships.filter(productSpecificationRelationshipItem => {
        const productSpecificationRelationshipIdentifier = this.getProductSpecificationRelationshipIdentifier(
          productSpecificationRelationshipItem
        );
        if (productSpecificationRelationshipCollectionIdentifiers.includes(productSpecificationRelationshipIdentifier)) {
          return false;
        }
        productSpecificationRelationshipCollectionIdentifiers.push(productSpecificationRelationshipIdentifier);
        return true;
      });
      return [...productSpecificationRelationshipsToAdd, ...productSpecificationRelationshipCollection];
    }
    return productSpecificationRelationshipCollection;
  }

  protected convertDateFromClient<
    T extends IProductSpecificationRelationship | NewProductSpecificationRelationship | PartialUpdateProductSpecificationRelationship
  >(productSpecificationRelationship: T): RestOf<T> {
    return {
      ...productSpecificationRelationship,
      validForFrom: productSpecificationRelationship.validForFrom?.toJSON() ?? null,
      validForTo: productSpecificationRelationship.validForTo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restProductSpecificationRelationship: RestProductSpecificationRelationship
  ): IProductSpecificationRelationship {
    return {
      ...restProductSpecificationRelationship,
      validForFrom: restProductSpecificationRelationship.validForFrom
        ? dayjs(restProductSpecificationRelationship.validForFrom)
        : undefined,
      validForTo: restProductSpecificationRelationship.validForTo ? dayjs(restProductSpecificationRelationship.validForTo) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestProductSpecificationRelationship>
  ): HttpResponse<IProductSpecificationRelationship> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestProductSpecificationRelationship[]>
  ): HttpResponse<IProductSpecificationRelationship[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
