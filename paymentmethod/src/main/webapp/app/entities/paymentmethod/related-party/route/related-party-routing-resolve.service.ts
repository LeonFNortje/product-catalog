import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRelatedParty } from '../related-party.model';
import { RelatedPartyService } from '../service/related-party.service';

@Injectable({ providedIn: 'root' })
export class RelatedPartyRoutingResolveService implements Resolve<IRelatedParty | null> {
  constructor(protected service: RelatedPartyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRelatedParty | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((relatedParty: HttpResponse<IRelatedParty>) => {
          if (relatedParty.body) {
            return of(relatedParty.body);
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
