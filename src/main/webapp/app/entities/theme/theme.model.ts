import { BaseEntity } from './../../shared';

export class Theme implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public backgroundPictureContentType?: string,
        public backgroundPicture?: any,
        public order?: number,
        public parentTheme?: BaseEntity,
        public themeSubscriptions?: BaseEntity[],
    ) {
    }
}
