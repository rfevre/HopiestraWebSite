/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalTagDetailComponent } from '../../../../../../main/webapp/app/entities/international-tag/international-tag-detail.component';
import { InternationalTagService } from '../../../../../../main/webapp/app/entities/international-tag/international-tag.service';
import { InternationalTag } from '../../../../../../main/webapp/app/entities/international-tag/international-tag.model';

describe('Component Tests', () => {

    describe('InternationalTag Management Detail Component', () => {
        let comp: InternationalTagDetailComponent;
        let fixture: ComponentFixture<InternationalTagDetailComponent>;
        let service: InternationalTagService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalTagDetailComponent],
                providers: [
                    InternationalTagService
                ]
            })
            .overrideTemplate(InternationalTagDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalTagDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalTagService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InternationalTag(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.internationalTag).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
