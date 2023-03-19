package com.example.todolist.view;

import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SamplePdf extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                     Document doc,
                                     PdfWriter writer,
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws Exception {
       //テキスト
       String currentTime = ( (java.util.Date)model.get( "currentTime" )).toString();
       doc.add( new Paragraph( currentTime )); 

       //表
       Table table = new Table( 1 );
       table.addCell( "currentTime" );
       table.addCell( currentTime );
       doc.add( table );
    }

}
