package tests.junit5.ui.pdf_xls;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.XlsReader;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$x;

public class SelenideFileTests {
    @Test
    public void readPdfTest() throws IOException {
        File pdf = new File("src/test/resources/test.pdf");
        PDF pdfReader = new PDF(pdf);
        String pdfText = pdfReader.text;
        Assertions.assertTrue(pdfText.contains("AC-Tester"));
    }

    @Test
    public void readPdfBrowserTest() throws IOException {
        Selenide.open("https://www.pdf995.com/samples/");
        File pdf = $x("//td[@data-sort='pdf.pdf']/a").download();
        PDF pdfReader = new PDF(pdf);
        Assertions.assertEquals("Software 995", pdfReader.author);
        Assertions.assertTrue(pdfReader.text.contains("Please visit us at www.pdf995.com to learn more"));
    }

    @Test
    public void readXlsxTest() throws Exception {
        File xlsx = new File("src/test/resources/local_exel.xlsx");
        XlsReader xlsReader = new XlsReader(xlsx);
        String[][] data = xlsReader.getSheetData();
        String name = data[2][1];
        Assertions.assertEquals("Philip", name);
        Assertions.assertTrue(xlsReader.isSheetContainsStringStream("France"));
    }

    @Test
    public void readXlsxBrowserTest() throws Exception {
        Selenide.open("https://www.wisdomaxis.com/technology/software/data/for-reports/");
        File xlsx = $x("//div[@class='grid_4'][2]/a[1]").download();
        XlsReader xlsReader = new XlsReader(xlsx);
        String[][] data = xlsReader.getSheetData();
        Assertions.assertEquals(data.length, 1007);
    }
}
