import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InternationalTag } from './international-tag.model';
import { InternationalTagPopupService } from './international-tag-popup.service';
import { InternationalTagService } from './international-tag.service';
import { Language, LanguageService } from '../language';
import { Tag, TagService } from '../tag';

@Component({
    selector: 'jhi-international-tag-dialog',
    templateUrl: './international-tag-dialog.component.html'
})
export class InternationalTagDialogComponent implements OnInit {

    internationalTag: InternationalTag;
    isSaving: boolean;

    languages: Language[];

    tags: Tag[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private internationalTagService: InternationalTagService,
        private languageService: LanguageService,
        private tagService: TagService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.languageService.query()
            .subscribe((res: HttpResponse<Language[]>) => { this.languages = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tagService.query()
            .subscribe((res: HttpResponse<Tag[]>) => { this.tags = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.internationalTag.id !== undefined) {
            this.subscribeToSaveResponse(
                this.internationalTagService.update(this.internationalTag));
        } else {
            this.subscribeToSaveResponse(
                this.internationalTagService.create(this.internationalTag));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InternationalTag>>) {
        result.subscribe((res: HttpResponse<InternationalTag>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InternationalTag) {
        this.eventManager.broadcast({ name: 'internationalTagListModification', content: 'OK'});
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

    trackTagById(index: number, item: Tag) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-international-tag-popup',
    template: ''
})
export class InternationalTagPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private internationalTagPopupService: InternationalTagPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.internationalTagPopupService
                    .open(InternationalTagDialogComponent as Component, params['id']);
            } else {
                this.internationalTagPopupService
                    .open(InternationalTagDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
