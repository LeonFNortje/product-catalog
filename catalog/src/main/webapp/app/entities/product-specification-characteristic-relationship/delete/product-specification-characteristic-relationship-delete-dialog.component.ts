import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductSpecificationCharacteristicRelationship } from '../product-specification-characteristic-relationship.model';
import { ProductSpecificationCharacteristicRelationshipService } from '../service/product-specification-characteristic-relationship.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './product-specification-characteristic-relationship-delete-dialog.component.html',
})
export class ProductSpecificationCharacteristicRelationshipDeleteDialogComponent {
  productSpecificationCharacteristicRelationship?: IProductSpecificationCharacteristicRelationship;

  constructor(
    protected productSpecificationCharacteristicRelationshipService: ProductSpecificationCharacteristicRelationshipService,
    protected activeModal: NgbActiveModal
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.productSpecificationCharacteristicRelationshipService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
