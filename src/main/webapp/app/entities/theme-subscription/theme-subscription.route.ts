import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ThemeSubscriptionComponent } from './theme-subscription.component';
import { ThemeSubscriptionDetailComponent } from './theme-subscription-detail.component';
import { ThemeSubscriptionPopupComponent } from './theme-subscription-dialog.component';
import { ThemeSubscriptionDeletePopupComponent } from './theme-subscription-delete-dialog.component';

@Injectable()
export class ThemeSubscriptionResolvePagingParams implements Resolve<any> {

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

export const themeSubscriptionRoute: Routes = [
    {
        path: 'theme-subscription',
        component: ThemeSubscriptionComponent,
        resolve: {
            'pagingParams': ThemeSubscriptionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.themeSubscription.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'theme-subscription/:id',
        component: ThemeSubscriptionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.themeSubscription.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const themeSubscriptionPopupRoute: Routes = [
    {
        path: 'theme-subscription-new',
        component: ThemeSubscriptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.themeSubscription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'theme-subscription/:id/edit',
        component: ThemeSubscriptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.themeSubscription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'theme-subscription/:id/delete',
        component: ThemeSubscriptionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.themeSubscription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
