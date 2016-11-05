package org.pdfbox.showcase;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.junit.Before;
import org.junit.Test;
import org.omidbiz.PersianCharacterUnifier;

public class PdfboxShowcase
{

    static char[] persian_characters = { 'ا', 'آ', 'ب', 'پ', 'ت', 'ض', 'ص', 'ث', 'ق', 'ف', 'غ', 'ع', 'ه', 'خ', 'ح', 'ج', 'چ', 'ش',
        'س', 'ی', 'ل', 'ت', 'ن', 'م', 'ک', 'گ', 'ظ', 'ط', 'ز', 'ر', 'ذ', 'د', 'پ', 'و' };

    static String[] persian_digis = { "۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹" };

    public static final String USER_HOME = System.getProperty("user.home");

    public static final String OUTPUT_FILE = USER_HOME + File.separator + "output_%s.pdf";

    PDDocument document;
    PDFont fontWithOnlyPersianCharacters;
    PDFont fontWithBothPersianAndEnglishCharacter;
    PDPage page;

    @Before
    public void setUp() throws IOException
    {
        document = new PDDocument();
        fontWithOnlyPersianCharacters = PDType0Font.load(document, PdfboxShowcase.class.getResourceAsStream("/BYekan.ttf"));
        fontWithBothPersianAndEnglishCharacter = PDType0Font.load(document, PdfboxShowcase.class.getResourceAsStream("/tahoma.ttf"));
        page = new PDPage(PDRectangle.A4);
        document.addPage(page);
    }

    /**
     * this method will produce invalid persian text ‫ س‌ل‌ا‌م <br />
     * result is سلام <br/>
     * for correct sample visit {@link ITextShowcase#testRtlText()}
     * 
     * @throws IOException
     * 
     */
    @Test
    public void testRtlText() throws IOException
    {

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(fontWithOnlyPersianCharacters, 12);
        contentStream.newLineAtOffset(50, 500);
        contentStream.showText("سلام");
        contentStream.endText();
        contentStream.close();
        document.save(new File(String.format(OUTPUT_FILE, RandomStringUtils.randomAlphabetic(5))));
        document.close();
    }

    /**
     * if you use a font without english character you will get exception
     * IllegalArgumentException: No glyph for U+0070 in font BYekan unlike iText <br/>
     * result : generates total crap
     * 
     * @throws IOException
     *             for correct sample visit {@link ITextShowcase#testMixText()}
     */
    @Test
    public void testMixText() throws IOException
    {
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(fontWithBothPersianAndEnglishCharacter, 12);
        // uncomment below line to get exception
        // contentStream.setFont(fontWithOnlyPersianCharacters, 12);
        contentStream.newLineAtOffset(50, 500);
        contentStream.showText("بیشتر باید روی پروژه pdfbox که متعلق به Apache است ، کار شود");
        contentStream.endText();
        contentStream.close();
        document.save(new File(String.format(OUTPUT_FILE, RandomStringUtils.randomAlphabetic(5))));
        document.close();
    }

    /**
     * java.lang.IllegalArgumentException: No glyph for U+06F0 in font Tahoma
     * @throws IOException
     */
    @Test(expected=IllegalArgumentException.class)    
    public void testTextWithNumber() throws IOException
    {
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(fontWithBothPersianAndEnglishCharacter, 12);
        // contentStream.setFont(fontWithOnlyPersianCharacters, 12);
        contentStream.newLineAtOffset(50, 500);
        contentStream.showText("سعی در ایجاد ۵۰ فایل پی دی اف");
//        contentStream.showText("سعی در ایجاد 50 فایل پی دی اف");
        //50 is differnet from ۵۰
        contentStream.endText();
        contentStream.close();
        document.save(new File(String.format(OUTPUT_FILE, RandomStringUtils.randomAlphabetic(5))));
        document.close();
    }

    @Test
    public void escapeUnicode()
    {
        for (int i = 0; i < persian_characters.length; i++)
        {
            String escapeUnicode = PersianCharacterUnifier.getInstance().escapeUnicode(persian_characters[i]);
            System.out.println(escapeUnicode);
        }
    }

}
