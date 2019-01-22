import { BaseEntity } from './../../shared';

export class Theme implements BaseEntity {
    constructor(
        public id?: number,
        public order?: number,
        public parentTheme?: BaseEntity,
        public themeSubscriptions?: BaseEntity[],
        public backgroundPicture?: BaseEntity,
    ) {
    }
}
