import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICharacteristicValueSpecification } from '../characteristic-value-specification.model';
import { CharacteristicValueSpecificationService } from '../service/characteristic-value-specification.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './characteristic-value-specification-delete-dialog.component.html',
})
export class CharacteristicValueSpecificationDeleteDialogComponent {
  characteristicValueSpecification?: ICharacteristicValueSpecification;

  constructor(
    protected characteristicValueSpecificationService: CharacteristicValueSpecificationService,
    protected activeModal: NgbActiveModal
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.characteristicValueSpecificationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
