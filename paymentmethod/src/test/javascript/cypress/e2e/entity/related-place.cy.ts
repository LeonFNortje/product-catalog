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

describe('RelatedPlace e2e test', () => {
  const relatedPlacePageUrl = '/paymentmethod/related-place';
  const relatedPlacePageUrlPattern = new RegExp('/paymentmethod/related-place(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const relatedPlaceSample = {};

  let relatedPlace;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/paymentmethod/api/related-places+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/paymentmethod/api/related-places').as('postEntityRequest');
    cy.intercept('DELETE', '/services/paymentmethod/api/related-places/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (relatedPlace) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/paymentmethod/api/related-places/${relatedPlace.id}`,
      }).then(() => {
        relatedPlace = undefined;
      });
    }
  });

  it('RelatedPlaces menu should load RelatedPlaces page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('paymentmethod/related-place');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RelatedPlace').should('exist');
    cy.url().should('match', relatedPlacePageUrlPattern);
  });

  describe('RelatedPlace page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(relatedPlacePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RelatedPlace page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/paymentmethod/related-place/new$'));
        cy.getEntityCreateUpdateHeading('RelatedPlace');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedPlacePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/paymentmethod/api/related-places',
          body: relatedPlaceSample,
        }).then(({ body }) => {
          relatedPlace = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/paymentmethod/api/related-places+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/paymentmethod/api/related-places?page=0&size=20>; rel="last",<http://localhost/services/paymentmethod/api/related-places?page=0&size=20>; rel="first"',
              },
              body: [relatedPlace],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(relatedPlacePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RelatedPlace page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('relatedPlace');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedPlacePageUrlPattern);
      });

      it('edit button click should load edit RelatedPlace page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RelatedPlace');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedPlacePageUrlPattern);
      });

      it('edit button click should load edit RelatedPlace page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RelatedPlace');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedPlacePageUrlPattern);
      });

      it('last delete button click should delete instance of RelatedPlace', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('relatedPlace').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedPlacePageUrlPattern);

        relatedPlace = undefined;
      });
    });
  });

  describe('new RelatedPlace page', () => {
    beforeEach(() => {
      cy.visit(`${relatedPlacePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RelatedPlace');
    });

    it('should create an instance of RelatedPlace', () => {
      cy.get(`[data-cy="id"]`).type('70011443-5f14-41e6-8e38-de9ceb29ef6f').should('have.value', '70011443-5f14-41e6-8e38-de9ceb29ef6f');

      cy.get(`[data-cy="href"]`).type('Fantastic Security Cotton').should('have.value', 'Fantastic Security Cotton');

      cy.get(`[data-cy="name"]`).type('primary Developer deposit').should('have.value', 'primary Developer deposit');

      cy.get(`[data-cy="role"]`).type('Grass-roots infomediaries').should('have.value', 'Grass-roots infomediaries');

      cy.get(`[data-cy="schemaLocation"]`).type('Movies Optional').should('have.value', 'Movies Optional');

      cy.get(`[data-cy="type"]`).type('payment Wooden District').should('have.value', 'payment Wooden District');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        relatedPlace = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', relatedPlacePageUrlPattern);
    });
  });
});
