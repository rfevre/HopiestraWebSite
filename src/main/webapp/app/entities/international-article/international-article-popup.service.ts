import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { InternationalArticle } from './international-article.model';
import { InternationalArticleService } from './international-article.service';

@Injectable()
export class InternationalArticlePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private internationalArticleService: InternationalArticleService

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
                this.internationalArticleService.find(id)
                    .subscribe((internationalArticleResponse: HttpResponse<InternationalArticle>) => {
                        const internationalArticle: InternationalArticle = internationalArticleResponse.body;
                        this.ngbModalRef = this.internationalArticleModalRef(component, internationalArticle);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.internationalArticleModalRef(component, new InternationalArticle());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    internationalArticleModalRef(component: Component, internationalArticle: InternationalArticle): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static', windowClass: 'modal-full-width'});
        modalRef.componentInstance.internationalArticle = internationalArticle;
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
