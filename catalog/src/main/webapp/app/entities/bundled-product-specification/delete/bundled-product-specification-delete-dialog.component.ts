import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBundledProductSpecification } from '../bundled-product-specification.model';
import { BundledProductSpecificationService } from '../service/bundled-product-specification.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './bundled-product-specification-delete-dialog.component.html',
})
export class BundledProductSpecificationDeleteDialogComponent {
  bundledProductSpecification?: IBundledProductSpecification;

  constructor(protected bundledProductSpecificationService: BundledProductSpecificationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.bundledProductSpecificationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
