import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InternationalArticleComponent } from './international-article.component';
import { InternationalArticleDetailComponent } from './international-article-detail.component';
import { InternationalArticlePopupComponent } from './international-article-dialog.component';
import { InternationalArticleDeletePopupComponent } from './international-article-delete-dialog.component';

@Injectable()
export class InternationalArticleResolvePagingParams implements Resolve<any> {

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

export const internationalArticleRoute: Routes = [
    {
        path: 'international-article',
        component: InternationalArticleComponent,
        resolve: {
            'pagingParams': InternationalArticleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalArticle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'international-article/:id',
        component: InternationalArticleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalArticle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const internationalArticlePopupRoute: Routes = [
    {
        path: 'international-article-new',
        component: InternationalArticlePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalArticle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'international-article/:id/edit',
        component: InternationalArticlePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalArticle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'international-article/:id/delete',
        component: InternationalArticleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.internationalArticle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
