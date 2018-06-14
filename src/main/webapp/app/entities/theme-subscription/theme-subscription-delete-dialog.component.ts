import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ThemeSubscription } from './theme-subscription.model';
import { ThemeSubscriptionPopupService } from './theme-subscription-popup.service';
import { ThemeSubscriptionService } from './theme-subscription.service';

@Component({
    selector: 'jhi-theme-subscription-delete-dialog',
    templateUrl: './theme-subscription-delete-dialog.component.html'
})
export class ThemeSubscriptionDeleteDialogComponent {

    themeSubscription: ThemeSubscription;

    constructor(
        private themeSubscriptionService: ThemeSubscriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.themeSubscriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'themeSubscriptionListModification',
                content: 'Deleted an themeSubscription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-theme-subscription-delete-popup',
    template: ''
})
export class ThemeSubscriptionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private themeSubscriptionPopupService: ThemeSubscriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.themeSubscriptionPopupService
                .open(ThemeSubscriptionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
