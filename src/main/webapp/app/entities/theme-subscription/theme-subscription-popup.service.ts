import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ThemeSubscription } from './theme-subscription.model';
import { ThemeSubscriptionService } from './theme-subscription.service';

@Injectable()
export class ThemeSubscriptionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private themeSubscriptionService: ThemeSubscriptionService

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
                this.themeSubscriptionService.find(id)
                    .subscribe((themeSubscriptionResponse: HttpResponse<ThemeSubscription>) => {
                        const themeSubscription: ThemeSubscription = themeSubscriptionResponse.body;
                        this.ngbModalRef = this.themeSubscriptionModalRef(component, themeSubscription);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.themeSubscriptionModalRef(component, new ThemeSubscription());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    themeSubscriptionModalRef(component: Component, themeSubscription: ThemeSubscription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.themeSubscription = themeSubscription;
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
