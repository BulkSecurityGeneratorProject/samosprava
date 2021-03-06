/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SamospravaTestModule } from '../../../test.module';
import { BudgetUpdateComponent } from 'app/entities/budget/budget-update.component';
import { BudgetService } from 'app/entities/budget/budget.service';
import { Budget } from 'app/shared/model/budget.model';

describe('Component Tests', () => {
  describe('Budget Management Update Component', () => {
    let comp: BudgetUpdateComponent;
    let fixture: ComponentFixture<BudgetUpdateComponent>;
    let service: BudgetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SamospravaTestModule],
        declarations: [BudgetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BudgetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BudgetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BudgetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Budget('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Budget();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
