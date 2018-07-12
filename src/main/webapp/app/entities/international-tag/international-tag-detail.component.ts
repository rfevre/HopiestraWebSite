import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { InternationalTag } from './international-tag.model';
import { InternationalTagService } from './international-tag.service';

@Component({
    selector: 'jhi-international-tag-detail',
    templateUrl: './international-tag-detail.component.html'
})
export class InternationalTagDetailComponent implements OnInit, OnDestroy {

    internationalTag: InternationalTag;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private internationalTagService: InternationalTagService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInternationalTags();
    }

    load(id) {
        this.internationalTagService.find(id)
            .subscribe((internationalTagResponse: HttpResponse<InternationalTag>) => {
                this.internationalTag = internationalTagResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInternationalTags() {
        this.eventSubscriber = this.eventManager.subscribe(
            'internationalTagListModification',
            (response) => this.load(this.internationalTag.id)
        );
    }
}
