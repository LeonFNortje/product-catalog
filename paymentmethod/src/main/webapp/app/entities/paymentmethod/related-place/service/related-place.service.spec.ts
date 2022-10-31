import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRelatedPlace } from '../related-place.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../related-place.test-samples';

import { RelatedPlaceService } from './related-place.service';

const requireRestSample: IRelatedPlace = {
  ...sampleWithRequiredData,
};

describe('RelatedPlace Service', () => {
  let service: RelatedPlaceService;
  let httpMock: HttpTestingController;
  let expectedResult: IRelatedPlace | IRelatedPlace[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RelatedPlaceService);
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

    it('should create a RelatedPlace', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const relatedPlace = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(relatedPlace).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RelatedPlace', () => {
      const relatedPlace = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(relatedPlace).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RelatedPlace', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RelatedPlace', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RelatedPlace', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRelatedPlaceToCollectionIfMissing', () => {
      it('should add a RelatedPlace to an empty array', () => {
        const relatedPlace: IRelatedPlace = sampleWithRequiredData;
        expectedResult = service.addRelatedPlaceToCollectionIfMissing([], relatedPlace);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(relatedPlace);
      });

      it('should not add a RelatedPlace to an array that contains it', () => {
        const relatedPlace: IRelatedPlace = sampleWithRequiredData;
        const relatedPlaceCollection: IRelatedPlace[] = [
          {
            ...relatedPlace,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRelatedPlaceToCollectionIfMissing(relatedPlaceCollection, relatedPlace);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RelatedPlace to an array that doesn't contain it", () => {
        const relatedPlace: IRelatedPlace = sampleWithRequiredData;
        const relatedPlaceCollection: IRelatedPlace[] = [sampleWithPartialData];
        expectedResult = service.addRelatedPlaceToCollectionIfMissing(relatedPlaceCollection, relatedPlace);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(relatedPlace);
      });

      it('should add only unique RelatedPlace to an array', () => {
        const relatedPlaceArray: IRelatedPlace[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const relatedPlaceCollection: IRelatedPlace[] = [sampleWithRequiredData];
        expectedResult = service.addRelatedPlaceToCollectionIfMissing(relatedPlaceCollection, ...relatedPlaceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const relatedPlace: IRelatedPlace = sampleWithRequiredData;
        const relatedPlace2: IRelatedPlace = sampleWithPartialData;
        expectedResult = service.addRelatedPlaceToCollectionIfMissing([], relatedPlace, relatedPlace2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(relatedPlace);
        expect(expectedResult).toContain(relatedPlace2);
      });

      it('should accept null and undefined values', () => {
        const relatedPlace: IRelatedPlace = sampleWithRequiredData;
        expectedResult = service.addRelatedPlaceToCollectionIfMissing([], null, relatedPlace, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(relatedPlace);
      });

      it('should return initial array if no RelatedPlace is added', () => {
        const relatedPlaceCollection: IRelatedPlace[] = [sampleWithRequiredData];
        expectedResult = service.addRelatedPlaceToCollectionIfMissing(relatedPlaceCollection, undefined, null);
        expect(expectedResult).toEqual(relatedPlaceCollection);
      });
    });

    describe('compareRelatedPlace', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRelatedPlace(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareRelatedPlace(entity1, entity2);
        const compareResult2 = service.compareRelatedPlace(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareRelatedPlace(entity1, entity2);
        const compareResult2 = service.compareRelatedPlace(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareRelatedPlace(entity1, entity2);
        const compareResult2 = service.compareRelatedPlace(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
