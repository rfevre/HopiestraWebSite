import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Theme } from './theme.model';
import { ThemePopupService } from './theme-popup.service';
import { ThemeService } from './theme.service';
import { ThemeSubscription, ThemeSubscriptionService } from '../theme-subscription';
import { Image, ImageService } from '../image';

@Component({
    selector: 'jhi-theme-dialog',
    templateUrl: './theme-dialog.component.html'
})
export class ThemeDialogComponent implements OnInit {

    theme: Theme;
    isSaving: boolean;

    themes: Theme[];

    themesubscriptions: ThemeSubscription[];

    images: Image[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private themeService: ThemeService,
        private themeSubscriptionService: ThemeSubscriptionService,
        private imageService: ImageService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.themeService.query()
            .subscribe((res: HttpResponse<Theme[]>) => { this.themes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.themeSubscriptionService.query()
            .subscribe((res: HttpResponse<ThemeSubscription[]>) => { this.themesubscriptions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.imageService.query()
            .subscribe((res: HttpResponse<Image[]>) => { this.images = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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

    trackThemeSubscriptionById(index: number, item: ThemeSubscription) {
        return item.id;
    }

    trackImageById(index: number, item: Image) {
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
