import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductSpecificationCharacteristicRelationshipComponent } from '../list/product-specification-characteristic-relationship.component';
import { ProductSpecificationCharacteristicRelationshipDetailComponent } from '../detail/product-specification-characteristic-relationship-detail.component';
import { ProductSpecificationCharacteristicRelationshipUpdateComponent } from '../update/product-specification-characteristic-relationship-update.component';
import { ProductSpecificationCharacteristicRelationshipRoutingResolveService } from './product-specification-characteristic-relationship-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productSpecificationCharacteristicRelationshipRoute: Routes = [
  {
    path: '',
    component: ProductSpecificationCharacteristicRelationshipComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductSpecificationCharacteristicRelationshipDetailComponent,
    resolve: {
      productSpecificationCharacteristicRelationship: ProductSpecificationCharacteristicRelationshipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductSpecificationCharacteristicRelationshipUpdateComponent,
    resolve: {
      productSpecificationCharacteristicRelationship: ProductSpecificationCharacteristicRelationshipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductSpecificationCharacteristicRelationshipUpdateComponent,
    resolve: {
      productSpecificationCharacteristicRelationship: ProductSpecificationCharacteristicRelationshipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productSpecificationCharacteristicRelationshipRoute)],
  exports: [RouterModule],
})
export class ProductSpecificationCharacteristicRelationshipRoutingModule {}
