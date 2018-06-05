import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InternationalArticle } from './international-article.model';
import { InternationalArticlePopupService } from './international-article-popup.service';
import { InternationalArticleService } from './international-article.service';

@Component({
    selector: 'jhi-international-article-delete-dialog',
    templateUrl: './international-article-delete-dialog.component.html'
})
export class InternationalArticleDeleteDialogComponent {

    internationalArticle: InternationalArticle;

    constructor(
        private internationalArticleService: InternationalArticleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.internationalArticleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'internationalArticleListModification',
                content: 'Deleted an internationalArticle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-international-article-delete-popup',
    template: ''
})
export class InternationalArticleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private internationalArticlePopupService: InternationalArticlePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.internationalArticlePopupService
                .open(InternationalArticleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
