import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ThemeComponent } from './theme.component';
import { ThemeDetailComponent } from './theme-detail.component';
import { ThemePopupComponent } from './theme-dialog.component';
import { ThemeDeletePopupComponent } from './theme-delete-dialog.component';

@Injectable()
export class ThemeResolvePagingParams implements Resolve<any> {

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

export const themeRoute: Routes = [
    {
        path: 'theme',
        component: ThemeComponent,
        resolve: {
            'pagingParams': ThemeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'theme/:id',
        component: ThemeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const themePopupRoute: Routes = [
    {
        path: 'theme-new',
        component: ThemePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'theme/:id/edit',
        component: ThemePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'theme/:id/delete',
        component: ThemeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
