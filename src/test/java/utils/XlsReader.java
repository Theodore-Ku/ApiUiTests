package utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class XlsReader {
    private final File xlsxFile;
    private XSSFSheet sheet;
    private XSSFWorkbook book;
    private String sheetName;

    public XlsReader(File xlsxFile) throws IOException {
        this.xlsxFile = xlsxFile;
        try {
            FileInputStream fs = new FileInputStream(xlsxFile);
            book = new XSSFWorkbook(fs);
            sheet = book.getSheetAt(0);
        } catch (IOException e){
            throw new IOException("Wrong file");
        }
    }

    public XlsReader(File xlsxFile, String sheetName) throws IOException {
        this.xlsxFile = xlsxFile;
        try {
            FileInputStream fs = new FileInputStream(xlsxFile);
            book = new XSSFWorkbook(fs);
            sheet = book.getSheet(sheetName);
        } catch (IOException e){
            throw new IOException("Wrong file");
        }
    }

    private String cellToString(XSSFCell cell) throws Exception {
        Object result = null;
        CellType type = cell.getCellType();
        switch (type) {
            case NUMERIC:
                result = cell.getNumericCellValue();
                break;
            case STRING:
                result = cell.getStringCellValue();
                break;
            case FORMULA:
                result = cell.getCellFormula();
                break;
            case BLANK:
                result = "";
                break;
            default:
                throw new Exception("Invalid format");
        }
        return result.toString();
    }

    private int xlsxCountColumn() {
        return sheet.getRow(0).getLastCellNum();
    }

    public int xlsxCountRow() {
        return sheet.getLastRowNum() + 1;
    }

    private String[][] deleteNulls(String[][] oldArray){
        return Arrays.stream(oldArray)
                .filter(row-> Arrays.stream(row).anyMatch(Objects::nonNull))
                .filter(row-> Arrays.stream(row).anyMatch(x-> !Objects.equals(x, "")))
                .toArray(String[][]::new);
    }

    private String[][] deleteEmptyRows(String[][] oldArray) {
        int k = 0;
        int m;
        for (String[] strings : oldArray) {
            m = 0;
            for (int j = 0; j < oldArray[0].length; j++) {
                if (strings[j] == null) { m = m + 1;}
            }
            if (m != oldArray[0].length) { k = k + 1;}
        }
        int n = 0;
        String[][] newArray = new String[k][oldArray[0].length];
        for (String[] strings : oldArray) {
            if (strings[0] != null) {
                System.arraycopy(strings, 0, newArray[n], 0, oldArray[0].length);
                n++;
            }
        }
        return newArray;
    }
    public String[][] getSheetData() throws Exception {
        int numberOfColumn = xlsxCountColumn();
        int numberOfRows = xlsxCountRow();
        String[][] data = new String[numberOfRows - 1][numberOfColumn];
        for (int i = 1; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumn; j++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(j);
                if(cell == null){
                    break;
                }
                String value = cellToString(cell);
                data[i - 1][j] = value;
            }
        }
        data = deleteNulls(data);
        return data;
    }

    public boolean isSheetContainsStringStream(String expected) throws Exception {
        return Arrays.stream(getSheetData())
                .flatMap(Arrays::stream)
                .anyMatch(x->x.contains(expected));
    }

    public boolean isSheetContainsString(String expected) throws Exception {
        String[][] data = getSheetData();
        for (String[] datum : data) {
            for (String s : datum) {
                if(s.contains(expected)){
                    return true;
                }
            }
        }
        return false;
    }

    public String[][] getSheetData(String sheetName) throws Exception {
        int numberOfColumn = xlsxCountColumn();
        int numberOfRows = xlsxCountRow();
        String[][] data = new String[numberOfRows - 1][numberOfColumn];
        for (int i = 1; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumn; j++) {
                XSSFRow row = book.getSheet(sheetName).getRow(i);
                XSSFCell cell = row.getCell(j);
                String value = cellToString(cell);
                data[i - 1][j] = value;
            }
        }
        return data;
    }
}
