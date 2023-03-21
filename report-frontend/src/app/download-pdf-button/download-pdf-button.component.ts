import { Component } from '@angular/core';
import { saveAs } from 'file-saver';
import { jsPDF } from 'jspdf';
import { StockData } from '../model/StockData';
import { StockValue } from '../model/StockValue';
import { StockService } from '../StockService/stock-service.service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-download-pdf-button',
  templateUrl: './download-pdf-button.component.html',
  styleUrls: ['./download-pdf-button.component.css']
})
export class DownloadPdfButtonComponent {

  symbols = ['AAPL', 'GOOG', 'AMZN', 'META', 'TWLO'];
  stocks: StockData = {data: {}};

  dateControl = new FormControl();
  symbolControl1 = new FormControl();
  symbolControl2 = new FormControl();
  startDate = new Date(2022, 0);

  constructor(private stockService: StockService) { }


  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const monthString = month < 10 ? `0${month}` : `${month}`;
    return `${year}-${monthString}`;
  }
  compareTwoStocks() {
    if (this.startDate && this.symbolControl1 && this.symbolControl2) {
      this.stockService.getStockData([this.symbolControl1.value, this.symbolControl2.value], this.formatDate(this.startDate))
        .subscribe(response => {
          this.stocks.data = response;
          console.log(this.stocks);
          console.log(this.stockDataToString(this.stocks));
        });
    }
  }

  downloadPDF() {
    const doc = new jsPDF();
    doc.text(this.stockDataToString(this.stocks), 10, 10);
    const blob = doc.output('blob');
    saveAs(blob, 'Stocks.pdf');
  }

  stockDataToString(stockData: StockData): string {
    let result = '';
    for (const symbol in stockData.data) {
      const stockValue = stockData.data[symbol];
      result +=  "{"+ `Symbol: ${symbol}\n` + this.stockValueToString(stockValue)+  "}";
      result += `\n\n`;
    }
    return result;
  }

  stockValueToString(stockValue: StockValue): string {
    return `Date: ${stockValue.date}\n` +
      `Open: ${stockValue.open}\n` +
      `High: ${stockValue.high}\n` +
      `Low: ${stockValue.low}\n` +
      `Close: ${stockValue.close}\n` +
      `Adjusted Close: ${stockValue.adjustedClose}\n` +
      `Volume: ${stockValue.volume}\n` +
      `Dividend Amount: ${stockValue.dividendAmount}\n`;
  }
}
