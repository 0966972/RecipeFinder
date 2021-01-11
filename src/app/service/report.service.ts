import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Report} from "../model/report.model";

@Injectable()
export class ReportService {

  private reportUrl: string = 'http://localhost:8080/report/';
  private readonly headers: any;

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({
      authorization: 'Basic ' + sessionStorage.getItem('token')
    });
  }

  public reportUser(body, headers): Observable<Object> {
    return this.http.post<Object>(this.reportUrl, body, {headers: headers});
  }

  public getReports(): Observable<Report[]> {
    return this.http.get<Report[]>(this.reportUrl, {headers: this.headers});
  }

}
