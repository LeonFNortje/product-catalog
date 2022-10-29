import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResourceSpecificationRef } from '../resource-specification-ref.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../resource-specification-ref.test-samples';

import { ResourceSpecificationRefService } from './resource-specification-ref.service';

const requireRestSample: IResourceSpecificationRef = {
  ...sampleWithRequiredData,
};

describe('ResourceSpecificationRef Service', () => {
  let service: ResourceSpecificationRefService;
  let httpMock: HttpTestingController;
  let expectedResult: IResourceSpecificationRef | IResourceSpecificationRef[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResourceSpecificationRefService);
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

    it('should create a ResourceSpecificationRef', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const resourceSpecificationRef = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(resourceSpecificationRef).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResourceSpecificationRef', () => {
      const resourceSpecificationRef = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(resourceSpecificationRef).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResourceSpecificationRef', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResourceSpecificationRef', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResourceSpecificationRef', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResourceSpecificationRefToCollectionIfMissing', () => {
      it('should add a ResourceSpecificationRef to an empty array', () => {
        const resourceSpecificationRef: IResourceSpecificationRef = sampleWithRequiredData;
        expectedResult = service.addResourceSpecificationRefToCollectionIfMissing([], resourceSpecificationRef);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resourceSpecificationRef);
      });

      it('should not add a ResourceSpecificationRef to an array that contains it', () => {
        const resourceSpecificationRef: IResourceSpecificationRef = sampleWithRequiredData;
        const resourceSpecificationRefCollection: IResourceSpecificationRef[] = [
          {
            ...resourceSpecificationRef,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResourceSpecificationRefToCollectionIfMissing(
          resourceSpecificationRefCollection,
          resourceSpecificationRef
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResourceSpecificationRef to an array that doesn't contain it", () => {
        const resourceSpecificationRef: IResourceSpecificationRef = sampleWithRequiredData;
        const resourceSpecificationRefCollection: IResourceSpecificationRef[] = [sampleWithPartialData];
        expectedResult = service.addResourceSpecificationRefToCollectionIfMissing(
          resourceSpecificationRefCollection,
          resourceSpecificationRef
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resourceSpecificationRef);
      });

      it('should add only unique ResourceSpecificationRef to an array', () => {
        const resourceSpecificationRefArray: IResourceSpecificationRef[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const resourceSpecificationRefCollection: IResourceSpecificationRef[] = [sampleWithRequiredData];
        expectedResult = service.addResourceSpecificationRefToCollectionIfMissing(
          resourceSpecificationRefCollection,
          ...resourceSpecificationRefArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resourceSpecificationRef: IResourceSpecificationRef = sampleWithRequiredData;
        const resourceSpecificationRef2: IResourceSpecificationRef = sampleWithPartialData;
        expectedResult = service.addResourceSpecificationRefToCollectionIfMissing([], resourceSpecificationRef, resourceSpecificationRef2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resourceSpecificationRef);
        expect(expectedResult).toContain(resourceSpecificationRef2);
      });

      it('should accept null and undefined values', () => {
        const resourceSpecificationRef: IResourceSpecificationRef = sampleWithRequiredData;
        expectedResult = service.addResourceSpecificationRefToCollectionIfMissing([], null, resourceSpecificationRef, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resourceSpecificationRef);
      });

      it('should return initial array if no ResourceSpecificationRef is added', () => {
        const resourceSpecificationRefCollection: IResourceSpecificationRef[] = [sampleWithRequiredData];
        expectedResult = service.addResourceSpecificationRefToCollectionIfMissing(resourceSpecificationRefCollection, undefined, null);
        expect(expectedResult).toEqual(resourceSpecificationRefCollection);
      });
    });

    describe('compareResourceSpecificationRef', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResourceSpecificationRef(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareResourceSpecificationRef(entity1, entity2);
        const compareResult2 = service.compareResourceSpecificationRef(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareResourceSpecificationRef(entity1, entity2);
        const compareResult2 = service.compareResourceSpecificationRef(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareResourceSpecificationRef(entity1, entity2);
        const compareResult2 = service.compareResourceSpecificationRef(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
