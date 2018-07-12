import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HopiestraWebSiteSharedModule } from '../../shared';
import {
    InternationalTagService,
    InternationalTagPopupService,
    InternationalTagComponent,
    InternationalTagDetailComponent,
    InternationalTagDialogComponent,
    InternationalTagPopupComponent,
    InternationalTagDeletePopupComponent,
    InternationalTagDeleteDialogComponent,
    internationalTagRoute,
    internationalTagPopupRoute,
    InternationalTagResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...internationalTagRoute,
    ...internationalTagPopupRoute,
];

@NgModule({
    imports: [
        HopiestraWebSiteSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InternationalTagComponent,
        InternationalTagDetailComponent,
        InternationalTagDialogComponent,
        InternationalTagDeleteDialogComponent,
        InternationalTagPopupComponent,
        InternationalTagDeletePopupComponent,
    ],
    entryComponents: [
        InternationalTagComponent,
        InternationalTagDialogComponent,
        InternationalTagPopupComponent,
        InternationalTagDeleteDialogComponent,
        InternationalTagDeletePopupComponent,
    ],
    providers: [
        InternationalTagService,
        InternationalTagPopupService,
        InternationalTagResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HopiestraWebSiteInternationalTagModule {}
