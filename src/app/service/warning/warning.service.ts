import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {WarningRequest} from "../../model/warning-request.model";
import {WarningResponse} from "../../model/warning-response.model";

@Injectable({
    providedIn: 'root'
})
export class WarningService {

    private warningUrl: string = 'http://localhost:8080/warning/';
    private readonly headers: any;

    constructor(private http: HttpClient) {
        this.headers = new HttpHeaders({
            authorization: 'Basic ' + sessionStorage.getItem('token')
        });
    }

    public warnUser(body: WarningRequest): Observable<Object> {
        return this.http.post<Object>(this.warningUrl, body, {headers: this.headers});
    }

    public getWarnings(): Observable<WarningResponse[]> {
        return this.http.get<WarningResponse[]>(this.warningUrl, {headers: this.headers});
    }

}
