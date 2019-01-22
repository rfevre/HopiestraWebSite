import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InternationalThemeComponent } from './international-theme.component';
import { InternationalThemeDetailComponent } from './international-theme-detail.component';
import { InternationalThemePopupComponent } from './international-theme-dialog.component';
import { InternationalThemeDeletePopupComponent } from './international-theme-delete-dialog.component';

@Injectable()
export class InternationalThemeResolvePagingParams implements Resolve<any> {

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

export const internationalThemeRoute: Routes = [
    {
        path: 'international-theme',
        component: InternationalThemeComponent,
        resolve: {
            'pagingParams': InternationalThemeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalTheme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'international-theme/:id',
        component: InternationalThemeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalTheme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const internationalThemePopupRoute: Routes = [
    {
        path: 'international-theme-new',
        component: InternationalThemePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalTheme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'international-theme/:id/edit',
        component: InternationalThemePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalTheme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'international-theme/:id/delete',
        component: InternationalThemeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalTheme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
