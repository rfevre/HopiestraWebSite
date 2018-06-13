import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HopiestraWebSiteSharedModule } from '../../shared';
import {
    ThemeService,
    ThemePopupService,
    ThemeComponent,
    ThemeDetailComponent,
    ThemeDialogComponent,
    ThemePopupComponent,
    ThemeDeletePopupComponent,
    ThemeDeleteDialogComponent,
    themeRoute,
    themePopupRoute,
    ThemeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...themeRoute,
    ...themePopupRoute,
];

@NgModule({
    imports: [
        HopiestraWebSiteSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ThemeComponent,
        ThemeDetailComponent,
        ThemeDialogComponent,
        ThemeDeleteDialogComponent,
        ThemePopupComponent,
        ThemeDeletePopupComponent,
    ],
    entryComponents: [
        ThemeComponent,
        ThemeDialogComponent,
        ThemePopupComponent,
        ThemeDeleteDialogComponent,
        ThemeDeletePopupComponent,
    ],
    providers: [
        ThemeService,
        ThemePopupService,
        ThemeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HopiestraWebSiteThemeModule {}
