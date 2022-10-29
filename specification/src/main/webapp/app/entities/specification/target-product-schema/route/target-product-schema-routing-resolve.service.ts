import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITargetProductSchema } from '../target-product-schema.model';
import { TargetProductSchemaService } from '../service/target-product-schema.service';

@Injectable({ providedIn: 'root' })
export class TargetProductSchemaRoutingResolveService implements Resolve<ITargetProductSchema | null> {
  constructor(protected service: TargetProductSchemaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITargetProductSchema | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((targetProductSchema: HttpResponse<ITargetProductSchema>) => {
          if (targetProductSchema.body) {
            return of(targetProductSchema.body);
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
