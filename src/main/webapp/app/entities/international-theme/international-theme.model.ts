import { BaseEntity } from './../../shared';

export class InternationalTheme implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public language?: BaseEntity,
    ) {
    }
}
