import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RelatedPartyComponent } from '../list/related-party.component';
import { RelatedPartyDetailComponent } from '../detail/related-party-detail.component';
import { RelatedPartyUpdateComponent } from '../update/related-party-update.component';
import { RelatedPartyRoutingResolveService } from './related-party-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const relatedPartyRoute: Routes = [
  {
    path: '',
    component: RelatedPartyComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RelatedPartyDetailComponent,
    resolve: {
      relatedParty: RelatedPartyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RelatedPartyUpdateComponent,
    resolve: {
      relatedParty: RelatedPartyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RelatedPartyUpdateComponent,
    resolve: {
      relatedParty: RelatedPartyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(relatedPartyRoute)],
  exports: [RouterModule],
})
export class RelatedPartyRoutingModule {}
