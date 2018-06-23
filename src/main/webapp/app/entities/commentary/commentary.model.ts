import { BaseEntity, User } from './../../shared';

export class Commentary implements BaseEntity {
    constructor(
        public id?: number,
        public content?: string,
        public author?: User,
        public article?: BaseEntity,
    ) {
    }
}
