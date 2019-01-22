/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalThemeComponent } from '../../../../../../main/webapp/app/entities/international-theme/international-theme.component';
import { InternationalThemeService } from '../../../../../../main/webapp/app/entities/international-theme/international-theme.service';
import { InternationalTheme } from '../../../../../../main/webapp/app/entities/international-theme/international-theme.model';

describe('Component Tests', () => {

    describe('InternationalTheme Management Component', () => {
        let comp: InternationalThemeComponent;
        let fixture: ComponentFixture<InternationalThemeComponent>;
        let service: InternationalThemeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalThemeComponent],
                providers: [
                    InternationalThemeService
                ]
            })
            .overrideTemplate(InternationalThemeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalThemeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalThemeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InternationalTheme(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.internationalThemes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
