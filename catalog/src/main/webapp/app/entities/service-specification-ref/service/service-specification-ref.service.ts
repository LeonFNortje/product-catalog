import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServiceSpecificationRef, NewServiceSpecificationRef } from '../service-specification-ref.model';

export type PartialUpdateServiceSpecificationRef = Partial<IServiceSpecificationRef> & Pick<IServiceSpecificationRef, 'id'>;

export type EntityResponseType = HttpResponse<IServiceSpecificationRef>;
export type EntityArrayResponseType = HttpResponse<IServiceSpecificationRef[]>;

@Injectable({ providedIn: 'root' })
export class ServiceSpecificationRefService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/service-specification-refs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(serviceSpecificationRef: NewServiceSpecificationRef): Observable<EntityResponseType> {
    return this.http.post<IServiceSpecificationRef>(this.resourceUrl, serviceSpecificationRef, { observe: 'response' });
  }

  update(serviceSpecificationRef: IServiceSpecificationRef): Observable<EntityResponseType> {
    return this.http.put<IServiceSpecificationRef>(
      `${this.resourceUrl}/${this.getServiceSpecificationRefIdentifier(serviceSpecificationRef)}`,
      serviceSpecificationRef,
      { observe: 'response' }
    );
  }

  partialUpdate(serviceSpecificationRef: PartialUpdateServiceSpecificationRef): Observable<EntityResponseType> {
    return this.http.patch<IServiceSpecificationRef>(
      `${this.resourceUrl}/${this.getServiceSpecificationRefIdentifier(serviceSpecificationRef)}`,
      serviceSpecificationRef,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IServiceSpecificationRef>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceSpecificationRef[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServiceSpecificationRefIdentifier(serviceSpecificationRef: Pick<IServiceSpecificationRef, 'id'>): string {
    return serviceSpecificationRef.id;
  }

  compareServiceSpecificationRef(
    o1: Pick<IServiceSpecificationRef, 'id'> | null,
    o2: Pick<IServiceSpecificationRef, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getServiceSpecificationRefIdentifier(o1) === this.getServiceSpecificationRefIdentifier(o2) : o1 === o2;
  }

  addServiceSpecificationRefToCollectionIfMissing<Type extends Pick<IServiceSpecificationRef, 'id'>>(
    serviceSpecificationRefCollection: Type[],
    ...serviceSpecificationRefsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const serviceSpecificationRefs: Type[] = serviceSpecificationRefsToCheck.filter(isPresent);
    if (serviceSpecificationRefs.length > 0) {
      const serviceSpecificationRefCollectionIdentifiers = serviceSpecificationRefCollection.map(
        serviceSpecificationRefItem => this.getServiceSpecificationRefIdentifier(serviceSpecificationRefItem)!
      );
      const serviceSpecificationRefsToAdd = serviceSpecificationRefs.filter(serviceSpecificationRefItem => {
        const serviceSpecificationRefIdentifier = this.getServiceSpecificationRefIdentifier(serviceSpecificationRefItem);
        if (serviceSpecificationRefCollectionIdentifiers.includes(serviceSpecificationRefIdentifier)) {
          return false;
        }
        serviceSpecificationRefCollectionIdentifiers.push(serviceSpecificationRefIdentifier);
        return true;
      });
      return [...serviceSpecificationRefsToAdd, ...serviceSpecificationRefCollection];
    }
    return serviceSpecificationRefCollection;
  }
}
