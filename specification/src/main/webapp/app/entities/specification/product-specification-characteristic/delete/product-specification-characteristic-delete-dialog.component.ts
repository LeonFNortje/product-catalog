import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductSpecificationCharacteristic } from '../product-specification-characteristic.model';
import { ProductSpecificationCharacteristicService } from '../service/product-specification-characteristic.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './product-specification-characteristic-delete-dialog.component.html',
})
export class ProductSpecificationCharacteristicDeleteDialogComponent {
  productSpecificationCharacteristic?: IProductSpecificationCharacteristic;

  constructor(
    protected productSpecificationCharacteristicService: ProductSpecificationCharacteristicService,
    protected activeModal: NgbActiveModal
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.productSpecificationCharacteristicService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
