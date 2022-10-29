import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductSpecificationCharacteristicComponent } from '../list/product-specification-characteristic.component';
import { ProductSpecificationCharacteristicDetailComponent } from '../detail/product-specification-characteristic-detail.component';
import { ProductSpecificationCharacteristicUpdateComponent } from '../update/product-specification-characteristic-update.component';
import { ProductSpecificationCharacteristicRoutingResolveService } from './product-specification-characteristic-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productSpecificationCharacteristicRoute: Routes = [
  {
    path: '',
    component: ProductSpecificationCharacteristicComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductSpecificationCharacteristicDetailComponent,
    resolve: {
      productSpecificationCharacteristic: ProductSpecificationCharacteristicRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductSpecificationCharacteristicUpdateComponent,
    resolve: {
      productSpecificationCharacteristic: ProductSpecificationCharacteristicRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductSpecificationCharacteristicUpdateComponent,
    resolve: {
      productSpecificationCharacteristic: ProductSpecificationCharacteristicRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productSpecificationCharacteristicRoute)],
  exports: [RouterModule],
})
export class ProductSpecificationCharacteristicRoutingModule {}
