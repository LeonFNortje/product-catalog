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

describe('PaymentMethod e2e test', () => {
  const paymentMethodPageUrl = '/paymentmethod/payment-method';
  const paymentMethodPageUrlPattern = new RegExp('/paymentmethod/payment-method(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const paymentMethodSample = {};

  let paymentMethod;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/paymentmethod/api/payment-methods+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/paymentmethod/api/payment-methods').as('postEntityRequest');
    cy.intercept('DELETE', '/services/paymentmethod/api/payment-methods/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (paymentMethod) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/paymentmethod/api/payment-methods/${paymentMethod.id}`,
      }).then(() => {
        paymentMethod = undefined;
      });
    }
  });

  it('PaymentMethods menu should load PaymentMethods page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('paymentmethod/payment-method');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PaymentMethod').should('exist');
    cy.url().should('match', paymentMethodPageUrlPattern);
  });

  describe('PaymentMethod page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(paymentMethodPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PaymentMethod page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/paymentmethod/payment-method/new$'));
        cy.getEntityCreateUpdateHeading('PaymentMethod');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/paymentmethod/api/payment-methods',
          body: paymentMethodSample,
        }).then(({ body }) => {
          paymentMethod = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/paymentmethod/api/payment-methods+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/paymentmethod/api/payment-methods?page=0&size=20>; rel="last",<http://localhost/services/paymentmethod/api/payment-methods?page=0&size=20>; rel="first"',
              },
              body: [paymentMethod],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(paymentMethodPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PaymentMethod page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('paymentMethod');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodPageUrlPattern);
      });

      it('edit button click should load edit PaymentMethod page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PaymentMethod');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodPageUrlPattern);
      });

      it('edit button click should load edit PaymentMethod page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PaymentMethod');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodPageUrlPattern);
      });

      it('last delete button click should delete instance of PaymentMethod', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('paymentMethod').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodPageUrlPattern);

        paymentMethod = undefined;
      });
    });
  });

  describe('new PaymentMethod page', () => {
    beforeEach(() => {
      cy.visit(`${paymentMethodPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PaymentMethod');
    });

    it('should create an instance of PaymentMethod', () => {
      cy.get(`[data-cy="id"]`).type('45762592-c750-42d4-bbc0-20720f324e09').should('have.value', '45762592-c750-42d4-bbc0-20720f324e09');

      cy.get(`[data-cy="href"]`).type('COM B2C leverage').should('have.value', 'COM B2C leverage');

      cy.get(`[data-cy="authorizationCode"]`).type('Mauritius Account Games').should('have.value', 'Mauritius Account Games');

      cy.get(`[data-cy="description"]`).type('Internal streamline').should('have.value', 'Internal streamline');

      cy.get(`[data-cy="isPreferred"]`).should('not.be.checked');
      cy.get(`[data-cy="isPreferred"]`).click().should('be.checked');

      cy.get(`[data-cy="name"]`).type('Music framework').should('have.value', 'Music framework');

      cy.get(`[data-cy="status"]`).type('Avon').should('have.value', 'Avon');

      cy.get(`[data-cy="statusDate"]`).type('2022-10-31T04:49').blur().should('have.value', '2022-10-31T04:49');

      cy.get(`[data-cy="statusReason"]`).type('incubate online').should('have.value', 'incubate online');

      cy.get(`[data-cy="validForFrom"]`).type('2022-10-31T16:16').blur().should('have.value', '2022-10-31T16:16');

      cy.get(`[data-cy="validForTo"]`).type('2022-10-31T04:43').blur().should('have.value', '2022-10-31T04:43');

      cy.get(`[data-cy="schemaLocation"]`).type('Leone').should('have.value', 'Leone');

      cy.get(`[data-cy="type"]`).type('Data Island').should('have.value', 'Data Island');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        paymentMethod = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', paymentMethodPageUrlPattern);
    });
  });
});
