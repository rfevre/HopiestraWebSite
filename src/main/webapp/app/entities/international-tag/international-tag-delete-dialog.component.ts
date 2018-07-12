import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InternationalTag } from './international-tag.model';
import { InternationalTagPopupService } from './international-tag-popup.service';
import { InternationalTagService } from './international-tag.service';

@Component({
    selector: 'jhi-international-tag-delete-dialog',
    templateUrl: './international-tag-delete-dialog.component.html'
})
export class InternationalTagDeleteDialogComponent {

    internationalTag: InternationalTag;

    constructor(
        private internationalTagService: InternationalTagService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.internationalTagService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'internationalTagListModification',
                content: 'Deleted an internationalTag'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-international-tag-delete-popup',
    template: ''
})
export class InternationalTagDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private internationalTagPopupService: InternationalTagPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.internationalTagPopupService
                .open(InternationalTagDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
