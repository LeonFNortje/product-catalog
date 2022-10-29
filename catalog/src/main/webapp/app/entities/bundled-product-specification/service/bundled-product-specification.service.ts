import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBundledProductSpecification, NewBundledProductSpecification } from '../bundled-product-specification.model';

export type PartialUpdateBundledProductSpecification = Partial<IBundledProductSpecification> & Pick<IBundledProductSpecification, 'id'>;

export type EntityResponseType = HttpResponse<IBundledProductSpecification>;
export type EntityArrayResponseType = HttpResponse<IBundledProductSpecification[]>;

@Injectable({ providedIn: 'root' })
export class BundledProductSpecificationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bundled-product-specifications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bundledProductSpecification: NewBundledProductSpecification): Observable<EntityResponseType> {
    return this.http.post<IBundledProductSpecification>(this.resourceUrl, bundledProductSpecification, { observe: 'response' });
  }

  update(bundledProductSpecification: IBundledProductSpecification): Observable<EntityResponseType> {
    return this.http.put<IBundledProductSpecification>(
      `${this.resourceUrl}/${this.getBundledProductSpecificationIdentifier(bundledProductSpecification)}`,
      bundledProductSpecification,
      { observe: 'response' }
    );
  }

  partialUpdate(bundledProductSpecification: PartialUpdateBundledProductSpecification): Observable<EntityResponseType> {
    return this.http.patch<IBundledProductSpecification>(
      `${this.resourceUrl}/${this.getBundledProductSpecificationIdentifier(bundledProductSpecification)}`,
      bundledProductSpecification,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IBundledProductSpecification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBundledProductSpecification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBundledProductSpecificationIdentifier(bundledProductSpecification: Pick<IBundledProductSpecification, 'id'>): string {
    return bundledProductSpecification.id;
  }

  compareBundledProductSpecification(
    o1: Pick<IBundledProductSpecification, 'id'> | null,
    o2: Pick<IBundledProductSpecification, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getBundledProductSpecificationIdentifier(o1) === this.getBundledProductSpecificationIdentifier(o2) : o1 === o2;
  }

  addBundledProductSpecificationToCollectionIfMissing<Type extends Pick<IBundledProductSpecification, 'id'>>(
    bundledProductSpecificationCollection: Type[],
    ...bundledProductSpecificationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bundledProductSpecifications: Type[] = bundledProductSpecificationsToCheck.filter(isPresent);
    if (bundledProductSpecifications.length > 0) {
      const bundledProductSpecificationCollectionIdentifiers = bundledProductSpecificationCollection.map(
        bundledProductSpecificationItem => this.getBundledProductSpecificationIdentifier(bundledProductSpecificationItem)!
      );
      const bundledProductSpecificationsToAdd = bundledProductSpecifications.filter(bundledProductSpecificationItem => {
        const bundledProductSpecificationIdentifier = this.getBundledProductSpecificationIdentifier(bundledProductSpecificationItem);
        if (bundledProductSpecificationCollectionIdentifiers.includes(bundledProductSpecificationIdentifier)) {
          return false;
        }
        bundledProductSpecificationCollectionIdentifiers.push(bundledProductSpecificationIdentifier);
        return true;
      });
      return [...bundledProductSpecificationsToAdd, ...bundledProductSpecificationCollection];
    }
    return bundledProductSpecificationCollection;
  }
}
