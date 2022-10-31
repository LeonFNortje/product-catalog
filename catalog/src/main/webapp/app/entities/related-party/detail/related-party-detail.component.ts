import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelatedParty } from '../related-party.model';

@Component({
  selector: 'jhi-related-party-detail',
  templateUrl: './related-party-detail.component.html',
})
export class RelatedPartyDetailComponent implements OnInit {
  relatedParty: IRelatedParty | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relatedParty }) => {
      this.relatedParty = relatedParty;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
