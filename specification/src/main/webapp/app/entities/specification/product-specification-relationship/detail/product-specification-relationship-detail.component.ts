import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductSpecificationRelationship } from '../product-specification-relationship.model';

@Component({
  selector: 'jhi-product-specification-relationship-detail',
  templateUrl: './product-specification-relationship-detail.component.html',
})
export class ProductSpecificationRelationshipDetailComponent implements OnInit {
  productSpecificationRelationship: IProductSpecificationRelationship | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSpecificationRelationship }) => {
      this.productSpecificationRelationship = productSpecificationRelationship;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
