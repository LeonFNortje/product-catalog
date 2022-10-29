import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductSpecificationCharacteristicRelationshipComponent } from './list/product-specification-characteristic-relationship.component';
import { ProductSpecificationCharacteristicRelationshipDetailComponent } from './detail/product-specification-characteristic-relationship-detail.component';
import { ProductSpecificationCharacteristicRelationshipUpdateComponent } from './update/product-specification-characteristic-relationship-update.component';
import { ProductSpecificationCharacteristicRelationshipDeleteDialogComponent } from './delete/product-specification-characteristic-relationship-delete-dialog.component';
import { ProductSpecificationCharacteristicRelationshipRoutingModule } from './route/product-specification-characteristic-relationship-routing.module';

@NgModule({
  imports: [SharedModule, ProductSpecificationCharacteristicRelationshipRoutingModule],
  declarations: [
    ProductSpecificationCharacteristicRelationshipComponent,
    ProductSpecificationCharacteristicRelationshipDetailComponent,
    ProductSpecificationCharacteristicRelationshipUpdateComponent,
    ProductSpecificationCharacteristicRelationshipDeleteDialogComponent,
  ],
})
export class ProductSpecificationCharacteristicRelationshipModule {}
