import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Event e2e test', () => {
  const eventPageUrl = '/paymentmethod/event';
  const eventPageUrlPattern = new RegExp('/paymentmethod/event(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const eventSample = {};

  let event;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/paymentmethod/api/events+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/paymentmethod/api/events').as('postEntityRequest');
    cy.intercept('DELETE', '/services/paymentmethod/api/events/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (event) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/paymentmethod/api/events/${event.id}`,
      }).then(() => {
        event = undefined;
      });
    }
  });

  it('Events menu should load Events page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('paymentmethod/event');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Event').should('exist');
    cy.url().should('match', eventPageUrlPattern);
  });

  describe('Event page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(eventPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Event page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/paymentmethod/event/new$'));
        cy.getEntityCreateUpdateHeading('Event');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/paymentmethod/api/events',
          body: eventSample,
        }).then(({ body }) => {
          event = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/paymentmethod/api/events+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/paymentmethod/api/events?page=0&size=20>; rel="last",<http://localhost/services/paymentmethod/api/events?page=0&size=20>; rel="first"',
              },
              body: [event],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(eventPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Event page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('event');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });

      it('edit button click should load edit Event page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Event');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });

      it('edit button click should load edit Event page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Event');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });

      it('last delete button click should delete instance of Event', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('event').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);

        event = undefined;
      });
    });
  });

  describe('new Event page', () => {
    beforeEach(() => {
      cy.visit(`${eventPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Event');
    });

    it('should create an instance of Event', () => {
      cy.get(`[data-cy="correlationId"]`).type('Usability withdrawal').should('have.value', 'Usability withdrawal');

      cy.get(`[data-cy="description"]`).type('Checking Wisconsin').should('have.value', 'Checking Wisconsin');

      cy.get(`[data-cy="domain"]`).type('Pants Investor').should('have.value', 'Pants Investor');

      cy.get(`[data-cy="eventId"]`).type('users').should('have.value', 'users');

      cy.get(`[data-cy="eventTime"]`).type('2022-10-31T03:47').blur().should('have.value', '2022-10-31T03:47');

      cy.get(`[data-cy="eventType"]`).type('Small').should('have.value', 'Small');

      cy.get(`[data-cy="href"]`).type('Saint Loan').should('have.value', 'Saint Loan');

      cy.get(`[data-cy="id"]`).type('1e8f0ef0-37cf-48c5-94a1-dacd07616599').should('have.value', '1e8f0ef0-37cf-48c5-94a1-dacd07616599');

      cy.get(`[data-cy="priority"]`).type('Tuna and').should('have.value', 'Tuna and');

      cy.get(`[data-cy="timeOccurred"]`).type('2022-10-31T12:50').blur().should('have.value', '2022-10-31T12:50');

      cy.get(`[data-cy="title"]`).type('synthesizing').should('have.value', 'synthesizing');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        event = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', eventPageUrlPattern);
    });
  });
});
