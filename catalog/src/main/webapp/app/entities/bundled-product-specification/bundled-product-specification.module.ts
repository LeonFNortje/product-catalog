import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BundledProductSpecificationComponent } from './list/bundled-product-specification.component';
import { BundledProductSpecificationDetailComponent } from './detail/bundled-product-specification-detail.component';
import { BundledProductSpecificationUpdateComponent } from './update/bundled-product-specification-update.component';
import { BundledProductSpecificationDeleteDialogComponent } from './delete/bundled-product-specification-delete-dialog.component';
import { BundledProductSpecificationRoutingModule } from './route/bundled-product-specification-routing.module';

@NgModule({
  imports: [SharedModule, BundledProductSpecificationRoutingModule],
  declarations: [
    BundledProductSpecificationComponent,
    BundledProductSpecificationDetailComponent,
    BundledProductSpecificationUpdateComponent,
    BundledProductSpecificationDeleteDialogComponent,
  ],
})
export class BundledProductSpecificationModule {}
