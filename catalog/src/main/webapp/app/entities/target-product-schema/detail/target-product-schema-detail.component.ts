import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITargetProductSchema } from '../target-product-schema.model';

@Component({
  selector: 'jhi-target-product-schema-detail',
  templateUrl: './target-product-schema-detail.component.html',
})
export class TargetProductSchemaDetailComponent implements OnInit {
  targetProductSchema: ITargetProductSchema | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ targetProductSchema }) => {
      this.targetProductSchema = targetProductSchema;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
