/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalArticleDetailComponent } from '../../../../../../main/webapp/app/entities/international-article/international-article-detail.component';
import { InternationalArticleService } from '../../../../../../main/webapp/app/entities/international-article/international-article.service';
import { InternationalArticle } from '../../../../../../main/webapp/app/entities/international-article/international-article.model';

describe('Component Tests', () => {

    describe('InternationalArticle Management Detail Component', () => {
        let comp: InternationalArticleDetailComponent;
        let fixture: ComponentFixture<InternationalArticleDetailComponent>;
        let service: InternationalArticleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalArticleDetailComponent],
                providers: [
                    InternationalArticleService
                ]
            })
            .overrideTemplate(InternationalArticleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalArticleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalArticleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InternationalArticle(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.internationalArticle).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
