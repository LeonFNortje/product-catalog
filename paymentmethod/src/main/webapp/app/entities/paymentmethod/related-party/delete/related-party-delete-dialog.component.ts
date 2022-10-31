import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRelatedParty } from '../related-party.model';
import { RelatedPartyService } from '../service/related-party.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './related-party-delete-dialog.component.html',
})
export class RelatedPartyDeleteDialogComponent {
  relatedParty?: IRelatedParty;

  constructor(protected relatedPartyService: RelatedPartyService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.relatedPartyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
