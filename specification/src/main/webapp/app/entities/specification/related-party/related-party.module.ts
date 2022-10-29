import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RelatedPartyComponent } from './list/related-party.component';
import { RelatedPartyDetailComponent } from './detail/related-party-detail.component';
import { RelatedPartyUpdateComponent } from './update/related-party-update.component';
import { RelatedPartyDeleteDialogComponent } from './delete/related-party-delete-dialog.component';
import { RelatedPartyRoutingModule } from './route/related-party-routing.module';

@NgModule({
  imports: [SharedModule, RelatedPartyRoutingModule],
  declarations: [RelatedPartyComponent, RelatedPartyDetailComponent, RelatedPartyUpdateComponent, RelatedPartyDeleteDialogComponent],
})
export class SpecificationRelatedPartyModule {}
