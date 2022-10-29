import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITargetProductSchema } from '../target-product-schema.model';
import { TargetProductSchemaService } from '../service/target-product-schema.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './target-product-schema-delete-dialog.component.html',
})
export class TargetProductSchemaDeleteDialogComponent {
  targetProductSchema?: ITargetProductSchema;

  constructor(protected targetProductSchemaService: TargetProductSchemaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.targetProductSchemaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
