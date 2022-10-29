import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAttachmentRefOrValue } from '../attachment-ref-or-value.model';
import { AttachmentRefOrValueService } from '../service/attachment-ref-or-value.service';

@Injectable({ providedIn: 'root' })
export class AttachmentRefOrValueRoutingResolveService implements Resolve<IAttachmentRefOrValue | null> {
  constructor(protected service: AttachmentRefOrValueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttachmentRefOrValue | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((attachmentRefOrValue: HttpResponse<IAttachmentRefOrValue>) => {
          if (attachmentRefOrValue.body) {
            return of(attachmentRefOrValue.body);
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
