import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'characteristic-value-specification',
        data: { pageTitle: 'catalogApp.characteristicValueSpecification.home.title' },
        loadChildren: () =>
          import('./characteristic-value-specification/characteristic-value-specification.module').then(
            m => m.CharacteristicValueSpecificationModule
          ),
      },
      {
        path: 'product-specification-characteristic-relationship',
        data: { pageTitle: 'catalogApp.productSpecificationCharacteristicRelationship.home.title' },
        loadChildren: () =>
          import('./product-specification-characteristic-relationship/product-specification-characteristic-relationship.module').then(
            m => m.ProductSpecificationCharacteristicRelationshipModule
          ),
      },
      {
        path: 'related-party',
        data: { pageTitle: 'catalogApp.relatedParty.home.title' },
        loadChildren: () => import('./related-party/related-party.module').then(m => m.RelatedPartyModule),
      },
      {
        path: 'product-specification',
        data: { pageTitle: 'catalogApp.productSpecification.home.title' },
        loadChildren: () => import('./product-specification/product-specification.module').then(m => m.ProductSpecificationModule),
      },
      {
        path: 'bundled-product-specification',
        data: { pageTitle: 'catalogApp.bundledProductSpecification.home.title' },
        loadChildren: () =>
          import('./bundled-product-specification/bundled-product-specification.module').then(m => m.BundledProductSpecificationModule),
      },
      {
        path: 'resource-specification-ref',
        data: { pageTitle: 'catalogApp.resourceSpecificationRef.home.title' },
        loadChildren: () =>
          import('./resource-specification-ref/resource-specification-ref.module').then(m => m.ResourceSpecificationRefModule),
      },
      {
        path: 'attachment-ref-or-value',
        data: { pageTitle: 'catalogApp.attachmentRefOrValue.home.title' },
        loadChildren: () => import('./attachment-ref-or-value/attachment-ref-or-value.module').then(m => m.AttachmentRefOrValueModule),
      },
      {
        path: 'target-product-schema',
        data: { pageTitle: 'catalogApp.targetProductSchema.home.title' },
        loadChildren: () => import('./target-product-schema/target-product-schema.module').then(m => m.TargetProductSchemaModule),
      },
      {
        path: 'product-specification-relationship',
        data: { pageTitle: 'catalogApp.productSpecificationRelationship.home.title' },
        loadChildren: () =>
          import('./product-specification-relationship/product-specification-relationship.module').then(
            m => m.ProductSpecificationRelationshipModule
          ),
      },
      {
        path: 'product-specification-characteristic',
        data: { pageTitle: 'catalogApp.productSpecificationCharacteristic.home.title' },
        loadChildren: () =>
          import('./product-specification-characteristic/product-specification-characteristic.module').then(
            m => m.ProductSpecificationCharacteristicModule
          ),
      },
      {
        path: 'service-specification-ref',
        data: { pageTitle: 'catalogApp.serviceSpecificationRef.home.title' },
        loadChildren: () =>
          import('./service-specification-ref/service-specification-ref.module').then(m => m.ServiceSpecificationRefModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
