import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HopiestraWebSiteTagModule } from './tag/tag.module';
import { HopiestraWebSiteLanguageModule } from './language/language.module';
import { HopiestraWebSiteInternationalArticleModule } from './international-article/international-article.module';
import { HopiestraWebSiteThemeModule } from './theme/theme.module';
import { HopiestraWebSiteThemeSubscriptionModule } from './theme-subscription/theme-subscription.module';
import { HopiestraWebSiteArticleModule } from './article/article.module';
import { HopiestraWebSiteCommentaryModule } from './commentary/commentary.module';
import { HopiestraWebSiteInternationalTagModule } from './international-tag/international-tag.module';
import { HopiestraWebSiteImageModule } from './image/image.module';
import { HopiestraWebSiteInternationalThemeModule } from './international-theme/international-theme.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        HopiestraWebSiteTagModule,
        HopiestraWebSiteLanguageModule,
        HopiestraWebSiteInternationalArticleModule,
        HopiestraWebSiteThemeModule,
        HopiestraWebSiteThemeSubscriptionModule,
        HopiestraWebSiteArticleModule,
        HopiestraWebSiteCommentaryModule,
        HopiestraWebSiteInternationalTagModule,
        HopiestraWebSiteImageModule,
        HopiestraWebSiteInternationalThemeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HopiestraWebSiteEntityModule {}
