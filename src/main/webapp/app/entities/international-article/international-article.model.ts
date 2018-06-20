import { BaseEntity } from './../../shared';

export class InternationalArticle implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public content?: any,
        public language?: BaseEntity,
        public tags?: BaseEntity[],
        public article?: BaseEntity,
    ) {
    }
}
