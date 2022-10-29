import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResourceSpecificationRefComponent } from './list/resource-specification-ref.component';
import { ResourceSpecificationRefDetailComponent } from './detail/resource-specification-ref-detail.component';
import { ResourceSpecificationRefUpdateComponent } from './update/resource-specification-ref-update.component';
import { ResourceSpecificationRefDeleteDialogComponent } from './delete/resource-specification-ref-delete-dialog.component';
import { ResourceSpecificationRefRoutingModule } from './route/resource-specification-ref-routing.module';

@NgModule({
  imports: [SharedModule, ResourceSpecificationRefRoutingModule],
  declarations: [
    ResourceSpecificationRefComponent,
    ResourceSpecificationRefDetailComponent,
    ResourceSpecificationRefUpdateComponent,
    ResourceSpecificationRefDeleteDialogComponent,
  ],
})
export class ResourceSpecificationRefModule {}
