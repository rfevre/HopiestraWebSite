/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { ThemeSubscriptionDetailComponent } from '../../../../../../main/webapp/app/entities/theme-subscription/theme-subscription-detail.component';
import { ThemeSubscriptionService } from '../../../../../../main/webapp/app/entities/theme-subscription/theme-subscription.service';
import { ThemeSubscription } from '../../../../../../main/webapp/app/entities/theme-subscription/theme-subscription.model';

describe('Component Tests', () => {

    describe('ThemeSubscription Management Detail Component', () => {
        let comp: ThemeSubscriptionDetailComponent;
        let fixture: ComponentFixture<ThemeSubscriptionDetailComponent>;
        let service: ThemeSubscriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [ThemeSubscriptionDetailComponent],
                providers: [
                    ThemeSubscriptionService
                ]
            })
            .overrideTemplate(ThemeSubscriptionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThemeSubscriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThemeSubscriptionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ThemeSubscription(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.themeSubscription).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
