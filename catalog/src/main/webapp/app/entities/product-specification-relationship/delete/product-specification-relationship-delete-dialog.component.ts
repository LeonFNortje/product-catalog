import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductSpecificationRelationship } from '../product-specification-relationship.model';
import { ProductSpecificationRelationshipService } from '../service/product-specification-relationship.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './product-specification-relationship-delete-dialog.component.html',
})
export class ProductSpecificationRelationshipDeleteDialogComponent {
  productSpecificationRelationship?: IProductSpecificationRelationship;

  constructor(
    protected productSpecificationRelationshipService: ProductSpecificationRelationshipService,
    protected activeModal: NgbActiveModal
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.productSpecificationRelationshipService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
