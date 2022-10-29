import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResourceSpecificationRef, NewResourceSpecificationRef } from '../resource-specification-ref.model';

export type PartialUpdateResourceSpecificationRef = Partial<IResourceSpecificationRef> & Pick<IResourceSpecificationRef, 'id'>;

export type EntityResponseType = HttpResponse<IResourceSpecificationRef>;
export type EntityArrayResponseType = HttpResponse<IResourceSpecificationRef[]>;

@Injectable({ providedIn: 'root' })
export class ResourceSpecificationRefService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resource-specification-refs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resourceSpecificationRef: NewResourceSpecificationRef): Observable<EntityResponseType> {
    return this.http.post<IResourceSpecificationRef>(this.resourceUrl, resourceSpecificationRef, { observe: 'response' });
  }

  update(resourceSpecificationRef: IResourceSpecificationRef): Observable<EntityResponseType> {
    return this.http.put<IResourceSpecificationRef>(
      `${this.resourceUrl}/${this.getResourceSpecificationRefIdentifier(resourceSpecificationRef)}`,
      resourceSpecificationRef,
      { observe: 'response' }
    );
  }

  partialUpdate(resourceSpecificationRef: PartialUpdateResourceSpecificationRef): Observable<EntityResponseType> {
    return this.http.patch<IResourceSpecificationRef>(
      `${this.resourceUrl}/${this.getResourceSpecificationRefIdentifier(resourceSpecificationRef)}`,
      resourceSpecificationRef,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IResourceSpecificationRef>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResourceSpecificationRef[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResourceSpecificationRefIdentifier(resourceSpecificationRef: Pick<IResourceSpecificationRef, 'id'>): string {
    return resourceSpecificationRef.id;
  }

  compareResourceSpecificationRef(
    o1: Pick<IResourceSpecificationRef, 'id'> | null,
    o2: Pick<IResourceSpecificationRef, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getResourceSpecificationRefIdentifier(o1) === this.getResourceSpecificationRefIdentifier(o2) : o1 === o2;
  }

  addResourceSpecificationRefToCollectionIfMissing<Type extends Pick<IResourceSpecificationRef, 'id'>>(
    resourceSpecificationRefCollection: Type[],
    ...resourceSpecificationRefsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const resourceSpecificationRefs: Type[] = resourceSpecificationRefsToCheck.filter(isPresent);
    if (resourceSpecificationRefs.length > 0) {
      const resourceSpecificationRefCollectionIdentifiers = resourceSpecificationRefCollection.map(
        resourceSpecificationRefItem => this.getResourceSpecificationRefIdentifier(resourceSpecificationRefItem)!
      );
      const resourceSpecificationRefsToAdd = resourceSpecificationRefs.filter(resourceSpecificationRefItem => {
        const resourceSpecificationRefIdentifier = this.getResourceSpecificationRefIdentifier(resourceSpecificationRefItem);
        if (resourceSpecificationRefCollectionIdentifiers.includes(resourceSpecificationRefIdentifier)) {
          return false;
        }
        resourceSpecificationRefCollectionIdentifiers.push(resourceSpecificationRefIdentifier);
        return true;
      });
      return [...resourceSpecificationRefsToAdd, ...resourceSpecificationRefCollection];
    }
    return resourceSpecificationRefCollection;
  }
}
