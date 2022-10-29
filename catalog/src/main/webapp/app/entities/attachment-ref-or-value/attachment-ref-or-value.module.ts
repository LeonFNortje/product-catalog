import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AttachmentRefOrValueComponent } from './list/attachment-ref-or-value.component';
import { AttachmentRefOrValueDetailComponent } from './detail/attachment-ref-or-value-detail.component';
import { AttachmentRefOrValueUpdateComponent } from './update/attachment-ref-or-value-update.component';
import { AttachmentRefOrValueDeleteDialogComponent } from './delete/attachment-ref-or-value-delete-dialog.component';
import { AttachmentRefOrValueRoutingModule } from './route/attachment-ref-or-value-routing.module';

@NgModule({
  imports: [SharedModule, AttachmentRefOrValueRoutingModule],
  declarations: [
    AttachmentRefOrValueComponent,
    AttachmentRefOrValueDetailComponent,
    AttachmentRefOrValueUpdateComponent,
    AttachmentRefOrValueDeleteDialogComponent,
  ],
})
export class AttachmentRefOrValueModule {}
