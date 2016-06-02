package framework.util.document;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

/**
 * Created by IlieV on 18.09.2015.
 * Helper class used for getting strings out of PDFs.
 */
public class PDFDocument {
    private String pdfText;
    private String pdfPath;

    public PDFDocument(String pdfPath) {
        this.pdfPath = pdfPath;
        getPdfText();
    }

    public void checkText(String textToCheck) {
        if (!pdfText.contains(textToCheck)) {
            throw new IllegalStateException(String.format("Searched text: \"%s\" was not found in PDF at location: %s",
                    textToCheck, pdfPath));
        }
    }

    public void getPdfText() {

        PDFParser parser;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;

        String parsedText;
        String fileName = pdfPath;
        File file = new File(fileName);
        try {
            parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            pdfText = parsedText.replaceAll("[^A-Za-z0-9. ]+", "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cosDoc != null)
                try {
                    cosDoc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (pdDoc != null)
                try {
                    pdDoc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
