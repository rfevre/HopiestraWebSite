import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { InternationalTheme } from './international-theme.model';
import { InternationalThemeService } from './international-theme.service';

@Injectable()
export class InternationalThemePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private internationalThemeService: InternationalThemeService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.internationalThemeService.find(id)
                    .subscribe((internationalThemeResponse: HttpResponse<InternationalTheme>) => {
                        const internationalTheme: InternationalTheme = internationalThemeResponse.body;
                        this.ngbModalRef = this.internationalThemeModalRef(component, internationalTheme);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.internationalThemeModalRef(component, new InternationalTheme());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    internationalThemeModalRef(component: Component, internationalTheme: InternationalTheme): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.internationalTheme = internationalTheme;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
