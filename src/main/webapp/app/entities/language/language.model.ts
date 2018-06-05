import { BaseEntity } from './../../shared';

export class Language implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
    ) {
    }
}
