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

describe('RelatedParty e2e test', () => {
  const relatedPartyPageUrl = '/paymentmethod/related-party';
  const relatedPartyPageUrlPattern = new RegExp('/paymentmethod/related-party(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const relatedPartySample = {};

  let relatedParty;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/paymentmethod/api/related-parties+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/paymentmethod/api/related-parties').as('postEntityRequest');
    cy.intercept('DELETE', '/services/paymentmethod/api/related-parties/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (relatedParty) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/paymentmethod/api/related-parties/${relatedParty.id}`,
      }).then(() => {
        relatedParty = undefined;
      });
    }
  });

  it('RelatedParties menu should load RelatedParties page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('paymentmethod/related-party');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RelatedParty').should('exist');
    cy.url().should('match', relatedPartyPageUrlPattern);
  });

  describe('RelatedParty page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(relatedPartyPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RelatedParty page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/paymentmethod/related-party/new$'));
        cy.getEntityCreateUpdateHeading('RelatedParty');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedPartyPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/paymentmethod/api/related-parties',
          body: relatedPartySample,
        }).then(({ body }) => {
          relatedParty = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/paymentmethod/api/related-parties+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/paymentmethod/api/related-parties?page=0&size=20>; rel="last",<http://localhost/services/paymentmethod/api/related-parties?page=0&size=20>; rel="first"',
              },
              body: [relatedParty],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(relatedPartyPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RelatedParty page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('relatedParty');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedPartyPageUrlPattern);
      });

      it('edit button click should load edit RelatedParty page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RelatedParty');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedPartyPageUrlPattern);
      });

      it('edit button click should load edit RelatedParty page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RelatedParty');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedPartyPageUrlPattern);
      });

      it('last delete button click should delete instance of RelatedParty', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('relatedParty').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedPartyPageUrlPattern);

        relatedParty = undefined;
      });
    });
  });

  describe('new RelatedParty page', () => {
    beforeEach(() => {
      cy.visit(`${relatedPartyPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RelatedParty');
    });

    it('should create an instance of RelatedParty', () => {
      cy.get(`[data-cy="id"]`).type('44ef57d1-53cf-4472-a6d4-7ee9a51ecad3').should('have.value', '44ef57d1-53cf-4472-a6d4-7ee9a51ecad3');

      cy.get(`[data-cy="href"]`).type('Sleek calculate Mission').should('have.value', 'Sleek calculate Mission');

      cy.get(`[data-cy="name"]`).type('Frozen quantify').should('have.value', 'Frozen quantify');

      cy.get(`[data-cy="role"]`).type('Security Zimbabwe').should('have.value', 'Security Zimbabwe');

      cy.get(`[data-cy="schemaLocation"]`).type('PNG Run').should('have.value', 'PNG Run');

      cy.get(`[data-cy="type"]`).type('Account Highway purple').should('have.value', 'Account Highway purple');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        relatedParty = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', relatedPartyPageUrlPattern);
    });
  });
});
