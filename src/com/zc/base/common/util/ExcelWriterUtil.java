package com.zc.base.common.util;

import com.zc.base.common.util.execl.ExcelComment;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;


public class ExcelWriterUtil {
    private HSSFWorkbook wb;
    private static Logger log = LoggerFactory.getLogger(ExcelWriterUtil.class);


    private Map<String, SheetWriter> sheetMap = new HashMap();

    private List<SheetWriter> sheetList = new ArrayList(3);


    private SheetWriter curSheetWriter;


    private Integer dataEndRowIndex;


    public ExcelWriterUtil() {
        this("sheet1");
    }


    public ExcelWriterUtil(String sheetName) {
        if (StringUtils.isEmpty(sheetName)) {
            sheetName = "sheet1";
        }
        this.wb = new HSSFWorkbook();
        this.curSheetWriter = new SheetWriter(sheetName, this.wb.createSheet(sheetName));
    }


    public ExcelWriterUtil(InputStream inputStream)
            throws IOException {
        this(new HSSFWorkbook(inputStream));
    }


    public ExcelWriterUtil(HSSFWorkbook workbook) {
        this.wb = workbook;
        for (int i = 0; i < this.wb.getNumberOfSheets(); i++) {
            HSSFSheet sheet = this.wb.getSheetAt(i);
            SheetWriter sheetWriter = new SheetWriter(sheet.getSheetName(), sheet);
            if (i == 0) {
                this.curSheetWriter = sheetWriter;
            }
            this.sheetList.add(sheetWriter);
            this.sheetMap.put(sheetWriter.sheetName, sheetWriter);
        }
    }


    public void setHiddenColumn(int columnIndex) {
        this.curSheetWriter.sheet.setColumnHidden(columnIndex, true);
    }


    public void setHiddenRow(int rowIndex) {
        this.curSheetWriter.sheet.getRow(rowIndex).setZeroHeight(true);
    }


    public void createNewSheet() {
        String sheetName = "sheet" + (this.sheetList.size() + 1);
        createNewSheet(sheetName);
    }


    public void createNewSheet(String sheetName) {
        if (this.sheetMap.get(sheetName) != null) {
            sheetName = sheetName + "_" + System.currentTimeMillis();
        }
        this.curSheetWriter = new SheetWriter(sheetName, this.wb.createSheet(sheetName));
    }


    public void changeSheet(int idx) {
        this.curSheetWriter = ((SheetWriter) this.sheetList.get(idx));
    }


    public void changeSheet(String sheetName) {
        SheetWriter sheetWriter = (SheetWriter) this.sheetMap.get(sheetName);
        if (sheetWriter == null) {
            throw new IllegalStateException(" can not find sheet " + sheetName);
        }
        this.curSheetWriter = sheetWriter;
    }


    public int getNumberOfSheets() {
        return this.wb.getNumberOfSheets();
    }


    public String getSheetName() {
        return this.curSheetWriter.sheetName;
    }


    public void setSheetName(String sheetName) {
        sheetName = sheetName + this.sheetList.size();
        if (this.sheetMap.get(sheetName) != null) {
            sheetName = sheetName + "_" + System.currentTimeMillis();
        }
        int sheetIx = this.curSheetWriter.getSheetIx();
        this.wb.setSheetName(sheetIx, sheetName);
    }


    public void write(int row, int column, Object value) {
        write(row, column, value, null);
    }


    public void write(int row, int column, Object value, HSSFCellStyle style) {
        this.curSheetWriter.writeCell(row, column, value, style);
    }


    public void writerMergeCell(int rowFrom, int colFrom, int rowTo, int colTo, Object value, HSSFCellStyle style) {
        this.curSheetWriter.writerMergeCell(rowFrom, colFrom, rowTo, colTo, value, style);
    }

    public void writeCellFormula(int rowPos, int columnPos, String formula, HSSFCellStyle style) {
        this.curSheetWriter.writeCellFormula(rowPos, columnPos, formula, style);
    }

    public void setColumnWidth(int column, int width) {
        this.curSheetWriter.setColumnWidth(column, width);
    }

    public void setColumnAutoWidth(int column) {
        this.curSheetWriter.setColumnAutoWidth(column);
    }


    public void setRowHeight(short row, short height) {
        this.curSheetWriter.setRowHeight(row, height);
    }

    public void setDisplayGridlines(boolean bool) {
        this.curSheetWriter.setDisplayGridlines(bool);
    }


    public void protectSheet(String password) {
        this.curSheetWriter.protectSheet(password);
    }


    public void removeProtect() {
        protectSheet(null);
    }

    public HSSFCellStyle newCellStyle() {
        return this.wb.createCellStyle();
    }

    public HSSFPalette getPalette() {
        return this.wb.getCustomPalette();
    }


    public HSSFFont newFont() {
        return this.wb.createFont();
    }

    public HSSFDataFormat newDataFormat() {
        return this.wb.createDataFormat();
    }

