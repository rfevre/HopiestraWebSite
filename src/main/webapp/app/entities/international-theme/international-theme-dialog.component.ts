import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InternationalTheme } from './international-theme.model';
import { InternationalThemePopupService } from './international-theme-popup.service';
import { InternationalThemeService } from './international-theme.service';
import { Language, LanguageService } from '../language';

@Component({
    selector: 'jhi-international-theme-dialog',
    templateUrl: './international-theme-dialog.component.html'
})
export class InternationalThemeDialogComponent implements OnInit {

    internationalTheme: InternationalTheme;
    isSaving: boolean;

    languages: Language[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private internationalThemeService: InternationalThemeService,
        private languageService: LanguageService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.languageService.query()
            .subscribe((res: HttpResponse<Language[]>) => { this.languages = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.internationalTheme.id !== undefined) {
            this.subscribeToSaveResponse(
                this.internationalThemeService.update(this.internationalTheme));
        } else {
            this.subscribeToSaveResponse(
                this.internationalThemeService.create(this.internationalTheme));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InternationalTheme>>) {
        result.subscribe((res: HttpResponse<InternationalTheme>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InternationalTheme) {
        this.eventManager.broadcast({ name: 'internationalThemeListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-international-theme-popup',
    template: ''
})
export class InternationalThemePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private internationalThemePopupService: InternationalThemePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.internationalThemePopupService
                    .open(InternationalThemeDialogComponent as Component, params['id']);
            } else {
                this.internationalThemePopupService
                    .open(InternationalThemeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
