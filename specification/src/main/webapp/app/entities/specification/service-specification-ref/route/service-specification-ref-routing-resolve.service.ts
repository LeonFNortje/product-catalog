import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServiceSpecificationRef } from '../service-specification-ref.model';
import { ServiceSpecificationRefService } from '../service/service-specification-ref.service';

@Injectable({ providedIn: 'root' })
export class ServiceSpecificationRefRoutingResolveService implements Resolve<IServiceSpecificationRef | null> {
  constructor(protected service: ServiceSpecificationRefService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceSpecificationRef | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((serviceSpecificationRef: HttpResponse<IServiceSpecificationRef>) => {
          if (serviceSpecificationRef.body) {
            return of(serviceSpecificationRef.body);
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
