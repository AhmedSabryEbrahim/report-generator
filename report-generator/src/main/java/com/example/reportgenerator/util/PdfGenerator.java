package com.example.reportgenerator.util;

import java.io.IOException;
import com.lowagie.text.Document;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

public class PdfGenerator {
    public void generate(HttpServletResponse response,
                         String title,
                         String result) throws DocumentException, IOException {
        Document document = new com.lowagie.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTiltle.setSize(20);
        Paragraph paragraph1 = new Paragraph(title, fontTiltle);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph1);
        document.add(new Paragraph(result));
        document.close();
    }
}