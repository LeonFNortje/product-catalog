import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResourceSpecificationRefComponent } from '../list/resource-specification-ref.component';
import { ResourceSpecificationRefDetailComponent } from '../detail/resource-specification-ref-detail.component';
import { ResourceSpecificationRefUpdateComponent } from '../update/resource-specification-ref-update.component';
import { ResourceSpecificationRefRoutingResolveService } from './resource-specification-ref-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const resourceSpecificationRefRoute: Routes = [
  {
    path: '',
    component: ResourceSpecificationRefComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResourceSpecificationRefDetailComponent,
    resolve: {
      resourceSpecificationRef: ResourceSpecificationRefRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResourceSpecificationRefUpdateComponent,
    resolve: {
      resourceSpecificationRef: ResourceSpecificationRefRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResourceSpecificationRefUpdateComponent,
    resolve: {
      resourceSpecificationRef: ResourceSpecificationRefRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resourceSpecificationRefRoute)],
  exports: [RouterModule],
})
export class ResourceSpecificationRefRoutingModule {}
