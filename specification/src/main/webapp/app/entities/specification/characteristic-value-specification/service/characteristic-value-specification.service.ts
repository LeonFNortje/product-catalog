import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICharacteristicValueSpecification, NewCharacteristicValueSpecification } from '../characteristic-value-specification.model';

export type PartialUpdateCharacteristicValueSpecification = Partial<ICharacteristicValueSpecification> &
  Pick<ICharacteristicValueSpecification, 'id'>;

type RestOf<T extends ICharacteristicValueSpecification | NewCharacteristicValueSpecification> = Omit<T, 'validForFrom' | 'validForTo'> & {
  validForFrom?: string | null;
  validForTo?: string | null;
};

export type RestCharacteristicValueSpecification = RestOf<ICharacteristicValueSpecification>;

export type NewRestCharacteristicValueSpecification = RestOf<NewCharacteristicValueSpecification>;

export type PartialUpdateRestCharacteristicValueSpecification = RestOf<PartialUpdateCharacteristicValueSpecification>;

export type EntityResponseType = HttpResponse<ICharacteristicValueSpecification>;
export type EntityArrayResponseType = HttpResponse<ICharacteristicValueSpecification[]>;

@Injectable({ providedIn: 'root' })
export class CharacteristicValueSpecificationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/characteristic-value-specifications', 'specification');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(characteristicValueSpecification: NewCharacteristicValueSpecification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(characteristicValueSpecification);
    return this.http
      .post<RestCharacteristicValueSpecification>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(characteristicValueSpecification: ICharacteristicValueSpecification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(characteristicValueSpecification);
    return this.http
      .put<RestCharacteristicValueSpecification>(
        `${this.resourceUrl}/${this.getCharacteristicValueSpecificationIdentifier(characteristicValueSpecification)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(characteristicValueSpecification: PartialUpdateCharacteristicValueSpecification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(characteristicValueSpecification);
    return this.http
      .patch<RestCharacteristicValueSpecification>(
        `${this.resourceUrl}/${this.getCharacteristicValueSpecificationIdentifier(characteristicValueSpecification)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCharacteristicValueSpecification>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCharacteristicValueSpecification[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCharacteristicValueSpecificationIdentifier(characteristicValueSpecification: Pick<ICharacteristicValueSpecification, 'id'>): number {
    return characteristicValueSpecification.id;
  }

  compareCharacteristicValueSpecification(
    o1: Pick<ICharacteristicValueSpecification, 'id'> | null,
    o2: Pick<ICharacteristicValueSpecification, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getCharacteristicValueSpecificationIdentifier(o1) === this.getCharacteristicValueSpecificationIdentifier(o2)
      : o1 === o2;
  }

  addCharacteristicValueSpecificationToCollectionIfMissing<Type extends Pick<ICharacteristicValueSpecification, 'id'>>(
    characteristicValueSpecificationCollection: Type[],
    ...characteristicValueSpecificationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const characteristicValueSpecifications: Type[] = characteristicValueSpecificationsToCheck.filter(isPresent);
    if (characteristicValueSpecifications.length > 0) {
      const characteristicValueSpecificationCollectionIdentifiers = characteristicValueSpecificationCollection.map(
        characteristicValueSpecificationItem => this.getCharacteristicValueSpecificationIdentifier(characteristicValueSpecificationItem)!
      );
      const characteristicValueSpecificationsToAdd = characteristicValueSpecifications.filter(characteristicValueSpecificationItem => {
        const characteristicValueSpecificationIdentifier = this.getCharacteristicValueSpecificationIdentifier(
          characteristicValueSpecificationItem
        );
        if (characteristicValueSpecificationCollectionIdentifiers.includes(characteristicValueSpecificationIdentifier)) {
          return false;
        }
        characteristicValueSpecificationCollectionIdentifiers.push(characteristicValueSpecificationIdentifier);
        return true;
      });
      return [...characteristicValueSpecificationsToAdd, ...characteristicValueSpecificationCollection];
    }
    return characteristicValueSpecificationCollection;
  }

  protected convertDateFromClient<
    T extends ICharacteristicValueSpecification | NewCharacteristicValueSpecification | PartialUpdateCharacteristicValueSpecification
  >(characteristicValueSpecification: T): RestOf<T> {
    return {
      ...characteristicValueSpecification,
      validForFrom: characteristicValueSpecification.validForFrom?.toJSON() ?? null,
      validForTo: characteristicValueSpecification.validForTo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restCharacteristicValueSpecification: RestCharacteristicValueSpecification
  ): ICharacteristicValueSpecification {
    return {
      ...restCharacteristicValueSpecification,
      validForFrom: restCharacteristicValueSpecification.validForFrom
        ? dayjs(restCharacteristicValueSpecification.validForFrom)
        : undefined,
      validForTo: restCharacteristicValueSpecification.validForTo ? dayjs(restCharacteristicValueSpecification.validForTo) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestCharacteristicValueSpecification>
  ): HttpResponse<ICharacteristicValueSpecification> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestCharacteristicValueSpecification[]>
  ): HttpResponse<ICharacteristicValueSpecification[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
