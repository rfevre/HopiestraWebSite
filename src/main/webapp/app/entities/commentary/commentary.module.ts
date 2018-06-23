import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HopiestraWebSiteSharedModule } from '../../shared';
import { HopiestraWebSiteAdminModule } from '../../admin/admin.module';
import {
    CommentaryService,
    CommentaryPopupService,
    CommentaryComponent,
    CommentaryDetailComponent,
    CommentaryDialogComponent,
    CommentaryPopupComponent,
    CommentaryDeletePopupComponent,
    CommentaryDeleteDialogComponent,
    commentaryRoute,
    commentaryPopupRoute,
    CommentaryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...commentaryRoute,
    ...commentaryPopupRoute,
];

@NgModule({
    imports: [
        HopiestraWebSiteSharedModule,
        HopiestraWebSiteAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CommentaryComponent,
        CommentaryDetailComponent,
        CommentaryDialogComponent,
        CommentaryDeleteDialogComponent,
        CommentaryPopupComponent,
        CommentaryDeletePopupComponent,
    ],
    entryComponents: [
        CommentaryComponent,
        CommentaryDialogComponent,
        CommentaryPopupComponent,
        CommentaryDeleteDialogComponent,
        CommentaryDeletePopupComponent,
    ],
    providers: [
        CommentaryService,
        CommentaryPopupService,
        CommentaryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HopiestraWebSiteCommentaryModule {}
