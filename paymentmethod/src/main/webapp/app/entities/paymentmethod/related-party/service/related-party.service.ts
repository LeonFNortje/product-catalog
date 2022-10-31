import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRelatedParty, NewRelatedParty } from '../related-party.model';

export type PartialUpdateRelatedParty = Partial<IRelatedParty> & Pick<IRelatedParty, 'id'>;

export type EntityResponseType = HttpResponse<IRelatedParty>;
export type EntityArrayResponseType = HttpResponse<IRelatedParty[]>;

@Injectable({ providedIn: 'root' })
export class RelatedPartyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/related-parties', 'paymentmethod');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(relatedParty: NewRelatedParty): Observable<EntityResponseType> {
    return this.http.post<IRelatedParty>(this.resourceUrl, relatedParty, { observe: 'response' });
  }

  update(relatedParty: IRelatedParty): Observable<EntityResponseType> {
    return this.http.put<IRelatedParty>(`${this.resourceUrl}/${this.getRelatedPartyIdentifier(relatedParty)}`, relatedParty, {
      observe: 'response',
    });
  }

  partialUpdate(relatedParty: PartialUpdateRelatedParty): Observable<EntityResponseType> {
    return this.http.patch<IRelatedParty>(`${this.resourceUrl}/${this.getRelatedPartyIdentifier(relatedParty)}`, relatedParty, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IRelatedParty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRelatedParty[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRelatedPartyIdentifier(relatedParty: Pick<IRelatedParty, 'id'>): string {
    return relatedParty.id;
  }

  compareRelatedParty(o1: Pick<IRelatedParty, 'id'> | null, o2: Pick<IRelatedParty, 'id'> | null): boolean {
    return o1 && o2 ? this.getRelatedPartyIdentifier(o1) === this.getRelatedPartyIdentifier(o2) : o1 === o2;
  }

  addRelatedPartyToCollectionIfMissing<Type extends Pick<IRelatedParty, 'id'>>(
    relatedPartyCollection: Type[],
    ...relatedPartiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const relatedParties: Type[] = relatedPartiesToCheck.filter(isPresent);
    if (relatedParties.length > 0) {
      const relatedPartyCollectionIdentifiers = relatedPartyCollection.map(
        relatedPartyItem => this.getRelatedPartyIdentifier(relatedPartyItem)!
      );
      const relatedPartiesToAdd = relatedParties.filter(relatedPartyItem => {
        const relatedPartyIdentifier = this.getRelatedPartyIdentifier(relatedPartyItem);
        if (relatedPartyCollectionIdentifiers.includes(relatedPartyIdentifier)) {
          return false;
        }
        relatedPartyCollectionIdentifiers.push(relatedPartyIdentifier);
        return true;
      });
      return [...relatedPartiesToAdd, ...relatedPartyCollection];
    }
    return relatedPartyCollection;
  }
}
