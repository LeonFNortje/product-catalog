import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProductSpecificationFormService, ProductSpecificationFormGroup } from './product-specification-form.service';
import { IProductSpecification } from '../product-specification.model';
import { ProductSpecificationService } from '../service/product-specification.service';
import { ITargetProductSchema } from 'app/entities/target-product-schema/target-product-schema.model';
import { TargetProductSchemaService } from 'app/entities/target-product-schema/service/target-product-schema.service';
import { IResourceSpecificationRef } from 'app/entities/resource-specification-ref/resource-specification-ref.model';
import { ResourceSpecificationRefService } from 'app/entities/resource-specification-ref/service/resource-specification-ref.service';
import { IAttachmentRefOrValue } from 'app/entities/attachment-ref-or-value/attachment-ref-or-value.model';
import { AttachmentRefOrValueService } from 'app/entities/attachment-ref-or-value/service/attachment-ref-or-value.service';
import { IRelatedParty } from 'app/entities/related-party/related-party.model';
import { RelatedPartyService } from 'app/entities/related-party/service/related-party.service';
import { IServiceSpecificationRef } from 'app/entities/service-specification-ref/service-specification-ref.model';
import { ServiceSpecificationRefService } from 'app/entities/service-specification-ref/service/service-specification-ref.service';
import { IProductSpecificationRelationship } from 'app/entities/product-specification-relationship/product-specification-relationship.model';
import { ProductSpecificationRelationshipService } from 'app/entities/product-specification-relationship/service/product-specification-relationship.service';
import { IBundledProductSpecification } from 'app/entities/bundled-product-specification/bundled-product-specification.model';
import { BundledProductSpecificationService } from 'app/entities/bundled-product-specification/service/bundled-product-specification.service';
import { IProductSpecificationCharacteristic } from 'app/entities/product-specification-characteristic/product-specification-characteristic.model';
import { ProductSpecificationCharacteristicService } from 'app/entities/product-specification-characteristic/service/product-specification-characteristic.service';

@Component({
  selector: 'jhi-product-specification-update',
  templateUrl: './product-specification-update.component.html',
})
export class ProductSpecificationUpdateComponent implements OnInit {
  isSaving = false;
  productSpecification: IProductSpecification | null = null;

  targetProductSchemasSharedCollection: ITargetProductSchema[] = [];
  resourceSpecificationRefsSharedCollection: IResourceSpecificationRef[] = [];
  attachmentRefOrValuesSharedCollection: IAttachmentRefOrValue[] = [];
  relatedPartiesSharedCollection: IRelatedParty[] = [];
  serviceSpecificationRefsSharedCollection: IServiceSpecificationRef[] = [];
  productSpecificationRelationshipsSharedCollection: IProductSpecificationRelationship[] = [];
  bundledProductSpecificationsSharedCollection: IBundledProductSpecification[] = [];
  productSpecificationCharacteristicsSharedCollection: IProductSpecificationCharacteristic[] = [];

  editForm: ProductSpecificationFormGroup = this.productSpecificationFormService.createProductSpecificationFormGroup();

