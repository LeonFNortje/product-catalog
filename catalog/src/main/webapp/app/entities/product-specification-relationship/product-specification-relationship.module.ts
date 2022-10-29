import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductSpecificationRelationshipComponent } from './list/product-specification-relationship.component';
import { ProductSpecificationRelationshipDetailComponent } from './detail/product-specification-relationship-detail.component';
import { ProductSpecificationRelationshipUpdateComponent } from './update/product-specification-relationship-update.component';
import { ProductSpecificationRelationshipDeleteDialogComponent } from './delete/product-specification-relationship-delete-dialog.component';
import { ProductSpecificationRelationshipRoutingModule } from './route/product-specification-relationship-routing.module';

@NgModule({
  imports: [SharedModule, ProductSpecificationRelationshipRoutingModule],
  declarations: [
    ProductSpecificationRelationshipComponent,
    ProductSpecificationRelationshipDetailComponent,
    ProductSpecificationRelationshipUpdateComponent,
    ProductSpecificationRelationshipDeleteDialogComponent,
  ],
})
export class ProductSpecificationRelationshipModule {}
