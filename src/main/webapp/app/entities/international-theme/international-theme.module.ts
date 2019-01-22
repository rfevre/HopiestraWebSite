import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HopiestraWebSiteSharedModule } from '../../shared';
import {
    InternationalThemeService,
    InternationalThemePopupService,
    InternationalThemeComponent,
    InternationalThemeDetailComponent,
    InternationalThemeDialogComponent,
    InternationalThemePopupComponent,
    InternationalThemeDeletePopupComponent,
    InternationalThemeDeleteDialogComponent,
    internationalThemeRoute,
    internationalThemePopupRoute,
    InternationalThemeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...internationalThemeRoute,
    ...internationalThemePopupRoute,
];

@NgModule({
    imports: [
        HopiestraWebSiteSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InternationalThemeComponent,
        InternationalThemeDetailComponent,
        InternationalThemeDialogComponent,
        InternationalThemeDeleteDialogComponent,
        InternationalThemePopupComponent,
        InternationalThemeDeletePopupComponent,
    ],
    entryComponents: [
        InternationalThemeComponent,
        InternationalThemeDialogComponent,
        InternationalThemePopupComponent,
        InternationalThemeDeleteDialogComponent,
        InternationalThemeDeletePopupComponent,
    ],
    providers: [
        InternationalThemeService,
        InternationalThemePopupService,
        InternationalThemeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HopiestraWebSiteInternationalThemeModule {}
