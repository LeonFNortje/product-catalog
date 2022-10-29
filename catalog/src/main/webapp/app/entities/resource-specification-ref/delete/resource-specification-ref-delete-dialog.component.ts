import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResourceSpecificationRef } from '../resource-specification-ref.model';
import { ResourceSpecificationRefService } from '../service/resource-specification-ref.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './resource-specification-ref-delete-dialog.component.html',
})
export class ResourceSpecificationRefDeleteDialogComponent {
  resourceSpecificationRef?: IResourceSpecificationRef;

  constructor(protected resourceSpecificationRefService: ResourceSpecificationRefService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.resourceSpecificationRefService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
