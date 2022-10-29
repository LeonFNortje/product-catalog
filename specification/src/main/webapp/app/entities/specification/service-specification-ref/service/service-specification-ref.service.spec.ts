import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IServiceSpecificationRef } from '../service-specification-ref.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../service-specification-ref.test-samples';

import { ServiceSpecificationRefService } from './service-specification-ref.service';

const requireRestSample: IServiceSpecificationRef = {
  ...sampleWithRequiredData,
};

describe('ServiceSpecificationRef Service', () => {
  let service: ServiceSpecificationRefService;
  let httpMock: HttpTestingController;
  let expectedResult: IServiceSpecificationRef | IServiceSpecificationRef[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ServiceSpecificationRefService);
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

    it('should create a ServiceSpecificationRef', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const serviceSpecificationRef = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(serviceSpecificationRef).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ServiceSpecificationRef', () => {
      const serviceSpecificationRef = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(serviceSpecificationRef).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ServiceSpecificationRef', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ServiceSpecificationRef', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ServiceSpecificationRef', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServiceSpecificationRefToCollectionIfMissing', () => {
      it('should add a ServiceSpecificationRef to an empty array', () => {
        const serviceSpecificationRef: IServiceSpecificationRef = sampleWithRequiredData;
        expectedResult = service.addServiceSpecificationRefToCollectionIfMissing([], serviceSpecificationRef);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(serviceSpecificationRef);
      });

      it('should not add a ServiceSpecificationRef to an array that contains it', () => {
        const serviceSpecificationRef: IServiceSpecificationRef = sampleWithRequiredData;
        const serviceSpecificationRefCollection: IServiceSpecificationRef[] = [
          {
            ...serviceSpecificationRef,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServiceSpecificationRefToCollectionIfMissing(
          serviceSpecificationRefCollection,
          serviceSpecificationRef
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ServiceSpecificationRef to an array that doesn't contain it", () => {
        const serviceSpecificationRef: IServiceSpecificationRef = sampleWithRequiredData;
        const serviceSpecificationRefCollection: IServiceSpecificationRef[] = [sampleWithPartialData];
        expectedResult = service.addServiceSpecificationRefToCollectionIfMissing(
          serviceSpecificationRefCollection,
          serviceSpecificationRef
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(serviceSpecificationRef);
      });

      it('should add only unique ServiceSpecificationRef to an array', () => {
        const serviceSpecificationRefArray: IServiceSpecificationRef[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const serviceSpecificationRefCollection: IServiceSpecificationRef[] = [sampleWithRequiredData];
        expectedResult = service.addServiceSpecificationRefToCollectionIfMissing(
          serviceSpecificationRefCollection,
          ...serviceSpecificationRefArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const serviceSpecificationRef: IServiceSpecificationRef = sampleWithRequiredData;
        const serviceSpecificationRef2: IServiceSpecificationRef = sampleWithPartialData;
        expectedResult = service.addServiceSpecificationRefToCollectionIfMissing([], serviceSpecificationRef, serviceSpecificationRef2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(serviceSpecificationRef);
        expect(expectedResult).toContain(serviceSpecificationRef2);
      });

      it('should accept null and undefined values', () => {
        const serviceSpecificationRef: IServiceSpecificationRef = sampleWithRequiredData;
        expectedResult = service.addServiceSpecificationRefToCollectionIfMissing([], null, serviceSpecificationRef, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(serviceSpecificationRef);
      });

      it('should return initial array if no ServiceSpecificationRef is added', () => {
        const serviceSpecificationRefCollection: IServiceSpecificationRef[] = [sampleWithRequiredData];
        expectedResult = service.addServiceSpecificationRefToCollectionIfMissing(serviceSpecificationRefCollection, undefined, null);
        expect(expectedResult).toEqual(serviceSpecificationRefCollection);
      });
    });

    describe('compareServiceSpecificationRef', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServiceSpecificationRef(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareServiceSpecificationRef(entity1, entity2);
        const compareResult2 = service.compareServiceSpecificationRef(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareServiceSpecificationRef(entity1, entity2);
        const compareResult2 = service.compareServiceSpecificationRef(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareServiceSpecificationRef(entity1, entity2);
        const compareResult2 = service.compareServiceSpecificationRef(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
