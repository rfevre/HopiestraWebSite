import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InternationalTagComponent } from './international-tag.component';
import { InternationalTagDetailComponent } from './international-tag-detail.component';
import { InternationalTagPopupComponent } from './international-tag-dialog.component';
import { InternationalTagDeletePopupComponent } from './international-tag-delete-dialog.component';

@Injectable()
export class InternationalTagResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const internationalTagRoute: Routes = [
    {
        path: 'international-tag',
        component: InternationalTagComponent,
        resolve: {
            'pagingParams': InternationalTagResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'international-tag/:id',
        component: InternationalTagDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const internationalTagPopupRoute: Routes = [
    {
        path: 'international-tag-new',
        component: InternationalTagPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'international-tag/:id/edit',
        component: InternationalTagPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'international-tag/:id/delete',
        component: InternationalTagDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
