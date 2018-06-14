import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HopiestraWebSiteSharedModule } from '../../shared';
import {
    ThemeSubscriptionService,
    ThemeSubscriptionPopupService,
    ThemeSubscriptionComponent,
    ThemeSubscriptionDetailComponent,
    ThemeSubscriptionDialogComponent,
    ThemeSubscriptionPopupComponent,
    ThemeSubscriptionDeletePopupComponent,
    ThemeSubscriptionDeleteDialogComponent,
    themeSubscriptionRoute,
    themeSubscriptionPopupRoute,
    ThemeSubscriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...themeSubscriptionRoute,
    ...themeSubscriptionPopupRoute,
];

@NgModule({
    imports: [
        HopiestraWebSiteSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ThemeSubscriptionComponent,
        ThemeSubscriptionDetailComponent,
        ThemeSubscriptionDialogComponent,
        ThemeSubscriptionDeleteDialogComponent,
        ThemeSubscriptionPopupComponent,
        ThemeSubscriptionDeletePopupComponent,
    ],
    entryComponents: [
        ThemeSubscriptionComponent,
        ThemeSubscriptionDialogComponent,
        ThemeSubscriptionPopupComponent,
        ThemeSubscriptionDeleteDialogComponent,
        ThemeSubscriptionDeletePopupComponent,
    ],
    providers: [
        ThemeSubscriptionService,
        ThemeSubscriptionPopupService,
        ThemeSubscriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HopiestraWebSiteThemeSubscriptionModule {}
