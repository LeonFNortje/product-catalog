import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'payment-method',
        data: { pageTitle: 'paymentmethodApp.paymentmethodPaymentMethod.home.title' },
        loadChildren: () => import('./paymentmethod/payment-method/payment-method.module').then(m => m.PaymentmethodPaymentMethodModule),
      },
      {
        path: 'event',
        data: { pageTitle: 'paymentmethodApp.paymentmethodEvent.home.title' },
        loadChildren: () => import('./paymentmethod/event/event.module').then(m => m.PaymentmethodEventModule),
      },
      {
        path: 'related-place',
        data: { pageTitle: 'paymentmethodApp.paymentmethodRelatedPlace.home.title' },
        loadChildren: () => import('./paymentmethod/related-place/related-place.module').then(m => m.PaymentmethodRelatedPlaceModule),
      },
      {
        path: 'related-party',
        data: { pageTitle: 'paymentmethodApp.paymentmethodRelatedParty.home.title' },
        loadChildren: () => import('./paymentmethod/related-party/related-party.module').then(m => m.PaymentmethodRelatedPartyModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
