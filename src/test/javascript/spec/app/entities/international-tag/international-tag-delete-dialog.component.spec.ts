/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalTagDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/international-tag/international-tag-delete-dialog.component';
import { InternationalTagService } from '../../../../../../main/webapp/app/entities/international-tag/international-tag.service';

describe('Component Tests', () => {

    describe('InternationalTag Management Delete Component', () => {
        let comp: InternationalTagDeleteDialogComponent;
        let fixture: ComponentFixture<InternationalTagDeleteDialogComponent>;
        let service: InternationalTagService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalTagDeleteDialogComponent],
                providers: [
                    InternationalTagService
                ]
            })
            .overrideTemplate(InternationalTagDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalTagDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalTagService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
