/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalThemeDialogComponent } from '../../../../../../main/webapp/app/entities/international-theme/international-theme-dialog.component';
import { InternationalThemeService } from '../../../../../../main/webapp/app/entities/international-theme/international-theme.service';
import { InternationalTheme } from '../../../../../../main/webapp/app/entities/international-theme/international-theme.model';
import { LanguageService } from '../../../../../../main/webapp/app/entities/language';

describe('Component Tests', () => {

    describe('InternationalTheme Management Dialog Component', () => {
        let comp: InternationalThemeDialogComponent;
        let fixture: ComponentFixture<InternationalThemeDialogComponent>;
        let service: InternationalThemeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalThemeDialogComponent],
                providers: [
                    LanguageService,
                    InternationalThemeService
                ]
            })
            .overrideTemplate(InternationalThemeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalThemeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalThemeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InternationalTheme(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.internationalTheme = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'internationalThemeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InternationalTheme();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.internationalTheme = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'internationalThemeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
