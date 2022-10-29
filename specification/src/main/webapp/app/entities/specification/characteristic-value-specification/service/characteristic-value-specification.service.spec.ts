import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICharacteristicValueSpecification } from '../characteristic-value-specification.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../characteristic-value-specification.test-samples';

import {
  CharacteristicValueSpecificationService,
  RestCharacteristicValueSpecification,
} from './characteristic-value-specification.service';

const requireRestSample: RestCharacteristicValueSpecification = {
  ...sampleWithRequiredData,
  validForFrom: sampleWithRequiredData.validForFrom?.toJSON(),
  validForTo: sampleWithRequiredData.validForTo?.toJSON(),
};

describe('CharacteristicValueSpecification Service', () => {
  let service: CharacteristicValueSpecificationService;
  let httpMock: HttpTestingController;
  let expectedResult: ICharacteristicValueSpecification | ICharacteristicValueSpecification[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CharacteristicValueSpecificationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CharacteristicValueSpecification', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const characteristicValueSpecification = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(characteristicValueSpecification).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CharacteristicValueSpecification', () => {
      const characteristicValueSpecification = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(characteristicValueSpecification).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CharacteristicValueSpecification', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CharacteristicValueSpecification', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CharacteristicValueSpecification', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCharacteristicValueSpecificationToCollectionIfMissing', () => {
      it('should add a CharacteristicValueSpecification to an empty array', () => {
        const characteristicValueSpecification: ICharacteristicValueSpecification = sampleWithRequiredData;
        expectedResult = service.addCharacteristicValueSpecificationToCollectionIfMissing([], characteristicValueSpecification);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(characteristicValueSpecification);
      });

      it('should not add a CharacteristicValueSpecification to an array that contains it', () => {
        const characteristicValueSpecification: ICharacteristicValueSpecification = sampleWithRequiredData;
        const characteristicValueSpecificationCollection: ICharacteristicValueSpecification[] = [
          {
            ...characteristicValueSpecification,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCharacteristicValueSpecificationToCollectionIfMissing(
          characteristicValueSpecificationCollection,
          characteristicValueSpecification
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CharacteristicValueSpecification to an array that doesn't contain it", () => {
        const characteristicValueSpecification: ICharacteristicValueSpecification = sampleWithRequiredData;
        const characteristicValueSpecificationCollection: ICharacteristicValueSpecification[] = [sampleWithPartialData];
        expectedResult = service.addCharacteristicValueSpecificationToCollectionIfMissing(
          characteristicValueSpecificationCollection,
          characteristicValueSpecification
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(characteristicValueSpecification);
      });

      it('should add only unique CharacteristicValueSpecification to an array', () => {
        const characteristicValueSpecificationArray: ICharacteristicValueSpecification[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const characteristicValueSpecificationCollection: ICharacteristicValueSpecification[] = [sampleWithRequiredData];
        expectedResult = service.addCharacteristicValueSpecificationToCollectionIfMissing(
          characteristicValueSpecificationCollection,
          ...characteristicValueSpecificationArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const characteristicValueSpecification: ICharacteristicValueSpecification = sampleWithRequiredData;
        const characteristicValueSpecification2: ICharacteristicValueSpecification = sampleWithPartialData;
        expectedResult = service.addCharacteristicValueSpecificationToCollectionIfMissing(
          [],
          characteristicValueSpecification,
          characteristicValueSpecification2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(characteristicValueSpecification);
        expect(expectedResult).toContain(characteristicValueSpecification2);
      });

      it('should accept null and undefined values', () => {
        const characteristicValueSpecification: ICharacteristicValueSpecification = sampleWithRequiredData;
        expectedResult = service.addCharacteristicValueSpecificationToCollectionIfMissing(
          [],
          null,
          characteristicValueSpecification,
          undefined
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(characteristicValueSpecification);
      });

      it('should return initial array if no CharacteristicValueSpecification is added', () => {
        const characteristicValueSpecificationCollection: ICharacteristicValueSpecification[] = [sampleWithRequiredData];
        expectedResult = service.addCharacteristicValueSpecificationToCollectionIfMissing(
          characteristicValueSpecificationCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(characteristicValueSpecificationCollection);
      });
    });

    describe('compareCharacteristicValueSpecification', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCharacteristicValueSpecification(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCharacteristicValueSpecification(entity1, entity2);
        const compareResult2 = service.compareCharacteristicValueSpecification(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCharacteristicValueSpecification(entity1, entity2);
        const compareResult2 = service.compareCharacteristicValueSpecification(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCharacteristicValueSpecification(entity1, entity2);
        const compareResult2 = service.compareCharacteristicValueSpecification(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
