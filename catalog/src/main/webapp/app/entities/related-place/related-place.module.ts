import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RelatedPlaceComponent } from './list/related-place.component';
import { RelatedPlaceDetailComponent } from './detail/related-place-detail.component';
import { RelatedPlaceUpdateComponent } from './update/related-place-update.component';
import { RelatedPlaceDeleteDialogComponent } from './delete/related-place-delete-dialog.component';
import { RelatedPlaceRoutingModule } from './route/related-place-routing.module';

@NgModule({
  imports: [SharedModule, RelatedPlaceRoutingModule],
  declarations: [RelatedPlaceComponent, RelatedPlaceDetailComponent, RelatedPlaceUpdateComponent, RelatedPlaceDeleteDialogComponent],
})
export class RelatedPlaceModule {}
