import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRelatedPlace } from '../related-place.model';
import { RelatedPlaceService } from '../service/related-place.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './related-place-delete-dialog.component.html',
})
export class RelatedPlaceDeleteDialogComponent {
  relatedPlace?: IRelatedPlace;

  constructor(protected relatedPlaceService: RelatedPlaceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.relatedPlaceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
