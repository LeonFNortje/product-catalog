import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProductSpecificationCharacteristicService } from '../service/product-specification-characteristic.service';

import { ProductSpecificationCharacteristicComponent } from './product-specification-characteristic.component';
import SpyInstance = jest.SpyInstance;

describe('ProductSpecificationCharacteristic Management Component', () => {
  let comp: ProductSpecificationCharacteristicComponent;
  let fixture: ComponentFixture<ProductSpecificationCharacteristicComponent>;
  let service: ProductSpecificationCharacteristicService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          { path: 'product-specification-characteristic', component: ProductSpecificationCharacteristicComponent },
        ]),
        HttpClientTestingModule,
      ],
      declarations: [ProductSpecificationCharacteristicComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ProductSpecificationCharacteristicComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductSpecificationCharacteristicComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProductSpecificationCharacteristicService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.productSpecificationCharacteristics?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to productSpecificationCharacteristicService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getProductSpecificationCharacteristicIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getProductSpecificationCharacteristicIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });

  it('should load a page', () => {
    // WHEN
    comp.navigateToPage(1);

    // THEN
    expect(routerNavigateSpy).toHaveBeenCalled();
  });

  it('should calculate the sort attribute for an id', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['id,desc'] }));
  });

  it('should calculate the sort attribute for a non-id attribute', () => {
    // GIVEN
    comp.predicate = 'name';

    // WHEN
    comp.navigateToWithComponentValues();

    // THEN
    expect(routerNavigateSpy).toHaveBeenLastCalledWith(
      expect.anything(),
      expect.objectContaining({
        queryParams: expect.objectContaining({
          sort: ['name,asc'],
        }),
      })
    );
  });

  it('should re-initialize the page', () => {
    // WHEN
    comp.loadPage(1);
    comp.reset();

    // THEN
    expect(comp.page).toEqual(1);
    expect(service.query).toHaveBeenCalledTimes(2);
    expect(comp.productSpecificationCharacteristics?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });
});
