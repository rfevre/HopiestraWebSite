/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalArticleComponent } from '../../../../../../main/webapp/app/entities/international-article/international-article.component';
import { InternationalArticleService } from '../../../../../../main/webapp/app/entities/international-article/international-article.service';
import { InternationalArticle } from '../../../../../../main/webapp/app/entities/international-article/international-article.model';

describe('Component Tests', () => {

    describe('InternationalArticle Management Component', () => {
        let comp: InternationalArticleComponent;
        let fixture: ComponentFixture<InternationalArticleComponent>;
        let service: InternationalArticleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalArticleComponent],
                providers: [
                    InternationalArticleService
                ]
            })
            .overrideTemplate(InternationalArticleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalArticleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalArticleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InternationalArticle(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.internationalArticles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
