import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBundledProductSpecification } from '../bundled-product-specification.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../bundled-product-specification.test-samples';

import { BundledProductSpecificationService } from './bundled-product-specification.service';

const requireRestSample: IBundledProductSpecification = {
  ...sampleWithRequiredData,
};

describe('BundledProductSpecification Service', () => {
  let service: BundledProductSpecificationService;
  let httpMock: HttpTestingController;
  let expectedResult: IBundledProductSpecification | IBundledProductSpecification[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BundledProductSpecificationService);
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

    it('should create a BundledProductSpecification', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const bundledProductSpecification = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(bundledProductSpecification).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BundledProductSpecification', () => {
      const bundledProductSpecification = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(bundledProductSpecification).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BundledProductSpecification', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BundledProductSpecification', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BundledProductSpecification', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBundledProductSpecificationToCollectionIfMissing', () => {
      it('should add a BundledProductSpecification to an empty array', () => {
        const bundledProductSpecification: IBundledProductSpecification = sampleWithRequiredData;
        expectedResult = service.addBundledProductSpecificationToCollectionIfMissing([], bundledProductSpecification);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bundledProductSpecification);
      });

      it('should not add a BundledProductSpecification to an array that contains it', () => {
        const bundledProductSpecification: IBundledProductSpecification = sampleWithRequiredData;
        const bundledProductSpecificationCollection: IBundledProductSpecification[] = [
          {
            ...bundledProductSpecification,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBundledProductSpecificationToCollectionIfMissing(
          bundledProductSpecificationCollection,
          bundledProductSpecification
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BundledProductSpecification to an array that doesn't contain it", () => {
        const bundledProductSpecification: IBundledProductSpecification = sampleWithRequiredData;
        const bundledProductSpecificationCollection: IBundledProductSpecification[] = [sampleWithPartialData];
        expectedResult = service.addBundledProductSpecificationToCollectionIfMissing(
          bundledProductSpecificationCollection,
          bundledProductSpecification
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bundledProductSpecification);
      });

      it('should add only unique BundledProductSpecification to an array', () => {
        const bundledProductSpecificationArray: IBundledProductSpecification[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const bundledProductSpecificationCollection: IBundledProductSpecification[] = [sampleWithRequiredData];
        expectedResult = service.addBundledProductSpecificationToCollectionIfMissing(
          bundledProductSpecificationCollection,
          ...bundledProductSpecificationArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bundledProductSpecification: IBundledProductSpecification = sampleWithRequiredData;
        const bundledProductSpecification2: IBundledProductSpecification = sampleWithPartialData;
        expectedResult = service.addBundledProductSpecificationToCollectionIfMissing(
          [],
          bundledProductSpecification,
          bundledProductSpecification2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bundledProductSpecification);
        expect(expectedResult).toContain(bundledProductSpecification2);
      });

      it('should accept null and undefined values', () => {
        const bundledProductSpecification: IBundledProductSpecification = sampleWithRequiredData;
        expectedResult = service.addBundledProductSpecificationToCollectionIfMissing([], null, bundledProductSpecification, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bundledProductSpecification);
      });

      it('should return initial array if no BundledProductSpecification is added', () => {
        const bundledProductSpecificationCollection: IBundledProductSpecification[] = [sampleWithRequiredData];
        expectedResult = service.addBundledProductSpecificationToCollectionIfMissing(
          bundledProductSpecificationCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(bundledProductSpecificationCollection);
      });
    });

    describe('compareBundledProductSpecification', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBundledProductSpecification(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareBundledProductSpecification(entity1, entity2);
        const compareResult2 = service.compareBundledProductSpecification(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareBundledProductSpecification(entity1, entity2);
        const compareResult2 = service.compareBundledProductSpecification(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareBundledProductSpecification(entity1, entity2);
        const compareResult2 = service.compareBundledProductSpecification(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
