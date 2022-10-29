import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductSpecificationCharacteristicRelationship } from '../product-specification-characteristic-relationship.model';
import { ProductSpecificationCharacteristicRelationshipService } from '../service/product-specification-characteristic-relationship.service';

@Injectable({ providedIn: 'root' })
export class ProductSpecificationCharacteristicRelationshipRoutingResolveService
  implements Resolve<IProductSpecificationCharacteristicRelationship | null>
{
  constructor(protected service: ProductSpecificationCharacteristicRelationshipService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductSpecificationCharacteristicRelationship | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productSpecificationCharacteristicRelationship: HttpResponse<IProductSpecificationCharacteristicRelationship>) => {
          if (productSpecificationCharacteristicRelationship.body) {
            return of(productSpecificationCharacteristicRelationship.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
