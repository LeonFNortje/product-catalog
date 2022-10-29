import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductSpecificationCharacteristicRelationship } from '../product-specification-characteristic-relationship.model';

@Component({
  selector: 'jhi-product-specification-characteristic-relationship-detail',
  templateUrl: './product-specification-characteristic-relationship-detail.component.html',
})
export class ProductSpecificationCharacteristicRelationshipDetailComponent implements OnInit {
  productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSpecificationCharacteristicRelationship }) => {
      this.productSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationship;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
