import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProductSpecificationCharacteristic } from '../product-specification-characteristic.model';
import { ProductSpecificationCharacteristicService } from '../service/product-specification-characteristic.service';

import { ProductSpecificationCharacteristicRoutingResolveService } from './product-specification-characteristic-routing-resolve.service';

describe('ProductSpecificationCharacteristic routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProductSpecificationCharacteristicRoutingResolveService;
  let service: ProductSpecificationCharacteristicService;
  let resultProductSpecificationCharacteristic: IProductSpecificationCharacteristic | null | undefined;

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
    routingResolveService = TestBed.inject(ProductSpecificationCharacteristicRoutingResolveService);
    service = TestBed.inject(ProductSpecificationCharacteristicService);
    resultProductSpecificationCharacteristic = undefined;
  });

  describe('resolve', () => {
    it('should return IProductSpecificationCharacteristic returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSpecificationCharacteristic = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductSpecificationCharacteristic).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSpecificationCharacteristic = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProductSpecificationCharacteristic).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IProductSpecificationCharacteristic>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSpecificationCharacteristic = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductSpecificationCharacteristic).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
