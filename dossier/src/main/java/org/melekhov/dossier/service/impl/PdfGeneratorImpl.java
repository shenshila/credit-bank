package org.melekhov.dossier.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.melekhov.dossier.service.PdfGenerator;
import org.melekhov.shareddto.dto.EmailMessageDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Slf4j
public class PdfGeneratorImpl implements PdfGenerator {

    @Override
    public byte[] generateStatementPdf(EmailMessageDto msg) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("document.pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Здраствуйте, ниже представлена информация о вашем кредите," +
                    "основанном на заявке:" + msg.getStatementId(), font);

            document.add(chunk);
            document.close();

//            PdfWriter writer = new PdfWriter();
//
//            PdfDocument pdfDoc = new PdfDocument(writer);
//            Document document = new Document(pdfDoc);
//
//            document.add(new Paragraph("Loan Statement"));
//            document.add(new Paragraph("Client Name: " + clientName));
//            document.add(new Paragraph("Loan Details: " + loanDetails));
//
//            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

}
