import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ReportService {

  private reportUrl : string = 'http://localhost:8080/report/';

  constructor(private http: HttpClient) {

  }

  public reportUser(body, headers): Observable<Object> {
    return this.http.post<Observable<Object>>(this.reportUrl, body, {headers: headers});
  }

}
