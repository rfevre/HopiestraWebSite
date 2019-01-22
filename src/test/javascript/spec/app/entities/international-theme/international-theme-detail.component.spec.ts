/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalThemeDetailComponent } from '../../../../../../main/webapp/app/entities/international-theme/international-theme-detail.component';
import { InternationalThemeService } from '../../../../../../main/webapp/app/entities/international-theme/international-theme.service';
import { InternationalTheme } from '../../../../../../main/webapp/app/entities/international-theme/international-theme.model';

describe('Component Tests', () => {

    describe('InternationalTheme Management Detail Component', () => {
        let comp: InternationalThemeDetailComponent;
        let fixture: ComponentFixture<InternationalThemeDetailComponent>;
        let service: InternationalThemeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalThemeDetailComponent],
                providers: [
                    InternationalThemeService
                ]
            })
            .overrideTemplate(InternationalThemeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalThemeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalThemeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InternationalTheme(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.internationalTheme).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
