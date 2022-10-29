import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttachmentRefOrValue } from '../attachment-ref-or-value.model';
import { AttachmentRefOrValueService } from '../service/attachment-ref-or-value.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './attachment-ref-or-value-delete-dialog.component.html',
})
export class AttachmentRefOrValueDeleteDialogComponent {
  attachmentRefOrValue?: IAttachmentRefOrValue;

  constructor(protected attachmentRefOrValueService: AttachmentRefOrValueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.attachmentRefOrValueService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
