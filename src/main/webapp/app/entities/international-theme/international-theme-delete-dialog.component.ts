import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InternationalTheme } from './international-theme.model';
import { InternationalThemePopupService } from './international-theme-popup.service';
import { InternationalThemeService } from './international-theme.service';

@Component({
    selector: 'jhi-international-theme-delete-dialog',
    templateUrl: './international-theme-delete-dialog.component.html'
})
export class InternationalThemeDeleteDialogComponent {

    internationalTheme: InternationalTheme;

    constructor(
        private internationalThemeService: InternationalThemeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.internationalThemeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'internationalThemeListModification',
                content: 'Deleted an internationalTheme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-international-theme-delete-popup',
    template: ''
})
export class InternationalThemeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private internationalThemePopupService: InternationalThemePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.internationalThemePopupService
                .open(InternationalThemeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
