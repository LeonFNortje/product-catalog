import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductSpecificationCharacteristic } from '../product-specification-characteristic.model';

@Component({
  selector: 'jhi-product-specification-characteristic-detail',
  templateUrl: './product-specification-characteristic-detail.component.html',
})
export class ProductSpecificationCharacteristicDetailComponent implements OnInit {
  productSpecificationCharacteristic: IProductSpecificationCharacteristic | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSpecificationCharacteristic }) => {
      this.productSpecificationCharacteristic = productSpecificationCharacteristic;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
