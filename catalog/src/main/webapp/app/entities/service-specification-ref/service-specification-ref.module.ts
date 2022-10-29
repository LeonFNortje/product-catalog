import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ServiceSpecificationRefComponent } from './list/service-specification-ref.component';
import { ServiceSpecificationRefDetailComponent } from './detail/service-specification-ref-detail.component';
import { ServiceSpecificationRefUpdateComponent } from './update/service-specification-ref-update.component';
import { ServiceSpecificationRefDeleteDialogComponent } from './delete/service-specification-ref-delete-dialog.component';
import { ServiceSpecificationRefRoutingModule } from './route/service-specification-ref-routing.module';

@NgModule({
  imports: [SharedModule, ServiceSpecificationRefRoutingModule],
  declarations: [
    ServiceSpecificationRefComponent,
    ServiceSpecificationRefDetailComponent,
    ServiceSpecificationRefUpdateComponent,
    ServiceSpecificationRefDeleteDialogComponent,
  ],
})
export class ServiceSpecificationRefModule {}
