import { IsbnDto } from './../dto/isbn-dto';
import { IsbnValidateRequest } from './../dto/isbn-validate-request';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
  })
export class IsbnValidateService {
    constructor(private http:HttpClient) {
    }

    validateIsbns(isbnList:Array<IsbnDto>) {
        let url = "http://localhost:8080/validate/all";
        let body = new IsbnValidateRequest(isbnList);
        let headersOptions = new HttpHeaders({'Content-Type': 'application/json'});
        console.log("calling: " + url);
        console.log(JSON.stringify(body));
        return this.http.post(url, body, {headers:headersOptions});
    }
}