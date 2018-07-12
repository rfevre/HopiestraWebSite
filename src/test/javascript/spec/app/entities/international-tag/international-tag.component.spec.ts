/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalTagComponent } from '../../../../../../main/webapp/app/entities/international-tag/international-tag.component';
import { InternationalTagService } from '../../../../../../main/webapp/app/entities/international-tag/international-tag.service';
import { InternationalTag } from '../../../../../../main/webapp/app/entities/international-tag/international-tag.model';

describe('Component Tests', () => {

    describe('InternationalTag Management Component', () => {
        let comp: InternationalTagComponent;
        let fixture: ComponentFixture<InternationalTagComponent>;
        let service: InternationalTagService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalTagComponent],
                providers: [
                    InternationalTagService
                ]
            })
            .overrideTemplate(InternationalTagComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalTagComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalTagService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InternationalTag(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.internationalTags[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
