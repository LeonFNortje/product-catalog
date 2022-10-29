import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBundledProductSpecification } from '../bundled-product-specification.model';

@Component({
  selector: 'jhi-bundled-product-specification-detail',
  templateUrl: './bundled-product-specification-detail.component.html',
})
export class BundledProductSpecificationDetailComponent implements OnInit {
  bundledProductSpecification: IBundledProductSpecification | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bundledProductSpecification }) => {
      this.bundledProductSpecification = bundledProductSpecification;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
