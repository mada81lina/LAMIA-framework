package framework.util.document;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.*;

/**
 * Created by TironM on 07.09.2015
 */
public class ExcelDocument {
    FileInputStream input_document = null;
    HSSFWorkbook my_xls_workbook = null;
    HSSFSheet my_worksheet;
    String file;

    public ExcelDocument(String file) {
        this.file = file;

        try {
            input_document = new FileInputStream(new File(this.file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert input_document != null;
            my_xls_workbook = new HSSFWorkbook(input_document);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            input_document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert my_xls_workbook != null;

        my_worksheet = my_xls_workbook.getSheetAt(0);
    }

    /**
     * Checks the value of the cell. Will throw exception if it is different.
     *
     * @param cellExpectedValue the expected value of the cell
     * @param rowIndex          zero based row number
     * @param columnIndex       zero based column number
     * @return this.
     */
    public ExcelDocument checkCellValue(String cellExpectedValue, int rowIndex, int columnIndex) {

        try {
            Cell cell = my_worksheet.getRow(rowIndex).getCell(columnIndex);
            String cellActualValue = cell.getStringCellValue();

            if (!cellActualValue.contains(cellExpectedValue)) {
                throw new IllegalStateException(String.format("Cell does not contain expected value. Got \"%s\" " +
                        ". Expected \"%s\"!", cellActualValue, cellExpectedValue));
            }
        } catch (NullPointerException ignored) {
            throw new IllegalStateException(String.format("Cell is null. Expected \"%s\"", cellExpectedValue));
        }
        return this;
    }

    /**
     * check if cellExpectedValue is in a given column
     *
     * @param cellExpectedValue the expected value of the cell
     * @param columnIndex       zero based column number
     * @return this.
     */
    public ExcelDocument checkValueInColumn(String cellExpectedValue, int columnIndex) {
        String allValuesInColumnConcatenated = "";
        try {
            for (int i = 0; i <= my_worksheet.getLastRowNum(); i++) {
                allValuesInColumnConcatenated += my_worksheet.getRow(i).getCell(columnIndex).getStringCellValue();
            }
        } catch (NullPointerException ignored) {
        }
        if (!allValuesInColumnConcatenated.contains(cellExpectedValue)) {
            throw new IllegalStateException(String.format("Column %s does not contain value: %s", columnIndex,
                    cellExpectedValue));
        }
        return this;
    }

    /**
     * Edits the cell at index
     *
     * @param rowIndex    zero based row number
     * @param columnIndex zero based column number
     * @param cellValue   new value of cell
     * @return this.
     */
    public ExcelDocument editCell(int rowIndex, int columnIndex, String cellValue) {
        Cell cell = my_worksheet.getRow(rowIndex).getCell(columnIndex);
        cell.setCellValue(cellValue);

        return this;
    }

    public void saveAndClose() {
        FileOutputStream output_file = null;
        try {
            output_file = new FileOutputStream(new File(this.file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            my_xls_workbook.write(output_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert output_file != null;
            output_file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
