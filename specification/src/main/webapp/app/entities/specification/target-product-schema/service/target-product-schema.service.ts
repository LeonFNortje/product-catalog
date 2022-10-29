import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITargetProductSchema, NewTargetProductSchema } from '../target-product-schema.model';

export type PartialUpdateTargetProductSchema = Partial<ITargetProductSchema> & Pick<ITargetProductSchema, 'id'>;

export type EntityResponseType = HttpResponse<ITargetProductSchema>;
export type EntityArrayResponseType = HttpResponse<ITargetProductSchema[]>;

@Injectable({ providedIn: 'root' })
export class TargetProductSchemaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/target-product-schemas', 'specification');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(targetProductSchema: NewTargetProductSchema): Observable<EntityResponseType> {
    return this.http.post<ITargetProductSchema>(this.resourceUrl, targetProductSchema, { observe: 'response' });
  }

  update(targetProductSchema: ITargetProductSchema): Observable<EntityResponseType> {
    return this.http.put<ITargetProductSchema>(
      `${this.resourceUrl}/${this.getTargetProductSchemaIdentifier(targetProductSchema)}`,
      targetProductSchema,
      { observe: 'response' }
    );
  }

  partialUpdate(targetProductSchema: PartialUpdateTargetProductSchema): Observable<EntityResponseType> {
    return this.http.patch<ITargetProductSchema>(
      `${this.resourceUrl}/${this.getTargetProductSchemaIdentifier(targetProductSchema)}`,
      targetProductSchema,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITargetProductSchema>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITargetProductSchema[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTargetProductSchemaIdentifier(targetProductSchema: Pick<ITargetProductSchema, 'id'>): number {
    return targetProductSchema.id;
  }

  compareTargetProductSchema(o1: Pick<ITargetProductSchema, 'id'> | null, o2: Pick<ITargetProductSchema, 'id'> | null): boolean {
    return o1 && o2 ? this.getTargetProductSchemaIdentifier(o1) === this.getTargetProductSchemaIdentifier(o2) : o1 === o2;
  }

  addTargetProductSchemaToCollectionIfMissing<Type extends Pick<ITargetProductSchema, 'id'>>(
    targetProductSchemaCollection: Type[],
    ...targetProductSchemasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const targetProductSchemas: Type[] = targetProductSchemasToCheck.filter(isPresent);
    if (targetProductSchemas.length > 0) {
      const targetProductSchemaCollectionIdentifiers = targetProductSchemaCollection.map(
        targetProductSchemaItem => this.getTargetProductSchemaIdentifier(targetProductSchemaItem)!
      );
      const targetProductSchemasToAdd = targetProductSchemas.filter(targetProductSchemaItem => {
        const targetProductSchemaIdentifier = this.getTargetProductSchemaIdentifier(targetProductSchemaItem);
        if (targetProductSchemaCollectionIdentifiers.includes(targetProductSchemaIdentifier)) {
          return false;
        }
        targetProductSchemaCollectionIdentifiers.push(targetProductSchemaIdentifier);
        return true;
      });
      return [...targetProductSchemasToAdd, ...targetProductSchemaCollection];
    }
    return targetProductSchemaCollection;
  }
}