    public void writeExcel(OutputStream outputStream) {
        try {
            this.wb.write(outputStream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


    public void adjustRowHeight() {
        this.curSheetWriter.adjustRowHeight();
    }


    public void autoSizeColumn(int columns) {
        this.curSheetWriter.autoSizeColumn(columns);
    }

    public short createDataFormat(String format) {
        return this.wb.createDataFormat().getFormat(format);
    }


    public void createSelect(int firstRow, int lastRow, int firstCol, int lastCol, String[] values) {
        this.curSheetWriter.createSelect(firstRow, lastRow, firstCol, lastCol, values);
    }


    public void addComment(int rowIndex, int column, String value) {
        this.curSheetWriter.addComment(rowIndex, column, value);
    }


    public void addComment(ExcelComment comment) {
        addComment(comment.getRow(), comment.getColumn(), comment.getValue());


        if (comment.getCellForegroundColor() != null) {
            HSSFCell cell = this.curSheetWriter.getCell(comment.getRow(), comment.getColumn());

            HSSFCellStyle style = this.wb.createCellStyle();

            style.setBorderTop((short) 1);
            style.setBorderBottom((short) 1);
            style.setBorderLeft((short) 1);
            style.setBorderRight((short) 1);

            style.setFillPattern((short) 1);

            style.setFillForegroundColor(comment.getCellForegroundColor().getIndex());

            cell.setCellStyle(style);
        }
    }


    public void removeRow(int rowIndex) {
        this.curSheetWriter.removeRow(rowIndex);
    }


    public void removeCell(int rowIndex, int cellnum) {
        this.curSheetWriter.removeCell(rowIndex, cellnum);
    }


    public void setDataEndRowIndex(Integer dataEndRowIndex) {
        this.dataEndRowIndex = dataEndRowIndex;
    }


    public Integer getDataEndRowIndex() {
        return Integer.valueOf(this.dataEndRowIndex == null ? 0 : this.dataEndRowIndex.intValue());
    }


    private class SheetWriter {
        String sheetName;


        private int totalRow;


        private HSSFSheet sheet;


        private int sheetIx;


        public SheetWriter(String sheetName, HSSFSheet sheet) {
            this.sheetName = sheetName;
            this.sheet = sheet;
            ExcelWriterUtil.this.sheetList.add(this);
            ExcelWriterUtil.this.sheetMap.put(sheetName, this);
            this.sheetIx = ExcelWriterUtil.this.sheetList.indexOf(this);
        }

        private void createRowIfNecessary(int writeRow) {
            while (this.totalRow <= writeRow) {
                this.sheet.createRow(this.totalRow);
                this.totalRow += 1;
            }
        }

        public void writeCell(int row, int column, Object value, HSSFCellStyle style) {
            if (value == null)
                return;
            createRowIfNecessary(row);

            HSSFRow workRow = this.sheet.getRow(row);
            HSSFCell cell = workRow.createCell(column);
            if (style != null) {
                cell.setCellStyle(style);
            }
            writeObjectValue(cell, value);
        }


        public void writerMergeCell(int rowFrom, int colFrom, int rowTo, int colTo, Object value, HSSFCellStyle style) {
            createRowIfNecessary(rowTo);
            Region region = new Region(rowFrom, (short) colFrom, rowTo, (short) colTo);
            this.sheet.addMergedRegion(region);

            for (int i = region.getRowFrom(); i <= region.getRowTo(); i++) {
                HSSFRow row = this.sheet.getRow(i);
                for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
                    HSSFCell cell = row.createCell((short) j);
                    if (style != null) {
                        cell.setCellStyle(style);
                    }
                }
            }

            HSSFRow workRow = this.sheet.getRow(rowFrom);
            HSSFCell cell = workRow.getCell((short) colFrom);

            writeObjectValue(cell, value);
        }


        private void writeCellFormula(int rowPos, int columnPos, String formula, HSSFCellStyle style) {
            createRowIfNecessary(rowPos);
            HSSFRow workRow = this.sheet.getRow(rowPos);
            HSSFCell cell = workRow.createCell((short) columnPos);
            if (style != null)
                cell.setCellStyle(style);
            cell.setCellFormula(formula);
        }

        private void writeObjectValue(HSSFCell cell, Object value) {
            if (value != null) {
                if (String.class.isInstance(value)) {
                    cell.setCellValue(new HSSFRichTextString(((String) value).trim()));
                } else if ((Double.class.isInstance(value)) || (Integer.class.isInstance(value)) ||
                        (Long.class.isInstance(value)) || (Float.class.isInstance(value))) {
                    cell.setCellValue(Double.parseDouble(String.valueOf(value)));
                } else if (Date.class.isInstance(value)) {
                    cell.setCellValue((Date) value);
                } else if (Calendar.class.isInstance(value)) {
                    cell.setCellValue((Calendar) value);
                } else {
                    cell.setCellValue(new HSSFRichTextString(String.valueOf(value).trim()));
                }
            }
        }

        public void setColumnWidth(int column, int width) {
            this.sheet.setColumnWidth(column, width);
        }

        public void setDisplayGridlines(boolean bool) {
            this.sheet.setDisplayGridlines(bool);
        }

        public void setColumnAutoWidth(int column) {
            this.sheet.autoSizeColumn(column);
        }


        public void setRowHeight(short row, short height) {
            createRowIfNecessary(row);
            HSSFRow workRow = this.sheet.getRow(row);
            workRow.setHeightInPoints(height);
        }


        public void adjustRowHeight() {
            float defaultRowGapInPoint = 4.0F;

            List regions = new ArrayList();
            for (int i = 0; i < this.sheet.getNumMergedRegions(); i++) {
                regions.add(this.sheet.getMergedRegionAt(i));
            }
            int rows = this.sheet.getPhysicalNumberOfRows();
            for (int r = 0; r < rows; r++) {
                HSSFRow row = this.sheet.getRow(r);
                int cells = row.getPhysicalNumberOfCells();
                for (short c = 0; c < cells; c = (short) (c + 1)) {
                    HSSFCell cell = row.getCell(c);

                    if ((cell != null) && (cell.getCellType() == 1)) {
                        boolean isBelongToRegion = false;
                        for (int i = 0; i < regions.size(); i++) {
                            Region region = (Region) regions.get(i);
                            if (region.contains(r, c)) {
                                isBelongToRegion = true;
                                int rowFrom = region.getRowFrom();
                                int rowTo = region.getRowTo();
                                int colFrom = region.getColumnFrom();
                                int colTo = region.getColumnTo();
                                int regionWidths = 0;
                                for (int ii = colFrom; ii <= colTo; ii++) {
                                    regionWidths += this.sheet.getColumnWidth(ii);
                                }
                                long stringWidths = 0L;
                                try {
                                    stringWidths = cell.getRichStringCellValue().toString().getBytes("utf-8").length * 256;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                long aRows = stringWidths / regionWidths + 1L + 1L;

                                HSSFFont font = cell.getCellStyle().getFont(this.sheet.getWorkbook());
                                float rowHeightInPoint = font.getFontHeightInPoints() + defaultRowGapInPoint;
                                float height = rowHeightInPoint * (float) aRows / (rowTo - rowFrom + 1);
                                for (int jj = rowFrom; jj <= rowTo; jj++) {
                                    HSSFRow RegionRow = this.sheet.getRow(jj);
                                    if (RegionRow.getHeightInPoints() < height) {
                                        RegionRow.setHeightInPoints(height);
                                    }
                                }
                                break;
                            }
                        }
                        if (!isBelongToRegion) {
                            long stringWidths = cell.getRichStringCellValue().toString().getBytes().length * 256;

                            long colWidth = this.sheet.getColumnWidth(c);
                            long aRows = stringWidths / colWidth + 1L;

                            HSSFFont font = cell.getCellStyle().getFont(this.sheet.getWorkbook());
                            float rowHeightInPoint = font.getFontHeightInPoints() + defaultRowGapInPoint;
                            if (row.getHeightInPoints() < (float) aRows * rowHeightInPoint) {
                                row.setHeightInPoints((float) aRows * rowHeightInPoint);
                            }
                        }
                    }
                }
            }
        }


        public void autoSizeColumn(int column) {
            for (int i = 0; i < column; i++) {
                this.sheet.autoSizeColumn((short) i);
            }
        }


        public void protectSheet(String password) {
            this.sheet.protectSheet(password);
        }


        public int getSheetIx() {
            return this.sheetIx;
        }


        public void createSelect(int firstRow, int lastRow, int firstCol, int lastCol, String[] values) {
            DataValidationHelper helper = this.sheet.getDataValidationHelper();

            DataValidationConstraint constraint = helper.createExplicitListConstraint(values);
            constraint.setExplicitListValues(values);

            CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
            DataValidation dataValidation = helper.createValidation(constraint, regions);

            this.sheet.addValidationData(dataValidation);
        }


        public void addComment(int rowIndex, int column, String value) {
            HSSFPatriarch p = this.sheet.createDrawingPatriarch();

            HSSFComment comment =
                    p.createComment(new HSSFClientAnchor(column, column, column, column, (short) 3, 3, (short) 5, 6));

            comment.setString(new HSSFRichTextString(value));

            comment.setRow(rowIndex);
            comment.setColumn(column);

            HSSFRow row = this.sheet.getRow(rowIndex);
            HSSFCell cell = row.getCell(column);
            cell.setCellComment(comment);
        }


        public HSSFCell getCell(int row, int column) {
            return this.sheet.getRow(row).getCell(column);
        }


        public void removeRow(int rowIndex) {
            HSSFRow row = this.sheet.getRow(rowIndex);
            if (row != null) {
                this.sheet.removeRow(row);
            }
        }


        public void removeCell(int rowIndex, int cellnum) {
            HSSFRow row = this.sheet.getRow(rowIndex);
            if (row != null) {
                Cell cell = row.getCell(cellnum);
                if (cell != null) {
                    row.removeCell(cell);
                }
            }
        }
    }
}
