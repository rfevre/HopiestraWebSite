import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HopiestraWebSiteSharedModule } from '../../shared';
import {
    LanguageService,
    LanguagePopupService,
    LanguageComponent,
    LanguageDetailComponent,
    LanguageDialogComponent,
    LanguagePopupComponent,
    LanguageDeletePopupComponent,
    LanguageDeleteDialogComponent,
    languageRoute,
    languagePopupRoute,
    LanguageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...languageRoute,
    ...languagePopupRoute,
];

@NgModule({
    imports: [
        HopiestraWebSiteSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LanguageComponent,
        LanguageDetailComponent,
        LanguageDialogComponent,
        LanguageDeleteDialogComponent,
        LanguagePopupComponent,
        LanguageDeletePopupComponent,
    ],
    entryComponents: [
        LanguageComponent,
        LanguageDialogComponent,
        LanguagePopupComponent,
        LanguageDeleteDialogComponent,
        LanguageDeletePopupComponent,
    ],
    providers: [
        LanguageService,
        LanguagePopupService,
        LanguageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HopiestraWebSiteLanguageModule {}
