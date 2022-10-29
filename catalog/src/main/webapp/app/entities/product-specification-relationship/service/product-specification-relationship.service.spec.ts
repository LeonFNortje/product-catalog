import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductSpecificationRelationship } from '../product-specification-relationship.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../product-specification-relationship.test-samples';

import {
  ProductSpecificationRelationshipService,
  RestProductSpecificationRelationship,
} from './product-specification-relationship.service';

const requireRestSample: RestProductSpecificationRelationship = {
  ...sampleWithRequiredData,
  validForFrom: sampleWithRequiredData.validForFrom?.toJSON(),
  validForTo: sampleWithRequiredData.validForTo?.toJSON(),
};

describe('ProductSpecificationRelationship Service', () => {
  let service: ProductSpecificationRelationshipService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductSpecificationRelationship | IProductSpecificationRelationship[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductSpecificationRelationshipService);
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

    it('should create a ProductSpecificationRelationship', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productSpecificationRelationship = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productSpecificationRelationship).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductSpecificationRelationship', () => {
      const productSpecificationRelationship = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productSpecificationRelationship).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductSpecificationRelationship', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductSpecificationRelationship', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductSpecificationRelationship', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductSpecificationRelationshipToCollectionIfMissing', () => {
      it('should add a ProductSpecificationRelationship to an empty array', () => {
        const productSpecificationRelationship: IProductSpecificationRelationship = sampleWithRequiredData;
        expectedResult = service.addProductSpecificationRelationshipToCollectionIfMissing([], productSpecificationRelationship);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSpecificationRelationship);
      });

      it('should not add a ProductSpecificationRelationship to an array that contains it', () => {
        const productSpecificationRelationship: IProductSpecificationRelationship = sampleWithRequiredData;
        const productSpecificationRelationshipCollection: IProductSpecificationRelationship[] = [
          {
            ...productSpecificationRelationship,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductSpecificationRelationshipToCollectionIfMissing(
          productSpecificationRelationshipCollection,
          productSpecificationRelationship
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductSpecificationRelationship to an array that doesn't contain it", () => {
        const productSpecificationRelationship: IProductSpecificationRelationship = sampleWithRequiredData;
        const productSpecificationRelationshipCollection: IProductSpecificationRelationship[] = [sampleWithPartialData];
        expectedResult = service.addProductSpecificationRelationshipToCollectionIfMissing(
          productSpecificationRelationshipCollection,
          productSpecificationRelationship
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSpecificationRelationship);
      });

      it('should add only unique ProductSpecificationRelationship to an array', () => {
        const productSpecificationRelationshipArray: IProductSpecificationRelationship[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const productSpecificationRelationshipCollection: IProductSpecificationRelationship[] = [sampleWithRequiredData];
        expectedResult = service.addProductSpecificationRelationshipToCollectionIfMissing(
          productSpecificationRelationshipCollection,
          ...productSpecificationRelationshipArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productSpecificationRelationship: IProductSpecificationRelationship = sampleWithRequiredData;
        const productSpecificationRelationship2: IProductSpecificationRelationship = sampleWithPartialData;
        expectedResult = service.addProductSpecificationRelationshipToCollectionIfMissing(
          [],
          productSpecificationRelationship,
          productSpecificationRelationship2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSpecificationRelationship);
        expect(expectedResult).toContain(productSpecificationRelationship2);
      });

      it('should accept null and undefined values', () => {
        const productSpecificationRelationship: IProductSpecificationRelationship = sampleWithRequiredData;
        expectedResult = service.addProductSpecificationRelationshipToCollectionIfMissing(
          [],
          null,
          productSpecificationRelationship,
          undefined
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSpecificationRelationship);
      });

      it('should return initial array if no ProductSpecificationRelationship is added', () => {
        const productSpecificationRelationshipCollection: IProductSpecificationRelationship[] = [sampleWithRequiredData];
        expectedResult = service.addProductSpecificationRelationshipToCollectionIfMissing(
          productSpecificationRelationshipCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(productSpecificationRelationshipCollection);
      });
    });

    describe('compareProductSpecificationRelationship', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductSpecificationRelationship(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareProductSpecificationRelationship(entity1, entity2);
        const compareResult2 = service.compareProductSpecificationRelationship(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareProductSpecificationRelationship(entity1, entity2);
        const compareResult2 = service.compareProductSpecificationRelationship(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareProductSpecificationRelationship(entity1, entity2);
        const compareResult2 = service.compareProductSpecificationRelationship(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
