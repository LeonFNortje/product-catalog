import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBundledProductSpecification } from '../bundled-product-specification.model';
import { BundledProductSpecificationService } from '../service/bundled-product-specification.service';

import { BundledProductSpecificationRoutingResolveService } from './bundled-product-specification-routing-resolve.service';

describe('BundledProductSpecification routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BundledProductSpecificationRoutingResolveService;
  let service: BundledProductSpecificationService;
  let resultBundledProductSpecification: IBundledProductSpecification | null | undefined;

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
    routingResolveService = TestBed.inject(BundledProductSpecificationRoutingResolveService);
    service = TestBed.inject(BundledProductSpecificationService);
    resultBundledProductSpecification = undefined;
  });

  describe('resolve', () => {
    it('should return IBundledProductSpecification returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBundledProductSpecification = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultBundledProductSpecification).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBundledProductSpecification = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBundledProductSpecification).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IBundledProductSpecification>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBundledProductSpecification = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultBundledProductSpecification).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
