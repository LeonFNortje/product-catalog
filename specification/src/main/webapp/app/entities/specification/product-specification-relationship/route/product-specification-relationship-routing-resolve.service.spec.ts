import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProductSpecificationRelationship } from '../product-specification-relationship.model';
import { ProductSpecificationRelationshipService } from '../service/product-specification-relationship.service';

import { ProductSpecificationRelationshipRoutingResolveService } from './product-specification-relationship-routing-resolve.service';

describe('ProductSpecificationRelationship routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProductSpecificationRelationshipRoutingResolveService;
  let service: ProductSpecificationRelationshipService;
  let resultProductSpecificationRelationship: IProductSpecificationRelationship | null | undefined;

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
    routingResolveService = TestBed.inject(ProductSpecificationRelationshipRoutingResolveService);
    service = TestBed.inject(ProductSpecificationRelationshipService);
    resultProductSpecificationRelationship = undefined;
  });

  describe('resolve', () => {
    it('should return IProductSpecificationRelationship returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSpecificationRelationship = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductSpecificationRelationship).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSpecificationRelationship = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProductSpecificationRelationship).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IProductSpecificationRelationship>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSpecificationRelationship = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductSpecificationRelationship).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
