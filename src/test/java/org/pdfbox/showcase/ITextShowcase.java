package org.pdfbox.showcase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.junit.Before;
import org.junit.Test;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.MultiColumnText;
import com.lowagie.text.pdf.PdfWriter;

public class ITextShowcase
{

    public static final String USER_HOME = System.getProperty("user.home");

    public static final String OUTPUT_FILE = USER_HOME + File.separator + "output_%s.pdf";

    Document document;

    @Before
    public void setUp() throws IOException, DocumentException
    {
        document = new Document();
    }

    @Test
    public void testRtlText() throws IOException, DocumentException, URISyntaxException
    {

        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(new File(String.format(OUTPUT_FILE, RandomStringUtils.randomAlphabetic(3)))));
        writer.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

        document.open();
        //
        MultiColumnText mct = new MultiColumnText();
        mct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        mct.setColumnsRightToLeft(true);
        Path path = Paths.get(ITextShowcase.class.getResource("/BYekan.ttf").toURI());
        BaseFont bf = BaseFont.createFont(path.toFile().getAbsolutePath(), BaseFont.IDENTITY_H, true);
        Font font = new Font(bf, 12);
        Paragraph p = new Paragraph("سلام", font);
        mct.addRegularColumns(document.left(), document.right(), 10f, 3);
        mct.addElement(p);
        //
        document.add(mct);
        document.close();
    }

    @Test
    public void testMixText() throws IOException, URISyntaxException, DocumentException
    {
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(new File(String.format(OUTPUT_FILE, RandomStringUtils.randomAlphabetic(3)))));
        writer.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        document.open();
        //
        MultiColumnText mct = new MultiColumnText();
        mct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        mct.setColumnsRightToLeft(true);
        //don't throw exception text won't be shown
        //Path path = Paths.get(ITextShowcase.class.getResource("/BYekan.ttf").toURI());
        Path path = Paths.get(ITextShowcase.class.getResource("/tahoma.ttf").toURI());
        BaseFont bf = BaseFont.createFont(path.toFile().getAbsolutePath(), BaseFont.IDENTITY_H, true);
        Font font = new Font(bf, 12);
        Paragraph p = new Paragraph("بیشتر باید روی پروژه pdfbox که متعلق به Apache است ، کار شود", font);
        mct.addRegularColumns(document.left(), document.right(), 10f, 3);
        mct.addElement(p);
        //
        document.add(mct);
        document.close();
    }

}
