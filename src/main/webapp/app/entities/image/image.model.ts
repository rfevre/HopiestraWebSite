import { BaseEntity } from './../../shared';

export class Image implements BaseEntity {
    constructor(
        public id?: number,
        public imageBlobContentType?: string,
        public imageBlob?: any,
        public name?: string,
    ) {
    }
}
