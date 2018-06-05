import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HopiestraWebSiteSharedModule } from '../../shared';
import {
    InternationalArticleService,
    InternationalArticlePopupService,
    InternationalArticleComponent,
    InternationalArticleDetailComponent,
    InternationalArticleDialogComponent,
    InternationalArticlePopupComponent,
    InternationalArticleDeletePopupComponent,
    InternationalArticleDeleteDialogComponent,
    internationalArticleRoute,
    internationalArticlePopupRoute,
    InternationalArticleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...internationalArticleRoute,
    ...internationalArticlePopupRoute,
];

@NgModule({
    imports: [
        HopiestraWebSiteSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InternationalArticleComponent,
        InternationalArticleDetailComponent,
        InternationalArticleDialogComponent,
        InternationalArticleDeleteDialogComponent,
        InternationalArticlePopupComponent,
        InternationalArticleDeletePopupComponent,
    ],
    entryComponents: [
        InternationalArticleComponent,
        InternationalArticleDialogComponent,
        InternationalArticlePopupComponent,
        InternationalArticleDeleteDialogComponent,
        InternationalArticleDeletePopupComponent,
    ],
    providers: [
        InternationalArticleService,
        InternationalArticlePopupService,
        InternationalArticleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HopiestraWebSiteInternationalArticleModule {}
