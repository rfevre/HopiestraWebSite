import { BaseEntity } from './../../shared';

export class Theme implements BaseEntity {
    constructor(
        public id?: number,
        public order?: number,
        public adminTitle?: string,
        public parentTheme?: BaseEntity,
        public themeSubscriptions?: BaseEntity[],
        public backgroundPicture?: BaseEntity,
        public internationalThemes?: BaseEntity[],
    ) {
    }
}
