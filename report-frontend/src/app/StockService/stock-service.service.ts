import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  private baseUrl = 'http://localhost:8080/stock';

  constructor(private http: HttpClient) { }

  getStockData(symbols: string[], date: string): Observable<any> {
    const symbolsParam = symbols.join(',');
    const url = `${this.baseUrl}/${symbolsParam}/${date}`;
    console.log(url);
    return this.http.get<any>(url);
  }
}
