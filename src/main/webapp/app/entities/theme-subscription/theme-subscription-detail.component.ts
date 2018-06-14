import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ThemeSubscription } from './theme-subscription.model';
import { ThemeSubscriptionService } from './theme-subscription.service';

@Component({
    selector: 'jhi-theme-subscription-detail',
    templateUrl: './theme-subscription-detail.component.html'
})
export class ThemeSubscriptionDetailComponent implements OnInit, OnDestroy {

    themeSubscription: ThemeSubscription;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private themeSubscriptionService: ThemeSubscriptionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInThemeSubscriptions();
    }

    load(id) {
        this.themeSubscriptionService.find(id)
            .subscribe((themeSubscriptionResponse: HttpResponse<ThemeSubscription>) => {
                this.themeSubscription = themeSubscriptionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInThemeSubscriptions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'themeSubscriptionListModification',
            (response) => this.load(this.themeSubscription.id)
        );
    }
}
