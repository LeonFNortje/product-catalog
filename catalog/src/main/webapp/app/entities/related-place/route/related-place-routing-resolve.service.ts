import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRelatedPlace } from '../related-place.model';
import { RelatedPlaceService } from '../service/related-place.service';

@Injectable({ providedIn: 'root' })
export class RelatedPlaceRoutingResolveService implements Resolve<IRelatedPlace | null> {
  constructor(protected service: RelatedPlaceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRelatedPlace | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((relatedPlace: HttpResponse<IRelatedPlace>) => {
          if (relatedPlace.body) {
            return of(relatedPlace.body);
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