  constructor(
    protected productSpecificationService: ProductSpecificationService,
    protected productSpecificationFormService: ProductSpecificationFormService,
    protected targetProductSchemaService: TargetProductSchemaService,
    protected resourceSpecificationRefService: ResourceSpecificationRefService,
    protected attachmentRefOrValueService: AttachmentRefOrValueService,
    protected relatedPartyService: RelatedPartyService,
    protected serviceSpecificationRefService: ServiceSpecificationRefService,
    protected productSpecificationRelationshipService: ProductSpecificationRelationshipService,
    protected bundledProductSpecificationService: BundledProductSpecificationService,
    protected productSpecificationCharacteristicService: ProductSpecificationCharacteristicService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTargetProductSchema = (o1: ITargetProductSchema | null, o2: ITargetProductSchema | null): boolean =>
    this.targetProductSchemaService.compareTargetProductSchema(o1, o2);

  compareResourceSpecificationRef = (o1: IResourceSpecificationRef | null, o2: IResourceSpecificationRef | null): boolean =>
    this.resourceSpecificationRefService.compareResourceSpecificationRef(o1, o2);

  compareAttachmentRefOrValue = (o1: IAttachmentRefOrValue | null, o2: IAttachmentRefOrValue | null): boolean =>
    this.attachmentRefOrValueService.compareAttachmentRefOrValue(o1, o2);

  compareRelatedParty = (o1: IRelatedParty | null, o2: IRelatedParty | null): boolean =>
    this.relatedPartyService.compareRelatedParty(o1, o2);

  compareServiceSpecificationRef = (o1: IServiceSpecificationRef | null, o2: IServiceSpecificationRef | null): boolean =>
    this.serviceSpecificationRefService.compareServiceSpecificationRef(o1, o2);

  compareProductSpecificationRelationship = (
    o1: IProductSpecificationRelationship | null,
    o2: IProductSpecificationRelationship | null
  ): boolean => this.productSpecificationRelationshipService.compareProductSpecificationRelationship(o1, o2);

  compareBundledProductSpecification = (o1: IBundledProductSpecification | null, o2: IBundledProductSpecification | null): boolean =>
    this.bundledProductSpecificationService.compareBundledProductSpecification(o1, o2);

  compareProductSpecificationCharacteristic = (
    o1: IProductSpecificationCharacteristic | null,
    o2: IProductSpecificationCharacteristic | null
  ): boolean => this.productSpecificationCharacteristicService.compareProductSpecificationCharacteristic(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSpecification }) => {
      this.productSpecification = productSpecification;
      if (productSpecification) {
        this.updateForm(productSpecification);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productSpecification = this.productSpecificationFormService.getProductSpecification(this.editForm);
    if (productSpecification.id !== null) {
      this.subscribeToSaveResponse(this.productSpecificationService.update(productSpecification));
    } else {
      this.subscribeToSaveResponse(this.productSpecificationService.create(productSpecification));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSpecification>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(productSpecification: IProductSpecification): void {
    this.productSpecification = productSpecification;
    this.productSpecificationFormService.resetForm(this.editForm, productSpecification);

    this.targetProductSchemasSharedCollection =
      this.targetProductSchemaService.addTargetProductSchemaToCollectionIfMissing<ITargetProductSchema>(
        this.targetProductSchemasSharedCollection,
        productSpecification.targetProductSchema
      );
    this.resourceSpecificationRefsSharedCollection =
      this.resourceSpecificationRefService.addResourceSpecificationRefToCollectionIfMissing<IResourceSpecificationRef>(
        this.resourceSpecificationRefsSharedCollection,
        productSpecification.resourceSpecificationRef
      );
    this.attachmentRefOrValuesSharedCollection =
      this.attachmentRefOrValueService.addAttachmentRefOrValueToCollectionIfMissing<IAttachmentRefOrValue>(
        this.attachmentRefOrValuesSharedCollection,
        productSpecification.attachmentRefOrValue
      );
    this.relatedPartiesSharedCollection = this.relatedPartyService.addRelatedPartyToCollectionIfMissing<IRelatedParty>(
      this.relatedPartiesSharedCollection,
      productSpecification.relatedParty
    );
    this.serviceSpecificationRefsSharedCollection =
      this.serviceSpecificationRefService.addServiceSpecificationRefToCollectionIfMissing<IServiceSpecificationRef>(
        this.serviceSpecificationRefsSharedCollection,
        productSpecification.serviceSpecificationRef
      );
    this.productSpecificationRelationshipsSharedCollection =
      this.productSpecificationRelationshipService.addProductSpecificationRelationshipToCollectionIfMissing<IProductSpecificationRelationship>(
        this.productSpecificationRelationshipsSharedCollection,
        productSpecification.productSpecificationRelationship
      );
    this.bundledProductSpecificationsSharedCollection =
      this.bundledProductSpecificationService.addBundledProductSpecificationToCollectionIfMissing<IBundledProductSpecification>(
        this.bundledProductSpecificationsSharedCollection,
        productSpecification.bundledProductSpecification
      );
    this.productSpecificationCharacteristicsSharedCollection =
      this.productSpecificationCharacteristicService.addProductSpecificationCharacteristicToCollectionIfMissing<IProductSpecificationCharacteristic>(
        this.productSpecificationCharacteristicsSharedCollection,
        productSpecification.productSpecificationCharacteristic
      );
  }

  protected loadRelationshipsOptions(): void {
    this.targetProductSchemaService
      .query()
      .pipe(map((res: HttpResponse<ITargetProductSchema[]>) => res.body ?? []))
      .pipe(
        map((targetProductSchemas: ITargetProductSchema[]) =>
          this.targetProductSchemaService.addTargetProductSchemaToCollectionIfMissing<ITargetProductSchema>(
            targetProductSchemas,
            this.productSpecification?.targetProductSchema
          )
        )
      )
      .subscribe((targetProductSchemas: ITargetProductSchema[]) => (this.targetProductSchemasSharedCollection = targetProductSchemas));

    this.resourceSpecificationRefService
      .query()
      .pipe(map((res: HttpResponse<IResourceSpecificationRef[]>) => res.body ?? []))
      .pipe(
        map((resourceSpecificationRefs: IResourceSpecificationRef[]) =>
          this.resourceSpecificationRefService.addResourceSpecificationRefToCollectionIfMissing<IResourceSpecificationRef>(
            resourceSpecificationRefs,
            this.productSpecification?.resourceSpecificationRef
          )
        )
      )
      .subscribe(
        (resourceSpecificationRefs: IResourceSpecificationRef[]) =>
          (this.resourceSpecificationRefsSharedCollection = resourceSpecificationRefs)
      );

    this.attachmentRefOrValueService
      .query()
      .pipe(map((res: HttpResponse<IAttachmentRefOrValue[]>) => res.body ?? []))
      .pipe(
        map((attachmentRefOrValues: IAttachmentRefOrValue[]) =>
          this.attachmentRefOrValueService.addAttachmentRefOrValueToCollectionIfMissing<IAttachmentRefOrValue>(
            attachmentRefOrValues,
            this.productSpecification?.attachmentRefOrValue
          )
        )
      )
      .subscribe((attachmentRefOrValues: IAttachmentRefOrValue[]) => (this.attachmentRefOrValuesSharedCollection = attachmentRefOrValues));

    this.relatedPartyService
      .query()
      .pipe(map((res: HttpResponse<IRelatedParty[]>) => res.body ?? []))
      .pipe(
        map((relatedParties: IRelatedParty[]) =>
          this.relatedPartyService.addRelatedPartyToCollectionIfMissing<IRelatedParty>(
            relatedParties,
            this.productSpecification?.relatedParty
          )
        )
      )
      .subscribe((relatedParties: IRelatedParty[]) => (this.relatedPartiesSharedCollection = relatedParties));

    this.serviceSpecificationRefService
      .query()
      .pipe(map((res: HttpResponse<IServiceSpecificationRef[]>) => res.body ?? []))
      .pipe(
        map((serviceSpecificationRefs: IServiceSpecificationRef[]) =>
          this.serviceSpecificationRefService.addServiceSpecificationRefToCollectionIfMissing<IServiceSpecificationRef>(
            serviceSpecificationRefs,
            this.productSpecification?.serviceSpecificationRef
          )
        )
      )
      .subscribe(
        (serviceSpecificationRefs: IServiceSpecificationRef[]) => (this.serviceSpecificationRefsSharedCollection = serviceSpecificationRefs)
      );

    this.productSpecificationRelationshipService
      .query()
      .pipe(map((res: HttpResponse<IProductSpecificationRelationship[]>) => res.body ?? []))
      .pipe(
        map((productSpecificationRelationships: IProductSpecificationRelationship[]) =>
          this.productSpecificationRelationshipService.addProductSpecificationRelationshipToCollectionIfMissing<IProductSpecificationRelationship>(
            productSpecificationRelationships,
            this.productSpecification?.productSpecificationRelationship
          )
        )
      )
      .subscribe(
        (productSpecificationRelationships: IProductSpecificationRelationship[]) =>
          (this.productSpecificationRelationshipsSharedCollection = productSpecificationRelationships)
      );

    this.bundledProductSpecificationService
      .query()
      .pipe(map((res: HttpResponse<IBundledProductSpecification[]>) => res.body ?? []))
      .pipe(
        map((bundledProductSpecifications: IBundledProductSpecification[]) =>
          this.bundledProductSpecificationService.addBundledProductSpecificationToCollectionIfMissing<IBundledProductSpecification>(
            bundledProductSpecifications,
            this.productSpecification?.bundledProductSpecification
          )
        )
      )
      .subscribe(
        (bundledProductSpecifications: IBundledProductSpecification[]) =>
          (this.bundledProductSpecificationsSharedCollection = bundledProductSpecifications)
      );

    this.productSpecificationCharacteristicService
      .query()
      .pipe(map((res: HttpResponse<IProductSpecificationCharacteristic[]>) => res.body ?? []))
      .pipe(
        map((productSpecificationCharacteristics: IProductSpecificationCharacteristic[]) =>
          this.productSpecificationCharacteristicService.addProductSpecificationCharacteristicToCollectionIfMissing<IProductSpecificationCharacteristic>(
            productSpecificationCharacteristics,
            this.productSpecification?.productSpecificationCharacteristic
          )
        )
      )
      .subscribe(
        (productSpecificationCharacteristics: IProductSpecificationCharacteristic[]) =>
          (this.productSpecificationCharacteristicsSharedCollection = productSpecificationCharacteristics)
      );
  }
}
