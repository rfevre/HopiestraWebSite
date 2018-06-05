import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HopiestraWebSiteTagModule } from './tag/tag.module';
import { HopiestraWebSiteLanguageModule } from './language/language.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        HopiestraWebSiteTagModule,
        HopiestraWebSiteLanguageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HopiestraWebSiteEntityModule {}
