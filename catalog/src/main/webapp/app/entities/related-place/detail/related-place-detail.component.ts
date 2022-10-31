import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelatedPlace } from '../related-place.model';

@Component({
  selector: 'jhi-related-place-detail',
  templateUrl: './related-place-detail.component.html',
})
export class RelatedPlaceDetailComponent implements OnInit {
  relatedPlace: IRelatedPlace | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relatedPlace }) => {
      this.relatedPlace = relatedPlace;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
