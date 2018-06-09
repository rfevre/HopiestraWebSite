import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { ProdConfig } from './blocks/config/prod.config';
import { HopiestraWebSiteAppModule } from './app.module';
import * as $ from 'jquery'; window['$'] = $; window['jQuery'] = $;

ProdConfig();

if (module['hot']) {
    module['hot'].accept();
}

platformBrowserDynamic().bootstrapModule(HopiestraWebSiteAppModule)
.then((success) => console.log(`Application started`))
.catch((err) => console.error(err));
