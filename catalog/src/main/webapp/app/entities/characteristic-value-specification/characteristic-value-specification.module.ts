import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CharacteristicValueSpecificationComponent } from './list/characteristic-value-specification.component';
import { CharacteristicValueSpecificationDetailComponent } from './detail/characteristic-value-specification-detail.component';
import { CharacteristicValueSpecificationUpdateComponent } from './update/characteristic-value-specification-update.component';
import { CharacteristicValueSpecificationDeleteDialogComponent } from './delete/characteristic-value-specification-delete-dialog.component';
import { CharacteristicValueSpecificationRoutingModule } from './route/characteristic-value-specification-routing.module';

@NgModule({
  imports: [SharedModule, CharacteristicValueSpecificationRoutingModule],
  declarations: [
    CharacteristicValueSpecificationComponent,
    CharacteristicValueSpecificationDetailComponent,
    CharacteristicValueSpecificationUpdateComponent,
    CharacteristicValueSpecificationDeleteDialogComponent,
  ],
})
export class CharacteristicValueSpecificationModule {}
