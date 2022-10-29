import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AttachmentRefOrValueComponent } from '../list/attachment-ref-or-value.component';
import { AttachmentRefOrValueDetailComponent } from '../detail/attachment-ref-or-value-detail.component';
import { AttachmentRefOrValueUpdateComponent } from '../update/attachment-ref-or-value-update.component';
import { AttachmentRefOrValueRoutingResolveService } from './attachment-ref-or-value-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const attachmentRefOrValueRoute: Routes = [
  {
    path: '',
    component: AttachmentRefOrValueComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttachmentRefOrValueDetailComponent,
    resolve: {
      attachmentRefOrValue: AttachmentRefOrValueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttachmentRefOrValueUpdateComponent,
    resolve: {
      attachmentRefOrValue: AttachmentRefOrValueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttachmentRefOrValueUpdateComponent,
    resolve: {
      attachmentRefOrValue: AttachmentRefOrValueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(attachmentRefOrValueRoute)],
  exports: [RouterModule],
})
export class AttachmentRefOrValueRoutingModule {}
