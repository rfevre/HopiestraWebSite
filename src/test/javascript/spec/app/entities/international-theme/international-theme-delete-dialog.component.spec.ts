/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalThemeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/international-theme/international-theme-delete-dialog.component';
import { InternationalThemeService } from '../../../../../../main/webapp/app/entities/international-theme/international-theme.service';

describe('Component Tests', () => {

    describe('InternationalTheme Management Delete Component', () => {
        let comp: InternationalThemeDeleteDialogComponent;
        let fixture: ComponentFixture<InternationalThemeDeleteDialogComponent>;
        let service: InternationalThemeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalThemeDeleteDialogComponent],
                providers: [
                    InternationalThemeService
                ]
            })
            .overrideTemplate(InternationalThemeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalThemeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalThemeService);
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
