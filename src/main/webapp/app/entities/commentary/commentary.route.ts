import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CommentaryComponent } from './commentary.component';
import { CommentaryDetailComponent } from './commentary-detail.component';
import { CommentaryPopupComponent } from './commentary-dialog.component';
import { CommentaryDeletePopupComponent } from './commentary-delete-dialog.component';

@Injectable()
export class CommentaryResolvePagingParams implements Resolve<any> {

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

export const commentaryRoute: Routes = [
    {
        path: 'commentary',
        component: CommentaryComponent,
        resolve: {
            'pagingParams': CommentaryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.commentary.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'commentary/:id',
        component: CommentaryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.commentary.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentaryPopupRoute: Routes = [
    {
        path: 'commentary-new',
        component: CommentaryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.commentary.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commentary/:id/edit',
        component: CommentaryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.commentary.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commentary/:id/delete',
        component: CommentaryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hopiestraWebSiteApp.commentary.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
