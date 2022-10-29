import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceSpecificationRef } from '../service-specification-ref.model';

@Component({
  selector: 'jhi-service-specification-ref-detail',
  templateUrl: './service-specification-ref-detail.component.html',
})
export class ServiceSpecificationRefDetailComponent implements OnInit {
  serviceSpecificationRef: IServiceSpecificationRef | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceSpecificationRef }) => {
      this.serviceSpecificationRef = serviceSpecificationRef;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
