import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Commentary } from './commentary.model';
import { CommentaryPopupService } from './commentary-popup.service';
import { CommentaryService } from './commentary.service';
import { User, UserService } from '../../shared';
import { Article, ArticleService } from '../article';

@Component({
    selector: 'jhi-commentary-dialog',
    templateUrl: './commentary-dialog.component.html'
})
export class CommentaryDialogComponent implements OnInit {

    commentary: Commentary;
    isSaving: boolean;

    users: User[];

    articles: Article[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private commentaryService: CommentaryService,
        private userService: UserService,
        private articleService: ArticleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.articleService.query()
            .subscribe((res: HttpResponse<Article[]>) => { this.articles = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commentary.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commentaryService.update(this.commentary));
        } else {
            this.subscribeToSaveResponse(
                this.commentaryService.create(this.commentary));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Commentary>>) {
        result.subscribe((res: HttpResponse<Commentary>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Commentary) {
        this.eventManager.broadcast({ name: 'commentaryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackArticleById(index: number, item: Article) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-commentary-popup',
    template: ''
})
export class CommentaryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentaryPopupService: CommentaryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commentaryPopupService
                    .open(CommentaryDialogComponent as Component, params['id']);
            } else {
                this.commentaryPopupService
                    .open(CommentaryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
