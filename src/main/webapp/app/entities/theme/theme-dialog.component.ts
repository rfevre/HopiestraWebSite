import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Theme } from './theme.model';
import { ThemePopupService } from './theme-popup.service';
import { ThemeService } from './theme.service';

@Component({
    selector: 'jhi-theme-dialog',
    templateUrl: './theme-dialog.component.html'
})
export class ThemeDialogComponent implements OnInit {

    theme: Theme;
    isSaving: boolean;

    themes: Theme[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private themeService: ThemeService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.themeService.query()
            .subscribe((res: HttpResponse<Theme[]>) => { this.themes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.theme, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.theme.id !== undefined) {
            this.subscribeToSaveResponse(
                this.themeService.update(this.theme));
        } else {
            this.subscribeToSaveResponse(
                this.themeService.create(this.theme));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Theme>>) {
        result.subscribe((res: HttpResponse<Theme>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Theme) {
        this.eventManager.broadcast({ name: 'themeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackThemeById(index: number, item: Theme) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-theme-popup',
    template: ''
})
export class ThemePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private themePopupService: ThemePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.themePopupService
                    .open(ThemeDialogComponent as Component, params['id']);
            } else {
                this.themePopupService
                    .open(ThemeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
