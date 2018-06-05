import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { InternationalArticle } from './international-article.model';
import { InternationalArticleService } from './international-article.service';

@Component({
    selector: 'jhi-international-article-detail',
    templateUrl: './international-article-detail.component.html'
})
export class InternationalArticleDetailComponent implements OnInit, OnDestroy {

    internationalArticle: InternationalArticle;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private internationalArticleService: InternationalArticleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInternationalArticles();
    }

    load(id) {
        this.internationalArticleService.find(id)
            .subscribe((internationalArticleResponse: HttpResponse<InternationalArticle>) => {
                this.internationalArticle = internationalArticleResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInternationalArticles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'internationalArticleListModification',
            (response) => this.load(this.internationalArticle.id)
        );
    }
}
