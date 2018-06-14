import { BaseEntity } from './../../shared';

export class ThemeSubscription implements BaseEntity {
    constructor(
        public id?: number,
        public email?: string,
        public subscripitonForAll?: boolean,
        public themes?: BaseEntity[],
    ) {
        this.subscripitonForAll = false;
    }
}
