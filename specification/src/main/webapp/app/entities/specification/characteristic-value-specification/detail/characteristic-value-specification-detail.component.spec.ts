import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CharacteristicValueSpecificationDetailComponent } from './characteristic-value-specification-detail.component';

describe('CharacteristicValueSpecification Management Detail Component', () => {
  let comp: CharacteristicValueSpecificationDetailComponent;
  let fixture: ComponentFixture<CharacteristicValueSpecificationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CharacteristicValueSpecificationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ characteristicValueSpecification: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CharacteristicValueSpecificationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CharacteristicValueSpecificationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load characteristicValueSpecification on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.characteristicValueSpecification).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
