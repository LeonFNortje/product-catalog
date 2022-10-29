import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductSpecificationCharacteristic } from '../product-specification-characteristic.model';
import { ProductSpecificationCharacteristicService } from '../service/product-specification-characteristic.service';

@Injectable({ providedIn: 'root' })
export class ProductSpecificationCharacteristicRoutingResolveService implements Resolve<IProductSpecificationCharacteristic | null> {
  constructor(protected service: ProductSpecificationCharacteristicService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductSpecificationCharacteristic | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productSpecificationCharacteristic: HttpResponse<IProductSpecificationCharacteristic>) => {
          if (productSpecificationCharacteristic.body) {
            return of(productSpecificationCharacteristic.body);
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
