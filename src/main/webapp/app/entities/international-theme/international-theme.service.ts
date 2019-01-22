import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { InternationalTheme } from './international-theme.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<InternationalTheme>;

@Injectable()
export class InternationalThemeService {

    private resourceUrl =  SERVER_API_URL + 'api/international-themes';

    constructor(private http: HttpClient) { }

    create(internationalTheme: InternationalTheme): Observable<EntityResponseType> {
        const copy = this.convert(internationalTheme);
        return this.http.post<InternationalTheme>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(internationalTheme: InternationalTheme): Observable<EntityResponseType> {
        const copy = this.convert(internationalTheme);
        return this.http.put<InternationalTheme>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<InternationalTheme>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<InternationalTheme[]>> {
        const options = createRequestOption(req);
        return this.http.get<InternationalTheme[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InternationalTheme[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: InternationalTheme = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<InternationalTheme[]>): HttpResponse<InternationalTheme[]> {
        const jsonResponse: InternationalTheme[] = res.body;
        const body: InternationalTheme[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to InternationalTheme.
     */
    private convertItemFromServer(internationalTheme: InternationalTheme): InternationalTheme {
        const copy: InternationalTheme = Object.assign({}, internationalTheme);
        return copy;
    }

    /**
     * Convert a InternationalTheme to a JSON which can be sent to the server.
     */
    private convert(internationalTheme: InternationalTheme): InternationalTheme {
        const copy: InternationalTheme = Object.assign({}, internationalTheme);
        return copy;
    }
}
