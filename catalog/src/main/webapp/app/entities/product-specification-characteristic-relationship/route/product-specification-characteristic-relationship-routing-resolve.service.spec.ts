import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProductSpecificationCharacteristicRelationship } from '../product-specification-characteristic-relationship.model';
import { ProductSpecificationCharacteristicRelationshipService } from '../service/product-specification-characteristic-relationship.service';

import { ProductSpecificationCharacteristicRelationshipRoutingResolveService } from './product-specification-characteristic-relationship-routing-resolve.service';

describe('ProductSpecificationCharacteristicRelationship routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProductSpecificationCharacteristicRelationshipRoutingResolveService;
  let service: ProductSpecificationCharacteristicRelationshipService;
  let resultProductSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ProductSpecificationCharacteristicRelationshipRoutingResolveService);
    service = TestBed.inject(ProductSpecificationCharacteristicRelationshipService);
    resultProductSpecificationCharacteristicRelationship = undefined;
  });

  describe('resolve', () => {
    it('should return IProductSpecificationCharacteristicRelationship returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSpecificationCharacteristicRelationship = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductSpecificationCharacteristicRelationship).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSpecificationCharacteristicRelationship = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProductSpecificationCharacteristicRelationship).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IProductSpecificationCharacteristicRelationship>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSpecificationCharacteristicRelationship = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductSpecificationCharacteristicRelationship).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
