import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CharacteristicValueSpecificationComponent } from '../list/characteristic-value-specification.component';
import { CharacteristicValueSpecificationDetailComponent } from '../detail/characteristic-value-specification-detail.component';
import { CharacteristicValueSpecificationUpdateComponent } from '../update/characteristic-value-specification-update.component';
import { CharacteristicValueSpecificationRoutingResolveService } from './characteristic-value-specification-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const characteristicValueSpecificationRoute: Routes = [
  {
    path: '',
    component: CharacteristicValueSpecificationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CharacteristicValueSpecificationDetailComponent,
    resolve: {
      characteristicValueSpecification: CharacteristicValueSpecificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CharacteristicValueSpecificationUpdateComponent,
    resolve: {
      characteristicValueSpecification: CharacteristicValueSpecificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CharacteristicValueSpecificationUpdateComponent,
    resolve: {
      characteristicValueSpecification: CharacteristicValueSpecificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(characteristicValueSpecificationRoute)],
  exports: [RouterModule],
})
export class CharacteristicValueSpecificationRoutingModule {}
