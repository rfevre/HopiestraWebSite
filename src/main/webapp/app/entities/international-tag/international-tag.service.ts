import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { InternationalTag } from './international-tag.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<InternationalTag>;

@Injectable()
export class InternationalTagService {

    private resourceUrl =  SERVER_API_URL + 'api/international-tags';

    constructor(private http: HttpClient) { }

    create(internationalTag: InternationalTag): Observable<EntityResponseType> {
        const copy = this.convert(internationalTag);
        return this.http.post<InternationalTag>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(internationalTag: InternationalTag): Observable<EntityResponseType> {
        const copy = this.convert(internationalTag);
        return this.http.put<InternationalTag>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<InternationalTag>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<InternationalTag[]>> {
        const options = createRequestOption(req);
        return this.http.get<InternationalTag[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InternationalTag[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: InternationalTag = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<InternationalTag[]>): HttpResponse<InternationalTag[]> {
        const jsonResponse: InternationalTag[] = res.body;
        const body: InternationalTag[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to InternationalTag.
     */
    private convertItemFromServer(internationalTag: InternationalTag): InternationalTag {
        const copy: InternationalTag = Object.assign({}, internationalTag);
        return copy;
    }

    /**
     * Convert a InternationalTag to a JSON which can be sent to the server.
     */
    private convert(internationalTag: InternationalTag): InternationalTag {
        const copy: InternationalTag = Object.assign({}, internationalTag);
        return copy;
    }
}
