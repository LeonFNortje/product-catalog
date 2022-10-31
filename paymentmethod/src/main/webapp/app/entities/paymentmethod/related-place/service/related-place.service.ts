import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRelatedPlace, NewRelatedPlace } from '../related-place.model';

export type PartialUpdateRelatedPlace = Partial<IRelatedPlace> & Pick<IRelatedPlace, 'id'>;

export type EntityResponseType = HttpResponse<IRelatedPlace>;
export type EntityArrayResponseType = HttpResponse<IRelatedPlace[]>;

@Injectable({ providedIn: 'root' })
export class RelatedPlaceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/related-places', 'paymentmethod');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(relatedPlace: NewRelatedPlace): Observable<EntityResponseType> {
    return this.http.post<IRelatedPlace>(this.resourceUrl, relatedPlace, { observe: 'response' });
  }

  update(relatedPlace: IRelatedPlace): Observable<EntityResponseType> {
    return this.http.put<IRelatedPlace>(`${this.resourceUrl}/${this.getRelatedPlaceIdentifier(relatedPlace)}`, relatedPlace, {
      observe: 'response',
    });
  }

  partialUpdate(relatedPlace: PartialUpdateRelatedPlace): Observable<EntityResponseType> {
    return this.http.patch<IRelatedPlace>(`${this.resourceUrl}/${this.getRelatedPlaceIdentifier(relatedPlace)}`, relatedPlace, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IRelatedPlace>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRelatedPlace[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRelatedPlaceIdentifier(relatedPlace: Pick<IRelatedPlace, 'id'>): string {
    return relatedPlace.id;
  }

  compareRelatedPlace(o1: Pick<IRelatedPlace, 'id'> | null, o2: Pick<IRelatedPlace, 'id'> | null): boolean {
    return o1 && o2 ? this.getRelatedPlaceIdentifier(o1) === this.getRelatedPlaceIdentifier(o2) : o1 === o2;
  }

  addRelatedPlaceToCollectionIfMissing<Type extends Pick<IRelatedPlace, 'id'>>(
    relatedPlaceCollection: Type[],
    ...relatedPlacesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const relatedPlaces: Type[] = relatedPlacesToCheck.filter(isPresent);
    if (relatedPlaces.length > 0) {
      const relatedPlaceCollectionIdentifiers = relatedPlaceCollection.map(
        relatedPlaceItem => this.getRelatedPlaceIdentifier(relatedPlaceItem)!
      );
      const relatedPlacesToAdd = relatedPlaces.filter(relatedPlaceItem => {
        const relatedPlaceIdentifier = this.getRelatedPlaceIdentifier(relatedPlaceItem);
        if (relatedPlaceCollectionIdentifiers.includes(relatedPlaceIdentifier)) {
          return false;
        }
        relatedPlaceCollectionIdentifiers.push(relatedPlaceIdentifier);
        return true;
      });
      return [...relatedPlacesToAdd, ...relatedPlaceCollection];
    }
    return relatedPlaceCollection;
  }
}
