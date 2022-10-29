import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAttachmentRefOrValue } from '../attachment-ref-or-value.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../attachment-ref-or-value.test-samples';

import { AttachmentRefOrValueService, RestAttachmentRefOrValue } from './attachment-ref-or-value.service';

const requireRestSample: RestAttachmentRefOrValue = {
  ...sampleWithRequiredData,
  validForFrom: sampleWithRequiredData.validForFrom?.toJSON(),
  validForTo: sampleWithRequiredData.validForTo?.toJSON(),
};

describe('AttachmentRefOrValue Service', () => {
  let service: AttachmentRefOrValueService;
  let httpMock: HttpTestingController;
  let expectedResult: IAttachmentRefOrValue | IAttachmentRefOrValue[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AttachmentRefOrValueService);
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

    it('should create a AttachmentRefOrValue', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const attachmentRefOrValue = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(attachmentRefOrValue).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AttachmentRefOrValue', () => {
      const attachmentRefOrValue = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(attachmentRefOrValue).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AttachmentRefOrValue', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AttachmentRefOrValue', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AttachmentRefOrValue', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAttachmentRefOrValueToCollectionIfMissing', () => {
      it('should add a AttachmentRefOrValue to an empty array', () => {
        const attachmentRefOrValue: IAttachmentRefOrValue = sampleWithRequiredData;
        expectedResult = service.addAttachmentRefOrValueToCollectionIfMissing([], attachmentRefOrValue);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(attachmentRefOrValue);
      });

      it('should not add a AttachmentRefOrValue to an array that contains it', () => {
        const attachmentRefOrValue: IAttachmentRefOrValue = sampleWithRequiredData;
        const attachmentRefOrValueCollection: IAttachmentRefOrValue[] = [
          {
            ...attachmentRefOrValue,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAttachmentRefOrValueToCollectionIfMissing(attachmentRefOrValueCollection, attachmentRefOrValue);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AttachmentRefOrValue to an array that doesn't contain it", () => {
        const attachmentRefOrValue: IAttachmentRefOrValue = sampleWithRequiredData;
        const attachmentRefOrValueCollection: IAttachmentRefOrValue[] = [sampleWithPartialData];
        expectedResult = service.addAttachmentRefOrValueToCollectionIfMissing(attachmentRefOrValueCollection, attachmentRefOrValue);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(attachmentRefOrValue);
      });

      it('should add only unique AttachmentRefOrValue to an array', () => {
        const attachmentRefOrValueArray: IAttachmentRefOrValue[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const attachmentRefOrValueCollection: IAttachmentRefOrValue[] = [sampleWithRequiredData];
        expectedResult = service.addAttachmentRefOrValueToCollectionIfMissing(attachmentRefOrValueCollection, ...attachmentRefOrValueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const attachmentRefOrValue: IAttachmentRefOrValue = sampleWithRequiredData;
        const attachmentRefOrValue2: IAttachmentRefOrValue = sampleWithPartialData;
        expectedResult = service.addAttachmentRefOrValueToCollectionIfMissing([], attachmentRefOrValue, attachmentRefOrValue2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(attachmentRefOrValue);
        expect(expectedResult).toContain(attachmentRefOrValue2);
      });

      it('should accept null and undefined values', () => {
        const attachmentRefOrValue: IAttachmentRefOrValue = sampleWithRequiredData;
        expectedResult = service.addAttachmentRefOrValueToCollectionIfMissing([], null, attachmentRefOrValue, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(attachmentRefOrValue);
      });

      it('should return initial array if no AttachmentRefOrValue is added', () => {
        const attachmentRefOrValueCollection: IAttachmentRefOrValue[] = [sampleWithRequiredData];
        expectedResult = service.addAttachmentRefOrValueToCollectionIfMissing(attachmentRefOrValueCollection, undefined, null);
        expect(expectedResult).toEqual(attachmentRefOrValueCollection);
      });
    });

    describe('compareAttachmentRefOrValue', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAttachmentRefOrValue(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareAttachmentRefOrValue(entity1, entity2);
        const compareResult2 = service.compareAttachmentRefOrValue(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareAttachmentRefOrValue(entity1, entity2);
        const compareResult2 = service.compareAttachmentRefOrValue(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareAttachmentRefOrValue(entity1, entity2);
        const compareResult2 = service.compareAttachmentRefOrValue(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
