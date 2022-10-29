import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttachmentRefOrValue } from '../attachment-ref-or-value.model';

@Component({
  selector: 'jhi-attachment-ref-or-value-detail',
  templateUrl: './attachment-ref-or-value-detail.component.html',
})
export class AttachmentRefOrValueDetailComponent implements OnInit {
  attachmentRefOrValue: IAttachmentRefOrValue | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attachmentRefOrValue }) => {
      this.attachmentRefOrValue = attachmentRefOrValue;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
