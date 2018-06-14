import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ThemeSubscription } from './theme-subscription.model';
import { ThemeSubscriptionPopupService } from './theme-subscription-popup.service';
import { ThemeSubscriptionService } from './theme-subscription.service';
import { Theme, ThemeService } from '../theme';

@Component({
    selector: 'jhi-theme-subscription-dialog',
    templateUrl: './theme-subscription-dialog.component.html'
})
export class ThemeSubscriptionDialogComponent implements OnInit {

    themeSubscription: ThemeSubscription;
    isSaving: boolean;

    themes: Theme[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private themeSubscriptionService: ThemeSubscriptionService,
        private themeService: ThemeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.themeService.query()
            .subscribe((res: HttpResponse<Theme[]>) => { this.themes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.themeSubscription.id !== undefined) {
            this.subscribeToSaveResponse(
                this.themeSubscriptionService.update(this.themeSubscription));
        } else {
            this.subscribeToSaveResponse(
                this.themeSubscriptionService.create(this.themeSubscription));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ThemeSubscription>>) {
        result.subscribe((res: HttpResponse<ThemeSubscription>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ThemeSubscription) {
        this.eventManager.broadcast({ name: 'themeSubscriptionListModification', content: 'OK'});
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
    selector: 'jhi-theme-subscription-popup',
    template: ''
})
export class ThemeSubscriptionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private themeSubscriptionPopupService: ThemeSubscriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.themeSubscriptionPopupService
                    .open(ThemeSubscriptionDialogComponent as Component, params['id']);
            } else {
                this.themeSubscriptionPopupService
                    .open(ThemeSubscriptionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
