import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ServiceSpecificationRefComponent } from '../list/service-specification-ref.component';
import { ServiceSpecificationRefDetailComponent } from '../detail/service-specification-ref-detail.component';
import { ServiceSpecificationRefUpdateComponent } from '../update/service-specification-ref-update.component';
import { ServiceSpecificationRefRoutingResolveService } from './service-specification-ref-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const serviceSpecificationRefRoute: Routes = [
  {
    path: '',
    component: ServiceSpecificationRefComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceSpecificationRefDetailComponent,
    resolve: {
      serviceSpecificationRef: ServiceSpecificationRefRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceSpecificationRefUpdateComponent,
    resolve: {
      serviceSpecificationRef: ServiceSpecificationRefRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceSpecificationRefUpdateComponent,
    resolve: {
      serviceSpecificationRef: ServiceSpecificationRefRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(serviceSpecificationRefRoute)],
  exports: [RouterModule],
})
export class ServiceSpecificationRefRoutingModule {}
