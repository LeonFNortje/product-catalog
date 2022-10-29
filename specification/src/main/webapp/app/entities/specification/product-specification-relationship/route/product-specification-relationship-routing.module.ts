import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductSpecificationRelationshipComponent } from '../list/product-specification-relationship.component';
import { ProductSpecificationRelationshipDetailComponent } from '../detail/product-specification-relationship-detail.component';
import { ProductSpecificationRelationshipUpdateComponent } from '../update/product-specification-relationship-update.component';
import { ProductSpecificationRelationshipRoutingResolveService } from './product-specification-relationship-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productSpecificationRelationshipRoute: Routes = [
  {
    path: '',
    component: ProductSpecificationRelationshipComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductSpecificationRelationshipDetailComponent,
    resolve: {
      productSpecificationRelationship: ProductSpecificationRelationshipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductSpecificationRelationshipUpdateComponent,
    resolve: {
      productSpecificationRelationship: ProductSpecificationRelationshipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductSpecificationRelationshipUpdateComponent,
    resolve: {
      productSpecificationRelationship: ProductSpecificationRelationshipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productSpecificationRelationshipRoute)],
  exports: [RouterModule],
})
export class ProductSpecificationRelationshipRoutingModule {}
