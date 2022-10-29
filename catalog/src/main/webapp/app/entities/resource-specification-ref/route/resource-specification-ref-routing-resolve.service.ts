import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResourceSpecificationRef } from '../resource-specification-ref.model';
import { ResourceSpecificationRefService } from '../service/resource-specification-ref.service';

@Injectable({ providedIn: 'root' })
export class ResourceSpecificationRefRoutingResolveService implements Resolve<IResourceSpecificationRef | null> {
  constructor(protected service: ResourceSpecificationRefService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResourceSpecificationRef | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resourceSpecificationRef: HttpResponse<IResourceSpecificationRef>) => {
          if (resourceSpecificationRef.body) {
            return of(resourceSpecificationRef.body);
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
