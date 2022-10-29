import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'characteristic-value-specification',
        data: { pageTitle: 'specificationApp.specificationCharacteristicValueSpecification.home.title' },
        loadChildren: () =>
          import('./specification/characteristic-value-specification/characteristic-value-specification.module').then(
            m => m.SpecificationCharacteristicValueSpecificationModule
          ),
      },
      {
        path: 'product-specification-characteristic-relationship',
        data: { pageTitle: 'specificationApp.specificationProductSpecificationCharacteristicRelationship.home.title' },
        loadChildren: () =>
          import(
            './specification/product-specification-characteristic-relationship/product-specification-characteristic-relationship.module'
          ).then(m => m.SpecificationProductSpecificationCharacteristicRelationshipModule),
      },
      {
        path: 'related-party',
        data: { pageTitle: 'specificationApp.specificationRelatedParty.home.title' },
        loadChildren: () => import('./specification/related-party/related-party.module').then(m => m.SpecificationRelatedPartyModule),
      },
      {
        path: 'product-specification',
        data: { pageTitle: 'specificationApp.specificationProductSpecification.home.title' },
        loadChildren: () =>
          import('./specification/product-specification/product-specification.module').then(m => m.SpecificationProductSpecificationModule),
      },
      {
        path: 'bundled-product-specification',
        data: { pageTitle: 'specificationApp.specificationBundledProductSpecification.home.title' },
        loadChildren: () =>
          import('./specification/bundled-product-specification/bundled-product-specification.module').then(
            m => m.SpecificationBundledProductSpecificationModule
          ),
      },
      {
        path: 'resource-specification-ref',
        data: { pageTitle: 'specificationApp.specificationResourceSpecificationRef.home.title' },
        loadChildren: () =>
          import('./specification/resource-specification-ref/resource-specification-ref.module').then(
            m => m.SpecificationResourceSpecificationRefModule
          ),
      },
      {
        path: 'attachment-ref-or-value',
        data: { pageTitle: 'specificationApp.specificationAttachmentRefOrValue.home.title' },
        loadChildren: () =>
          import('./specification/attachment-ref-or-value/attachment-ref-or-value.module').then(
            m => m.SpecificationAttachmentRefOrValueModule
          ),
      },
      {
        path: 'target-product-schema',
        data: { pageTitle: 'specificationApp.specificationTargetProductSchema.home.title' },
        loadChildren: () =>
          import('./specification/target-product-schema/target-product-schema.module').then(m => m.SpecificationTargetProductSchemaModule),
      },
      {
        path: 'product-specification-relationship',
        data: { pageTitle: 'specificationApp.specificationProductSpecificationRelationship.home.title' },
        loadChildren: () =>
          import('./specification/product-specification-relationship/product-specification-relationship.module').then(
            m => m.SpecificationProductSpecificationRelationshipModule
          ),
      },
      {
        path: 'product-specification-characteristic',
        data: { pageTitle: 'specificationApp.specificationProductSpecificationCharacteristic.home.title' },
        loadChildren: () =>
          import('./specification/product-specification-characteristic/product-specification-characteristic.module').then(
            m => m.SpecificationProductSpecificationCharacteristicModule
          ),
      },
      {
        path: 'service-specification-ref',
        data: { pageTitle: 'specificationApp.specificationServiceSpecificationRef.home.title' },
        loadChildren: () =>
          import('./specification/service-specification-ref/service-specification-ref.module').then(
            m => m.SpecificationServiceSpecificationRefModule
          ),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
