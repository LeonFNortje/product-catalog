import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductSpecification, NewProductSpecification } from '../product-specification.model';

export type PartialUpdateProductSpecification = Partial<IProductSpecification> & Pick<IProductSpecification, 'id'>;

type RestOf<T extends IProductSpecification | NewProductSpecification> = Omit<T, 'lastUpdate' | 'validForFrom' | 'validForTo'> & {
  lastUpdate?: string | null;
  validForFrom?: string | null;
  validForTo?: string | null;
};

export type RestProductSpecification = RestOf<IProductSpecification>;

export type NewRestProductSpecification = RestOf<NewProductSpecification>;

export type PartialUpdateRestProductSpecification = RestOf<PartialUpdateProductSpecification>;

export type EntityResponseType = HttpResponse<IProductSpecification>;
export type EntityArrayResponseType = HttpResponse<IProductSpecification[]>;

@Injectable({ providedIn: 'root' })
export class ProductSpecificationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-specifications', 'specification');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productSpecification: NewProductSpecification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecification);
    return this.http
      .post<RestProductSpecification>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(productSpecification: IProductSpecification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecification);
    return this.http
      .put<RestProductSpecification>(`${this.resourceUrl}/${this.getProductSpecificationIdentifier(productSpecification)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(productSpecification: PartialUpdateProductSpecification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSpecification);
    return this.http
      .patch<RestProductSpecification>(`${this.resourceUrl}/${this.getProductSpecificationIdentifier(productSpecification)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestProductSpecification>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProductSpecification[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductSpecificationIdentifier(productSpecification: Pick<IProductSpecification, 'id'>): string {
    return productSpecification.id;
  }

  compareProductSpecification(o1: Pick<IProductSpecification, 'id'> | null, o2: Pick<IProductSpecification, 'id'> | null): boolean {
    return o1 && o2 ? this.getProductSpecificationIdentifier(o1) === this.getProductSpecificationIdentifier(o2) : o1 === o2;
  }

  addProductSpecificationToCollectionIfMissing<Type extends Pick<IProductSpecification, 'id'>>(
    productSpecificationCollection: Type[],
    ...productSpecificationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productSpecifications: Type[] = productSpecificationsToCheck.filter(isPresent);
    if (productSpecifications.length > 0) {
      const productSpecificationCollectionIdentifiers = productSpecificationCollection.map(
        productSpecificationItem => this.getProductSpecificationIdentifier(productSpecificationItem)!
      );
      const productSpecificationsToAdd = productSpecifications.filter(productSpecificationItem => {
        const productSpecificationIdentifier = this.getProductSpecificationIdentifier(productSpecificationItem);
        if (productSpecificationCollectionIdentifiers.includes(productSpecificationIdentifier)) {
          return false;
        }
        productSpecificationCollectionIdentifiers.push(productSpecificationIdentifier);
        return true;
      });
      return [...productSpecificationsToAdd, ...productSpecificationCollection];
    }
    return productSpecificationCollection;
  }

  protected convertDateFromClient<T extends IProductSpecification | NewProductSpecification | PartialUpdateProductSpecification>(
    productSpecification: T
  ): RestOf<T> {
    return {
      ...productSpecification,
      lastUpdate: productSpecification.lastUpdate?.toJSON() ?? null,
      validForFrom: productSpecification.validForFrom?.toJSON() ?? null,
      validForTo: productSpecification.validForTo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProductSpecification: RestProductSpecification): IProductSpecification {
    return {
      ...restProductSpecification,
      lastUpdate: restProductSpecification.lastUpdate ? dayjs(restProductSpecification.lastUpdate) : undefined,
      validForFrom: restProductSpecification.validForFrom ? dayjs(restProductSpecification.validForFrom) : undefined,
      validForTo: restProductSpecification.validForTo ? dayjs(restProductSpecification.validForTo) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProductSpecification>): HttpResponse<IProductSpecification> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProductSpecification[]>): HttpResponse<IProductSpecification[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
