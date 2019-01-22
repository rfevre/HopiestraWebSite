import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { InternationalTheme } from './international-theme.model';
import { InternationalThemeService } from './international-theme.service';

@Component({
    selector: 'jhi-international-theme-detail',
    templateUrl: './international-theme-detail.component.html'
})
export class InternationalThemeDetailComponent implements OnInit, OnDestroy {

    internationalTheme: InternationalTheme;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private internationalThemeService: InternationalThemeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInternationalThemes();
    }

    load(id) {
        this.internationalThemeService.find(id)
            .subscribe((internationalThemeResponse: HttpResponse<InternationalTheme>) => {
                this.internationalTheme = internationalThemeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInternationalThemes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'internationalThemeListModification',
            (response) => this.load(this.internationalTheme.id)
        );
    }
}
