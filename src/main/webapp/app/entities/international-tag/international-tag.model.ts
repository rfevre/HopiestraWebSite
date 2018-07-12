import { BaseEntity } from './../../shared';

export class InternationalTag implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public language?: BaseEntity,
        public tags?: BaseEntity[],
    ) {
    }
}
