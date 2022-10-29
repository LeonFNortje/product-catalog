import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductSpecificationRelationship } from '../product-specification-relationship.model';
import { ProductSpecificationRelationshipService } from '../service/product-specification-relationship.service';

@Injectable({ providedIn: 'root' })
export class ProductSpecificationRelationshipRoutingResolveService implements Resolve<IProductSpecificationRelationship | null> {
  constructor(protected service: ProductSpecificationRelationshipService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductSpecificationRelationship | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productSpecificationRelationship: HttpResponse<IProductSpecificationRelationship>) => {
          if (productSpecificationRelationship.body) {
            return of(productSpecificationRelationship.body);
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
