import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductSpecificationCharacteristic } from '../product-specification-characteristic.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../product-specification-characteristic.test-samples';

import {
  ProductSpecificationCharacteristicService,
  RestProductSpecificationCharacteristic,
} from './product-specification-characteristic.service';

const requireRestSample: RestProductSpecificationCharacteristic = {
  ...sampleWithRequiredData,
  validForFrom: sampleWithRequiredData.validForFrom?.toJSON(),
  validForTo: sampleWithRequiredData.validForTo?.toJSON(),
};

describe('ProductSpecificationCharacteristic Service', () => {
  let service: ProductSpecificationCharacteristicService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductSpecificationCharacteristic | IProductSpecificationCharacteristic[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductSpecificationCharacteristicService);
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

    it('should create a ProductSpecificationCharacteristic', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productSpecificationCharacteristic = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productSpecificationCharacteristic).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductSpecificationCharacteristic', () => {
      const productSpecificationCharacteristic = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productSpecificationCharacteristic).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductSpecificationCharacteristic', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductSpecificationCharacteristic', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductSpecificationCharacteristic', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductSpecificationCharacteristicToCollectionIfMissing', () => {
      it('should add a ProductSpecificationCharacteristic to an empty array', () => {
        const productSpecificationCharacteristic: IProductSpecificationCharacteristic = sampleWithRequiredData;
        expectedResult = service.addProductSpecificationCharacteristicToCollectionIfMissing([], productSpecificationCharacteristic);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSpecificationCharacteristic);
      });

      it('should not add a ProductSpecificationCharacteristic to an array that contains it', () => {
        const productSpecificationCharacteristic: IProductSpecificationCharacteristic = sampleWithRequiredData;
        const productSpecificationCharacteristicCollection: IProductSpecificationCharacteristic[] = [
          {
            ...productSpecificationCharacteristic,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductSpecificationCharacteristicToCollectionIfMissing(
          productSpecificationCharacteristicCollection,
          productSpecificationCharacteristic
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductSpecificationCharacteristic to an array that doesn't contain it", () => {
        const productSpecificationCharacteristic: IProductSpecificationCharacteristic = sampleWithRequiredData;
        const productSpecificationCharacteristicCollection: IProductSpecificationCharacteristic[] = [sampleWithPartialData];
        expectedResult = service.addProductSpecificationCharacteristicToCollectionIfMissing(
          productSpecificationCharacteristicCollection,
          productSpecificationCharacteristic
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSpecificationCharacteristic);
      });

      it('should add only unique ProductSpecificationCharacteristic to an array', () => {
        const productSpecificationCharacteristicArray: IProductSpecificationCharacteristic[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const productSpecificationCharacteristicCollection: IProductSpecificationCharacteristic[] = [sampleWithRequiredData];
        expectedResult = service.addProductSpecificationCharacteristicToCollectionIfMissing(
          productSpecificationCharacteristicCollection,
          ...productSpecificationCharacteristicArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productSpecificationCharacteristic: IProductSpecificationCharacteristic = sampleWithRequiredData;
        const productSpecificationCharacteristic2: IProductSpecificationCharacteristic = sampleWithPartialData;
        expectedResult = service.addProductSpecificationCharacteristicToCollectionIfMissing(
          [],
          productSpecificationCharacteristic,
          productSpecificationCharacteristic2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSpecificationCharacteristic);
        expect(expectedResult).toContain(productSpecificationCharacteristic2);
      });

      it('should accept null and undefined values', () => {
        const productSpecificationCharacteristic: IProductSpecificationCharacteristic = sampleWithRequiredData;
        expectedResult = service.addProductSpecificationCharacteristicToCollectionIfMissing(
          [],
          null,
          productSpecificationCharacteristic,
          undefined
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSpecificationCharacteristic);
      });

      it('should return initial array if no ProductSpecificationCharacteristic is added', () => {
        const productSpecificationCharacteristicCollection: IProductSpecificationCharacteristic[] = [sampleWithRequiredData];
        expectedResult = service.addProductSpecificationCharacteristicToCollectionIfMissing(
          productSpecificationCharacteristicCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(productSpecificationCharacteristicCollection);
      });
    });

    describe('compareProductSpecificationCharacteristic', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductSpecificationCharacteristic(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareProductSpecificationCharacteristic(entity1, entity2);
        const compareResult2 = service.compareProductSpecificationCharacteristic(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareProductSpecificationCharacteristic(entity1, entity2);
        const compareResult2 = service.compareProductSpecificationCharacteristic(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareProductSpecificationCharacteristic(entity1, entity2);
        const compareResult2 = service.compareProductSpecificationCharacteristic(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
