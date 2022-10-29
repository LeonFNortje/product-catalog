import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICharacteristicValueSpecification } from '../characteristic-value-specification.model';

@Component({
  selector: 'jhi-characteristic-value-specification-detail',
  templateUrl: './characteristic-value-specification-detail.component.html',
})
export class CharacteristicValueSpecificationDetailComponent implements OnInit {
  characteristicValueSpecification: ICharacteristicValueSpecification | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ characteristicValueSpecification }) => {
      this.characteristicValueSpecification = characteristicValueSpecification;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
