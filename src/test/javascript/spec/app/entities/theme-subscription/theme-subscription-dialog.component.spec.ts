/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { ThemeSubscriptionDialogComponent } from '../../../../../../main/webapp/app/entities/theme-subscription/theme-subscription-dialog.component';
import { ThemeSubscriptionService } from '../../../../../../main/webapp/app/entities/theme-subscription/theme-subscription.service';
import { ThemeSubscription } from '../../../../../../main/webapp/app/entities/theme-subscription/theme-subscription.model';
import { ThemeService } from '../../../../../../main/webapp/app/entities/theme';

describe('Component Tests', () => {

    describe('ThemeSubscription Management Dialog Component', () => {
        let comp: ThemeSubscriptionDialogComponent;
        let fixture: ComponentFixture<ThemeSubscriptionDialogComponent>;
        let service: ThemeSubscriptionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [ThemeSubscriptionDialogComponent],
                providers: [
                    ThemeService,
                    ThemeSubscriptionService
                ]
            })
            .overrideTemplate(ThemeSubscriptionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThemeSubscriptionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThemeSubscriptionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ThemeSubscription(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.themeSubscription = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'themeSubscriptionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ThemeSubscription();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.themeSubscription = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'themeSubscriptionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
