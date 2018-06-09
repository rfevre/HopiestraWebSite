import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HopiestraWebSiteSharedModule } from '../../shared';

import 'froala-editor/js/froala_editor.pkgd.min.js';
import { FroalaEditorModule, FroalaViewModule } from 'angular-froala-wysiwyg';
import { SafeHtmlPipe } from '../../pipe/safe-html.pipe';

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
        RouterModule.forChild(ENTITY_STATES),
        FroalaEditorModule.forRoot(),
        FroalaViewModule.forRoot(),
    ],
    declarations: [
        InternationalArticleComponent,
        InternationalArticleDetailComponent,
        InternationalArticleDialogComponent,
        InternationalArticleDeleteDialogComponent,
        InternationalArticlePopupComponent,
        InternationalArticleDeletePopupComponent,
        SafeHtmlPipe,
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
