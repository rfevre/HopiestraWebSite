import { BaseEntity, User } from './../../shared';

export class Article implements BaseEntity {
    constructor(
        public id?: number,
        public backgroundPictureContentType?: string,
        public backgroundPicture?: any,
        public publicationDate?: any,
        public updateDate?: any,
        public creationDate?: any,
        public deleteDate?: any,
        public author?: User,
        public theme?: BaseEntity,
        public internationalsArticles?: BaseEntity[],
    ) {
    }
}
