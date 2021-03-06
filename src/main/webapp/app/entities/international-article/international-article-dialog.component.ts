import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { InternationalArticle } from './international-article.model';
import { InternationalArticlePopupService } from './international-article-popup.service';
import { InternationalArticleService } from './international-article.service';
import { Language, LanguageService } from '../language';
import { Article, ArticleService } from '../article';

import * as Quill from 'quill';
import ImageResize from 'quill-image-resize-module';
Quill.register('modules/imageResize', ImageResize);

@Component({
    selector: 'jhi-international-article-dialog',
    templateUrl: './international-article-dialog.component.html'
})
export class InternationalArticleDialogComponent implements OnInit {

    internationalArticle: InternationalArticle;
    isSaving: boolean;

    languages: Language[];

    articles: Article[];

    public editor_modules = {};

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private internationalArticleService: InternationalArticleService,
        private languageService: LanguageService,
        private articleService: ArticleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.languageService.query()
            .subscribe((res: HttpResponse<Language[]>) => { this.languages = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.articleService.query()
            .subscribe((res: HttpResponse<Article[]>) => { this.articles = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));

        this.editor_modules = {
            toolbar: {
                container: [
                    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
                    ['blockquote', 'code-block'],
                    [{ 'header': 1 }, { 'header': 2 }],               // custom button values
                    [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                    [{ 'script': 'sub'}, { 'script': 'super' }],      // superscript/subscript
                    [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
                    [{ 'direction': 'rtl' }],                         // text direction
                    [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
                    [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                    [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
                    [{ 'font': [] }],
                    [{ 'align': [] }],
                    ['clean'],                                         // remove formatting button
                    ['link', 'image', 'video']
                ]
            },
            imageResize: true
        };
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.internationalArticle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.internationalArticleService.update(this.internationalArticle));
        } else {
            this.subscribeToSaveResponse(
                this.internationalArticleService.create(this.internationalArticle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InternationalArticle>>) {
        result.subscribe((res: HttpResponse<InternationalArticle>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InternationalArticle) {
        this.eventManager.broadcast({ name: 'internationalArticleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }

    trackArticleById(index: number, item: Article) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-international-article-popup',
    template: ''
})
export class InternationalArticlePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private internationalArticlePopupService: InternationalArticlePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.internationalArticlePopupService
                    .open(InternationalArticleDialogComponent as Component, params['id']);
            } else {
                this.internationalArticlePopupService
                    .open(InternationalArticleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
