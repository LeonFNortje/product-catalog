import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TargetProductSchemaComponent } from '../list/target-product-schema.component';
import { TargetProductSchemaDetailComponent } from '../detail/target-product-schema-detail.component';
import { TargetProductSchemaUpdateComponent } from '../update/target-product-schema-update.component';
import { TargetProductSchemaRoutingResolveService } from './target-product-schema-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const targetProductSchemaRoute: Routes = [
  {
    path: '',
    component: TargetProductSchemaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TargetProductSchemaDetailComponent,
    resolve: {
      targetProductSchema: TargetProductSchemaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TargetProductSchemaUpdateComponent,
    resolve: {
      targetProductSchema: TargetProductSchemaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TargetProductSchemaUpdateComponent,
    resolve: {
      targetProductSchema: TargetProductSchemaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(targetProductSchemaRoute)],
  exports: [RouterModule],
})
export class TargetProductSchemaRoutingModule {}
