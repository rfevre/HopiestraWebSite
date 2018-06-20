import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HopiestraWebSiteSharedModule } from '../../shared';
import { HopiestraWebSiteAdminModule } from '../../admin/admin.module';
import {
    ArticleService,
    ArticlePopupService,
    ArticleComponent,
    ArticleDetailComponent,
    ArticleDialogComponent,
    ArticlePopupComponent,
    ArticleDeletePopupComponent,
    ArticleDeleteDialogComponent,
    articleRoute,
    articlePopupRoute,
    ArticleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...articleRoute,
    ...articlePopupRoute,
];

@NgModule({
    imports: [
        HopiestraWebSiteSharedModule,
        HopiestraWebSiteAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ArticleComponent,
        ArticleDetailComponent,
        ArticleDialogComponent,
        ArticleDeleteDialogComponent,
        ArticlePopupComponent,
        ArticleDeletePopupComponent,
    ],
    entryComponents: [
        ArticleComponent,
        ArticleDialogComponent,
        ArticlePopupComponent,
        ArticleDeleteDialogComponent,
        ArticleDeletePopupComponent,
    ],
    providers: [
        ArticleService,
        ArticlePopupService,
        ArticleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HopiestraWebSiteArticleModule {}
