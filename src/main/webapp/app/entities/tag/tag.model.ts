import { BaseEntity } from './../../shared';

export class Tag implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public articles?: BaseEntity[],
        public internationalTags?: BaseEntity[],
    ) {
    }
}
