import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TargetProductSchemaDetailComponent } from './target-product-schema-detail.component';

describe('TargetProductSchema Management Detail Component', () => {
  let comp: TargetProductSchemaDetailComponent;
  let fixture: ComponentFixture<TargetProductSchemaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TargetProductSchemaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ targetProductSchema: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TargetProductSchemaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TargetProductSchemaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load targetProductSchema on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.targetProductSchema).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
