import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'payment-method',
        data: { pageTitle: 'catalogApp.paymentMethod.home.title' },
        loadChildren: () => import('./payment-method/payment-method.module').then(m => m.PaymentMethodModule),
      },
      {
        path: 'event',
        data: { pageTitle: 'catalogApp.event.home.title' },
        loadChildren: () => import('./event/event.module').then(m => m.EventModule),
      },
      {
        path: 'related-place',
        data: { pageTitle: 'catalogApp.relatedPlace.home.title' },
        loadChildren: () => import('./related-place/related-place.module').then(m => m.RelatedPlaceModule),
      },
      {
        path: 'related-party',
        data: { pageTitle: 'catalogApp.relatedParty.home.title' },
        loadChildren: () => import('./related-party/related-party.module').then(m => m.RelatedPartyModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
