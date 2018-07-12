/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { HopiestraWebSiteTestModule } from '../../../test.module';
import { InternationalArticleDialogComponent } from '../../../../../../main/webapp/app/entities/international-article/international-article-dialog.component';
import { InternationalArticleService } from '../../../../../../main/webapp/app/entities/international-article/international-article.service';
import { InternationalArticle } from '../../../../../../main/webapp/app/entities/international-article/international-article.model';
import { LanguageService } from '../../../../../../main/webapp/app/entities/language';
import { ArticleService } from '../../../../../../main/webapp/app/entities/article';

describe('Component Tests', () => {

    describe('InternationalArticle Management Dialog Component', () => {
        let comp: InternationalArticleDialogComponent;
        let fixture: ComponentFixture<InternationalArticleDialogComponent>;
        let service: InternationalArticleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HopiestraWebSiteTestModule],
                declarations: [InternationalArticleDialogComponent],
                providers: [
                    LanguageService,
                    ArticleService,
                    InternationalArticleService
                ]
            })
            .overrideTemplate(InternationalArticleDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InternationalArticleDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternationalArticleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InternationalArticle(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.internationalArticle = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'internationalArticleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InternationalArticle();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.internationalArticle = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'internationalArticleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
