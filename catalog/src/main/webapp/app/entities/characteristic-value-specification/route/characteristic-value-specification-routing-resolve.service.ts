import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICharacteristicValueSpecification } from '../characteristic-value-specification.model';
import { CharacteristicValueSpecificationService } from '../service/characteristic-value-specification.service';

@Injectable({ providedIn: 'root' })
export class CharacteristicValueSpecificationRoutingResolveService implements Resolve<ICharacteristicValueSpecification | null> {
  constructor(protected service: CharacteristicValueSpecificationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICharacteristicValueSpecification | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((characteristicValueSpecification: HttpResponse<ICharacteristicValueSpecification>) => {
          if (characteristicValueSpecification.body) {
            return of(characteristicValueSpecification.body);
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
