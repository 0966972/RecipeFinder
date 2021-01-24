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

    constructor(private http: HttpClient) {
    }

    public warnUser(body: WarningRequest): Observable<Object> {
        const headers = new HttpHeaders({
            authorization: 'Basic ' + sessionStorage.getItem('token')
        });
        return this.http.post<Object>(this.warningUrl, body, {headers: headers});
    }

    public getWarningsForCurrentUser(): Observable<WarningResponse[]> {
        const headers = new HttpHeaders({
            authorization: 'Basic ' + sessionStorage.getItem('token')
        });
        const url = this.warningUrl + 'currentUser'
        return this.http.get<WarningResponse[]>(url, {headers: headers});
    }

    public getWarnings(): Observable<WarningResponse[]> {
        const headers = new HttpHeaders({
            authorization: 'Basic ' + sessionStorage.getItem('token')
        });
        return this.http.get<WarningResponse[]>(this.warningUrl, {headers: headers});
    }

}
