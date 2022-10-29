import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductSpecificationCharacteristicComponent } from './list/product-specification-characteristic.component';
import { ProductSpecificationCharacteristicDetailComponent } from './detail/product-specification-characteristic-detail.component';
import { ProductSpecificationCharacteristicUpdateComponent } from './update/product-specification-characteristic-update.component';
import { ProductSpecificationCharacteristicDeleteDialogComponent } from './delete/product-specification-characteristic-delete-dialog.component';
import { ProductSpecificationCharacteristicRoutingModule } from './route/product-specification-characteristic-routing.module';

@NgModule({
  imports: [SharedModule, ProductSpecificationCharacteristicRoutingModule],
  declarations: [
    ProductSpecificationCharacteristicComponent,
    ProductSpecificationCharacteristicDetailComponent,
    ProductSpecificationCharacteristicUpdateComponent,
    ProductSpecificationCharacteristicDeleteDialogComponent,
  ],
})
export class SpecificationProductSpecificationCharacteristicModule {}
