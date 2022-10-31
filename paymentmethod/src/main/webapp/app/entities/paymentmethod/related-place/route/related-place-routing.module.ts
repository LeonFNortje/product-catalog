import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RelatedPlaceComponent } from '../list/related-place.component';
import { RelatedPlaceDetailComponent } from '../detail/related-place-detail.component';
import { RelatedPlaceUpdateComponent } from '../update/related-place-update.component';
import { RelatedPlaceRoutingResolveService } from './related-place-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const relatedPlaceRoute: Routes = [
  {
    path: '',
    component: RelatedPlaceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RelatedPlaceDetailComponent,
    resolve: {
      relatedPlace: RelatedPlaceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RelatedPlaceUpdateComponent,
    resolve: {
      relatedPlace: RelatedPlaceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RelatedPlaceUpdateComponent,
    resolve: {
      relatedPlace: RelatedPlaceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(relatedPlaceRoute)],
  exports: [RouterModule],
})
export class RelatedPlaceRoutingModule {}
