import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBundledProductSpecification } from '../bundled-product-specification.model';
import { BundledProductSpecificationService } from '../service/bundled-product-specification.service';

@Injectable({ providedIn: 'root' })
export class BundledProductSpecificationRoutingResolveService implements Resolve<IBundledProductSpecification | null> {
  constructor(protected service: BundledProductSpecificationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBundledProductSpecification | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bundledProductSpecification: HttpResponse<IBundledProductSpecification>) => {
          if (bundledProductSpecification.body) {
            return of(bundledProductSpecification.body);
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
