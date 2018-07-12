/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalTagDialogComponent } from '../../../../../../main/webapp/app/entities/international-tag/international-tag-dialog.component';
import { InternationalTagService } from '../../../../../../main/webapp/app/entities/international-tag/international-tag.service';
import { InternationalTag } from '../../../../../../main/webapp/app/entities/international-tag/international-tag.model';
import { LanguageService } from '../../../../../../main/webapp/app/entities/language';
import { TagService } from '../../../../../../main/webapp/app/entities/tag';

describe('Component Tests', () => {

    describe('InternationalTag Management Dialog Component', () => {
        let comp: InternationalTagDialogComponent;
        let fixture: ComponentFixture<InternationalTagDialogComponent>;
        let service: InternationalTagService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalTagDialogComponent],
                providers: [
                    LanguageService,
                    TagService,
                    InternationalTagService
                ]
            })
            .overrideTemplate(InternationalTagDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalTagDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalTagService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InternationalTag(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.internationalTag = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'internationalTagListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InternationalTag();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.internationalTag = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'internationalTagListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
