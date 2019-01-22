import { BaseEntity, User } from './../../shared';

export class Article implements BaseEntity {
    constructor(
        public id?: number,
        public publicationDate?: any,
        public updateDate?: any,
        public creationDate?: any,
        public deleteDate?: any,
        public adminTitle?: string,
        public author?: User,
        public theme?: BaseEntity,
        public internationalsArticles?: BaseEntity[],
        public tags?: BaseEntity[],
        public backgroundPicture?: BaseEntity,
    ) {
    }
}
