import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TargetProductSchemaComponent } from './list/target-product-schema.component';
import { TargetProductSchemaDetailComponent } from './detail/target-product-schema-detail.component';
import { TargetProductSchemaUpdateComponent } from './update/target-product-schema-update.component';
import { TargetProductSchemaDeleteDialogComponent } from './delete/target-product-schema-delete-dialog.component';
import { TargetProductSchemaRoutingModule } from './route/target-product-schema-routing.module';

@NgModule({
  imports: [SharedModule, TargetProductSchemaRoutingModule],
  declarations: [
    TargetProductSchemaComponent,
    TargetProductSchemaDetailComponent,
    TargetProductSchemaUpdateComponent,
    TargetProductSchemaDeleteDialogComponent,
  ],
})
export class SpecificationTargetProductSchemaModule {}
