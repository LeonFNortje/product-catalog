import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRelatedParty } from '../related-party.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../related-party.test-samples';

import { RelatedPartyService } from './related-party.service';

const requireRestSample: IRelatedParty = {
  ...sampleWithRequiredData,
};

describe('RelatedParty Service', () => {
  let service: RelatedPartyService;
  let httpMock: HttpTestingController;
  let expectedResult: IRelatedParty | IRelatedParty[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RelatedPartyService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a RelatedParty', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const relatedParty = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(relatedParty).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RelatedParty', () => {
      const relatedParty = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(relatedParty).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RelatedParty', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RelatedParty', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RelatedParty', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRelatedPartyToCollectionIfMissing', () => {
      it('should add a RelatedParty to an empty array', () => {
        const relatedParty: IRelatedParty = sampleWithRequiredData;
        expectedResult = service.addRelatedPartyToCollectionIfMissing([], relatedParty);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(relatedParty);
      });

      it('should not add a RelatedParty to an array that contains it', () => {
        const relatedParty: IRelatedParty = sampleWithRequiredData;
        const relatedPartyCollection: IRelatedParty[] = [
          {
            ...relatedParty,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRelatedPartyToCollectionIfMissing(relatedPartyCollection, relatedParty);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RelatedParty to an array that doesn't contain it", () => {
        const relatedParty: IRelatedParty = sampleWithRequiredData;
        const relatedPartyCollection: IRelatedParty[] = [sampleWithPartialData];
        expectedResult = service.addRelatedPartyToCollectionIfMissing(relatedPartyCollection, relatedParty);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(relatedParty);
      });

      it('should add only unique RelatedParty to an array', () => {
        const relatedPartyArray: IRelatedParty[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const relatedPartyCollection: IRelatedParty[] = [sampleWithRequiredData];
        expectedResult = service.addRelatedPartyToCollectionIfMissing(relatedPartyCollection, ...relatedPartyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const relatedParty: IRelatedParty = sampleWithRequiredData;
        const relatedParty2: IRelatedParty = sampleWithPartialData;
        expectedResult = service.addRelatedPartyToCollectionIfMissing([], relatedParty, relatedParty2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(relatedParty);
        expect(expectedResult).toContain(relatedParty2);
      });

      it('should accept null and undefined values', () => {
        const relatedParty: IRelatedParty = sampleWithRequiredData;
        expectedResult = service.addRelatedPartyToCollectionIfMissing([], null, relatedParty, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(relatedParty);
      });

      it('should return initial array if no RelatedParty is added', () => {
        const relatedPartyCollection: IRelatedParty[] = [sampleWithRequiredData];
        expectedResult = service.addRelatedPartyToCollectionIfMissing(relatedPartyCollection, undefined, null);
        expect(expectedResult).toEqual(relatedPartyCollection);
      });
    });

    describe('compareRelatedParty', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRelatedParty(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareRelatedParty(entity1, entity2);
        const compareResult2 = service.compareRelatedParty(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareRelatedParty(entity1, entity2);
        const compareResult2 = service.compareRelatedParty(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareRelatedParty(entity1, entity2);
        const compareResult2 = service.compareRelatedParty(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
