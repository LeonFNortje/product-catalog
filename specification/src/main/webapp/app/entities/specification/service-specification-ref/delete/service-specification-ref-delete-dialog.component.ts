import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IServiceSpecificationRef } from '../service-specification-ref.model';
import { ServiceSpecificationRefService } from '../service/service-specification-ref.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './service-specification-ref-delete-dialog.component.html',
})
export class ServiceSpecificationRefDeleteDialogComponent {
  serviceSpecificationRef?: IServiceSpecificationRef;

  constructor(protected serviceSpecificationRefService: ServiceSpecificationRefService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.serviceSpecificationRefService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
