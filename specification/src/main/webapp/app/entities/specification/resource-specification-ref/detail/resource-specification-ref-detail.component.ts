import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResourceSpecificationRef } from '../resource-specification-ref.model';

@Component({
  selector: 'jhi-resource-specification-ref-detail',
  templateUrl: './resource-specification-ref-detail.component.html',
})
export class ResourceSpecificationRefDetailComponent implements OnInit {
  resourceSpecificationRef: IResourceSpecificationRef | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resourceSpecificationRef }) => {
      this.resourceSpecificationRef = resourceSpecificationRef;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
