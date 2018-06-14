/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { ThemeSubscriptionComponent } from '../../../../../../main/webapp/app/entities/theme-subscription/theme-subscription.component';
import { ThemeSubscriptionService } from '../../../../../../main/webapp/app/entities/theme-subscription/theme-subscription.service';
import { ThemeSubscription } from '../../../../../../main/webapp/app/entities/theme-subscription/theme-subscription.model';

describe('Component Tests', () => {

    describe('ThemeSubscription Management Component', () => {
        let comp: ThemeSubscriptionComponent;
        let fixture: ComponentFixture<ThemeSubscriptionComponent>;
        let service: ThemeSubscriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [ThemeSubscriptionComponent],
                providers: [
                    ThemeSubscriptionService
                ]
            })
            .overrideTemplate(ThemeSubscriptionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThemeSubscriptionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThemeSubscriptionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ThemeSubscription(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.themeSubscriptions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
