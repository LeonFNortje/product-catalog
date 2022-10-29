import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BundledProductSpecificationComponent } from '../list/bundled-product-specification.component';
import { BundledProductSpecificationDetailComponent } from '../detail/bundled-product-specification-detail.component';
import { BundledProductSpecificationUpdateComponent } from '../update/bundled-product-specification-update.component';
import { BundledProductSpecificationRoutingResolveService } from './bundled-product-specification-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const bundledProductSpecificationRoute: Routes = [
  {
    path: '',
    component: BundledProductSpecificationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BundledProductSpecificationDetailComponent,
    resolve: {
      bundledProductSpecification: BundledProductSpecificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BundledProductSpecificationUpdateComponent,
    resolve: {
      bundledProductSpecification: BundledProductSpecificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BundledProductSpecificationUpdateComponent,
    resolve: {
      bundledProductSpecification: BundledProductSpecificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bundledProductSpecificationRoute)],
  exports: [RouterModule],
})
export class BundledProductSpecificationRoutingModule {}
