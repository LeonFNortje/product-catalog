import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductSpecificationCharacteristicRelationship } from '../product-specification-characteristic-relationship.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../product-specification-characteristic-relationship.test-samples';

import {
  ProductSpecificationCharacteristicRelationshipService,
  RestProductSpecificationCharacteristicRelationship,
} from './product-specification-characteristic-relationship.service';

const requireRestSample: RestProductSpecificationCharacteristicRelationship = {
  ...sampleWithRequiredData,
  validForFrom: sampleWithRequiredData.validForFrom?.toJSON(),
  validForTo: sampleWithRequiredData.validForTo?.toJSON(),
};

describe('ProductSpecificationCharacteristicRelationship Service', () => {
  let service: ProductSpecificationCharacteristicRelationshipService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductSpecificationCharacteristicRelationship | IProductSpecificationCharacteristicRelationship[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductSpecificationCharacteristicRelationshipService);
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

    it('should create a ProductSpecificationCharacteristicRelationship', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productSpecificationCharacteristicRelationship = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productSpecificationCharacteristicRelationship).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductSpecificationCharacteristicRelationship', () => {
      const productSpecificationCharacteristicRelationship = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productSpecificationCharacteristicRelationship).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductSpecificationCharacteristicRelationship', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductSpecificationCharacteristicRelationship', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductSpecificationCharacteristicRelationship', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductSpecificationCharacteristicRelationshipToCollectionIfMissing', () => {
      it('should add a ProductSpecificationCharacteristicRelationship to an empty array', () => {
        const productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship = sampleWithRequiredData;
        expectedResult = service.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing(
          [],
          productSpecificationCharacteristicRelationship
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSpecificationCharacteristicRelationship);
      });

      it('should not add a ProductSpecificationCharacteristicRelationship to an array that contains it', () => {
        const productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship = sampleWithRequiredData;
        const productSpecificationCharacteristicRelationshipCollection: IProductSpecificationCharacteristicRelationship[] = [
          {
            ...productSpecificationCharacteristicRelationship,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing(
          productSpecificationCharacteristicRelationshipCollection,
          productSpecificationCharacteristicRelationship
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductSpecificationCharacteristicRelationship to an array that doesn't contain it", () => {
        const productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship = sampleWithRequiredData;
        const productSpecificationCharacteristicRelationshipCollection: IProductSpecificationCharacteristicRelationship[] = [
          sampleWithPartialData,
        ];
        expectedResult = service.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing(
          productSpecificationCharacteristicRelationshipCollection,
          productSpecificationCharacteristicRelationship
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSpecificationCharacteristicRelationship);
      });

      it('should add only unique ProductSpecificationCharacteristicRelationship to an array', () => {
        const productSpecificationCharacteristicRelationshipArray: IProductSpecificationCharacteristicRelationship[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const productSpecificationCharacteristicRelationshipCollection: IProductSpecificationCharacteristicRelationship[] = [
          sampleWithRequiredData,
        ];
        expectedResult = service.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing(
          productSpecificationCharacteristicRelationshipCollection,
          ...productSpecificationCharacteristicRelationshipArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship = sampleWithRequiredData;
        const productSpecificationCharacteristicRelationship2: IProductSpecificationCharacteristicRelationship = sampleWithPartialData;
        expectedResult = service.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing(
          [],
          productSpecificationCharacteristicRelationship,
          productSpecificationCharacteristicRelationship2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSpecificationCharacteristicRelationship);
        expect(expectedResult).toContain(productSpecificationCharacteristicRelationship2);
      });

      it('should accept null and undefined values', () => {
        const productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship = sampleWithRequiredData;
        expectedResult = service.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing(
          [],
          null,
          productSpecificationCharacteristicRelationship,
          undefined
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSpecificationCharacteristicRelationship);
      });

      it('should return initial array if no ProductSpecificationCharacteristicRelationship is added', () => {
        const productSpecificationCharacteristicRelationshipCollection: IProductSpecificationCharacteristicRelationship[] = [
          sampleWithRequiredData,
        ];
        expectedResult = service.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing(
          productSpecificationCharacteristicRelationshipCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(productSpecificationCharacteristicRelationshipCollection);
      });
    });

    describe('compareProductSpecificationCharacteristicRelationship', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductSpecificationCharacteristicRelationship(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareProductSpecificationCharacteristicRelationship(entity1, entity2);
        const compareResult2 = service.compareProductSpecificationCharacteristicRelationship(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareProductSpecificationCharacteristicRelationship(entity1, entity2);
        const compareResult2 = service.compareProductSpecificationCharacteristicRelationship(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareProductSpecificationCharacteristicRelationship(entity1, entity2);
        const compareResult2 = service.compareProductSpecificationCharacteristicRelationship(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
