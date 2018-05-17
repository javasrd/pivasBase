package com.zc.base.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class ExcelExporter {
    private Log logger = LogFactory.getLog(ExcelExporter.class);

    protected String title = "";


    protected String description;


    protected List<String> headList;


    protected List<List<Object>> rowDataList;


    protected List<String> dataTypeList;


    protected String excelType;


    protected String sheetName;


    protected String crossHeadText = "";


    protected Integer startRow = Integer.valueOf(0);


    protected Integer startCol = Integer.valueOf(0);


    protected Integer rowNumber;


    protected Integer defaultColWidth = Integer.valueOf(10);


    protected String dataCellType = "String";


    protected Short dataCellAlign = Short.valueOf((short) 2);


    protected Short titleRowHeight = Short.valueOf((short) 50);


    protected Short titleFontHeight = Short.valueOf((short) 22);


    protected Short descRowHeight = Short.valueOf((short) 15);


    protected Short descFontHeight = Short.valueOf((short) 10);


    protected Short dataFontHeight = Short.valueOf((short) 10);


    protected Short headFontHeight = Short.valueOf((short) 10);


    protected Short colheadRowHeigh;


    protected Short headForegroundColor = Short.valueOf(IndexedColors.GREY_40_PERCENT.getIndex());


    protected boolean isDataFstHead = false;


    protected Short dataRowHeigh;


    protected Workbook destWorkbook;


    protected CellStyle titleStyle;


    protected CellStyle colHeadStyle;


    protected CellStyle rowHeadStyle;


    protected CellStyle descriptionStyle;


    protected CellStyle stringDataCellStyle;


    protected CellStyle integerDataCellStyle;


    protected CellStyle floatDataCellStyle;


    public void export(OutputStream out)
            throws IOException {
        Workbook workbook = getWorkbook();
        workbook.write(out);
    }


    public Workbook getWorkbook() {
        createBlankWorkbook();

        if (this.destWorkbook != null) {
            createCellStyles();
            fillTitle();
            fillDiscription();
            if (this.headList != null) {
                fillColHead();
                if (this.rowDataList != null) {
                    fillData();
                    this.logger.info("excel ready " + this.rowDataList.size() + "records!");
                }
            }
        }
        return this.destWorkbook;
    }


    protected void createBlankWorkbook() {
        if ("xlsx".equals(this.excelType)) {
            this.destWorkbook = new XSSFWorkbook();

        } else if ("xls".equals(this.excelType)) {
            this.destWorkbook = new HSSFWorkbook();

        } else {
            this.logger.debug("unkonw excel type : \"" + this.excelType + "\" will return null!");
            this.destWorkbook = null;
            return;
        }


        this.sheetName = (this.sheetName == null ? "sheet1" : this.sheetName);
        Sheet sheet = this.destWorkbook.createSheet(this.sheetName);

        sheet.setDefaultColumnWidth(this.defaultColWidth.intValue());


        this.rowNumber = this.startRow;
    }


    protected void createCellStyles() {
        this.titleStyle = this.destWorkbook.createCellStyle();

        this.titleStyle.setAlignment((short) 2);

        this.titleStyle.setVerticalAlignment((short) 1);

        Font titleFont = this.destWorkbook.createFont();

        titleFont.setFontHeightInPoints(this.titleFontHeight.shortValue());

        titleFont.setBoldweight((short) 700);
        this.titleStyle.setFont(titleFont);


        this.descriptionStyle = this.destWorkbook.createCellStyle();

        this.descriptionStyle.setAlignment((short) 1);

        this.descriptionStyle.setVerticalAlignment((short) 1);

        Font descriptionfont = this.destWorkbook.createFont();

        descriptionfont.setFontHeightInPoints(this.descFontHeight.shortValue());
        this.descriptionStyle.setFont(descriptionfont);


        this.colHeadStyle = this.destWorkbook.createCellStyle();

        this.colHeadStyle.setAlignment((short) 2);

        this.colHeadStyle.setVerticalAlignment((short) 1);

        this.colHeadStyle.setWrapText(true);

        this.colHeadStyle.setFillForegroundColor(this.headForegroundColor.shortValue());
        this.colHeadStyle.setFillPattern((short) 1);


        Font colHeadfont = this.destWorkbook.createFont();

        colHeadfont.setFontHeightInPoints(this.headFontHeight.shortValue());

        colHeadfont.setBoldweight((short) 700);
        this.colHeadStyle.setFont(colHeadfont);


        this.colHeadStyle.setBorderBottom((short) 1);
        this.colHeadStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        this.colHeadStyle.setBorderLeft((short) 1);
        this.colHeadStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        this.colHeadStyle.setBorderRight((short) 1);
        this.colHeadStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        this.colHeadStyle.setBorderTop((short) 1);
        this.colHeadStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());


        this.rowHeadStyle = this.destWorkbook.createCellStyle();
        this.rowHeadStyle.setAlignment((short) 2);

        this.rowHeadStyle.setFillForegroundColor(this.headForegroundColor.shortValue());
        this.rowHeadStyle.setFillPattern((short) 1);

        Font rowHeadfont = this.destWorkbook.createFont();

        rowHeadfont.setFontHeightInPoints(this.headFontHeight.shortValue());

        rowHeadfont.setBoldweight((short) 700);
        this.rowHeadStyle.setFont(rowHeadfont);

        this.rowHeadStyle.setVerticalAlignment((short) 1);

        this.rowHeadStyle.setWrapText(true);

        this.rowHeadStyle.setBorderBottom((short) 1);
        this.rowHeadStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        this.rowHeadStyle.setBorderLeft((short) 1);
        this.rowHeadStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        this.rowHeadStyle.setBorderRight((short) 1);
        this.rowHeadStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        this.rowHeadStyle.setBorderTop((short) 1);
        this.rowHeadStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());


        this.stringDataCellStyle = this.destWorkbook.createCellStyle();
        Font stringDatafont = this.destWorkbook.createFont();

        stringDatafont.setFontHeightInPoints(this.dataFontHeight.shortValue());
        this.stringDataCellStyle.setFont(stringDatafont);

        this.stringDataCellStyle.setVerticalAlignment((short) 1);

        this.stringDataCellStyle.setWrapText(true);

        this.stringDataCellStyle.setBorderBottom((short) 1);
        this.stringDataCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        this.stringDataCellStyle.setBorderLeft((short) 1);
        this.stringDataCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        this.stringDataCellStyle.setBorderRight((short) 1);
        this.stringDataCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        this.stringDataCellStyle.setBorderTop((short) 1);
        this.stringDataCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());


        this.integerDataCellStyle = this.destWorkbook.createCellStyle();
        Font integerDatafont = this.destWorkbook.createFont();

        integerDatafont.setFontHeightInPoints(this.dataFontHeight.shortValue());
        this.integerDataCellStyle.setFont(integerDatafont);

        this.integerDataCellStyle.setVerticalAlignment((short) 1);

        this.integerDataCellStyle.setWrapText(true);

        this.integerDataCellStyle.setBorderBottom((short) 1);
        this.integerDataCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        this.integerDataCellStyle.setBorderLeft((short) 1);
        this.integerDataCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        this.integerDataCellStyle.setBorderRight((short) 1);
        this.integerDataCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        this.integerDataCellStyle.setBorderTop((short) 1);
        this.integerDataCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        DataFormat integerFormat = this.destWorkbook.createDataFormat();
        this.integerDataCellStyle.setDataFormat(integerFormat.getFormat("#,##0"));


        this.floatDataCellStyle = this.destWorkbook.createCellStyle();
        Font floatDatafont = this.destWorkbook.createFont();

        floatDatafont.setFontHeightInPoints(this.dataFontHeight.shortValue());
        this.floatDataCellStyle.setFont(floatDatafont);

        this.floatDataCellStyle.setVerticalAlignment((short) 1);

        this.floatDataCellStyle.setWrapText(true);

        this.floatDataCellStyle.setBorderBottom((short) 1);
        this.floatDataCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        this.floatDataCellStyle.setBorderLeft((short) 1);
        this.floatDataCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        this.floatDataCellStyle.setBorderRight((short) 1);
        this.floatDataCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        this.floatDataCellStyle.setBorderTop((short) 1);
        this.floatDataCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        DataFormat floatFormat = this.destWorkbook.createDataFormat();
        this.floatDataCellStyle.setDataFormat(floatFormat.getFormat("#,##0.00"));
    }


    protected void fillTitle() {
        Sheet sheet = this.destWorkbook.getSheet(this.sheetName);
        Row row = sheet.createRow(this.rowNumber.intValue());
        row.setHeightInPoints(this.titleRowHeight.shortValue());
        Cell titleCell = row.createCell(this.startCol.intValue(), 1);
        titleCell.setCellValue(this.title);
        titleCell.setCellStyle(this.titleStyle);


        int mergedColCount = 8;


        if ((this.rowDataList != null) && (this.rowDataList.size() > 0)) {
            mergedColCount = ((List) this.rowDataList.get(0)).size();
        } else if ((this.headList != null) && (this.headList.size() > 0)) {
            mergedColCount = this.headList.size();
        }

        sheet.addMergedRegion(new CellRangeAddress(this.rowNumber.intValue(), this.rowNumber.intValue(), this.startCol.intValue(), this.startCol.intValue() + mergedColCount - 1));

        this.rowNumber = Integer.valueOf(this.rowNumber.intValue() + 1);
    }


    protected void fillDiscription() {
        if ((this.description != null) && (this.description.trim().length() > 0)) {
            Sheet sheet = this.destWorkbook.getSheet(this.sheetName);
            Row row = sheet.createRow(this.rowNumber.intValue());
            row.setHeightInPoints(this.descRowHeight.shortValue());
            Cell descriptionCell = row.createCell(this.startCol.intValue(), 1);
            descriptionCell.setCellValue(this.description);
            descriptionCell.setCellStyle(this.descriptionStyle);


            int mergedColCount = 8;


            if ((this.rowDataList != null) && (this.rowDataList.size() > 0)) {
                mergedColCount = ((List) this.rowDataList.get(0)).size();
            } else if ((this.headList != null) && (this.headList.size() > 0)) {
                mergedColCount = this.headList.size();
            }
            sheet.addMergedRegion(new CellRangeAddress(this.rowNumber.intValue(), this.rowNumber.intValue(), this.startCol.intValue(), this.startCol.intValue() + mergedColCount - 1));

            this.rowNumber = Integer.valueOf(this.rowNumber.intValue() + 1);
        }
    }


    protected void fillColHead() {
        Sheet sheet = this.destWorkbook.getSheet(this.sheetName);
        Row row = sheet.createRow(this.rowNumber.intValue());

        if (this.colheadRowHeigh != null) {
            row.setHeightInPoints(this.colheadRowHeigh.shortValue());
        }

        int colHeadStartColNum = this.startCol.intValue();

        if ((this.isDataFstHead) && (this.rowDataList != null) && (this.rowDataList.size() > 0)) {

            if (this.crossHeadText != null) {
                Cell colHeadCell = row.createCell(colHeadStartColNum, 1);
                colHeadCell.setCellValue(this.crossHeadText);
                colHeadCell.setCellStyle(this.colHeadStyle);
            }

            colHeadStartColNum++;
        }

        for (int i = 0; i < this.headList.size(); i++) {
            Cell colHeadCell = row.createCell(colHeadStartColNum + i, 1);
            colHeadCell.setCellValue((String) this.headList.get(i));
            colHeadCell.setCellStyle(this.colHeadStyle);
        }


        this.rowNumber = Integer.valueOf(this.rowNumber.intValue() + 1);
    }


    protected void fillData() {
        Sheet sheet = this.destWorkbook.getSheet(this.sheetName);
        for (int i = 0; i < this.rowDataList.size(); i++) {

            Row row = sheet.createRow(this.rowNumber.intValue());

            if (this.dataRowHeigh != null) {
                row.setHeightInPoints(this.dataRowHeigh.shortValue());
            }
            List<Object> rowData = (List) this.rowDataList.get(i);

            for (int j = 0; j < rowData.size(); j++) {

                Cell cell = row.createCell(this.startCol.intValue() + j);
                Object data = rowData.get(j);

                if ((j == 0) && (this.isDataFstHead)) {

                    cell.setCellType(1);

                    cell.setCellStyle(this.rowHeadStyle);

                    cell.setCellValue(data.toString());

                } else {

                    if (this.dataCellType != null) {

                        if ("Integer".equals(this.dataCellType)) {
                            data = Integer.valueOf(Integer.parseInt(data.toString()));

                        } else if ("Float".equals(this.dataCellType)) {
                            data = Double.valueOf(data.toString());

                        } else if ("String".equals(this.dataCellType)) {
                            data = data.toString();
                        }
                    }


                    if ((data instanceof Number)) {


                        cell.setCellType(0);

                        List<Class> integerClassList = new ArrayList();
                        integerClassList.add(Integer.class);
                        integerClassList.add(Long.class);
                        integerClassList.add(Short.class);
                        integerClassList.add(BigInteger.class);


                        if (integerClassList.contains(data.getClass())) {

                            cell.setCellStyle(this.integerDataCellStyle);

                        } else {

                            cell.setCellStyle(this.floatDataCellStyle);
                        }
                        cell.setCellValue(Double.parseDouble(data.toString()));

                    } else {

                        cell.setCellType(0);

                        cell.setCellStyle(this.stringDataCellStyle);

                        cell.setCellValue(data.toString());
                    }


                    if (this.dataCellAlign != null) {
                        cell.getCellStyle().setAlignment(this.dataCellAlign.shortValue());
                    }
                }
            }

            this.rowNumber = Integer.valueOf(this.rowNumber.intValue() + 1);
        }
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getHeadList() {
        return this.headList;
    }

    public void setHeadList(List<String> headList) {
        this.headList = headList;
    }

    public List<List<Object>> getRowDataList() {
        return this.rowDataList;
    }

    public void setRowDataList(List<List<Object>> rowDataList) {
        this.rowDataList = rowDataList;
    }

    public short getHeadForegroundColor() {
        return this.headForegroundColor.shortValue();
    }

    public void setHeadForegroundColor(short headForegroundColor) {
        this.headForegroundColor = Short.valueOf(headForegroundColor);
    }

    public String getExcelType() {
        return this.excelType;
    }

    public void setExcelType(String excelType) {
        this.excelType = excelType;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Workbook getDestWorkbook() {
        return this.destWorkbook;
    }

    public void setDestWorkbook(Workbook destWorkbook) {
        this.destWorkbook = destWorkbook;
    }

    public Integer getStartRow() {
        return this.startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getStartCol() {
        return this.startCol;
    }

    public void setStartCol(Integer startCol) {
        this.startCol = startCol;
    }

    public Integer getDefaultColWidth() {
        return this.defaultColWidth;
    }

    public void setDefaultColWidth(Integer defaultColWidth) {
        this.defaultColWidth = defaultColWidth;
    }

    public short getTitleRowHeight() {
        return this.titleRowHeight.shortValue();
    }

    public void setTitleRowHeight(short titleRowHeight) {
        this.titleRowHeight = Short.valueOf(titleRowHeight);
    }

    public short getTitleFontHeight() {
        return this.titleFontHeight.shortValue();
    }

    public short getColheadRowHeigh() {
        return this.colheadRowHeigh.shortValue();
    }

    public void setColheadRowHeigh(short colheadRowHeigh) {
        this.colheadRowHeigh = Short.valueOf(colheadRowHeigh);
    }

    public void setTitleFontHeight(short titleFontHeight) {
        this.titleFontHeight = Short.valueOf(titleFontHeight);
    }

    public short getHeadFontHeight() {
        return this.headFontHeight.shortValue();
    }

    public void setHeadFontHeight(short headFontHeight) {
        this.headFontHeight = Short.valueOf(headFontHeight);
    }

    public boolean isDataFstHead() {
        return this.isDataFstHead;
    }

    public void setDataFstHead(boolean isDataFstHead) {
        this.isDataFstHead = isDataFstHead;
    }

    public short getDataRowHeigh() {
        return this.dataRowHeigh.shortValue();
    }

    public void setDataRowHeigh(Short dataRowHeigh) {
        this.dataRowHeigh = dataRowHeigh;
    }

    public List<String> getDataTypeList() {
        return this.dataTypeList;
    }

    public void setDataTypeList(List<String> dataTypeList) {
        this.dataTypeList = dataTypeList;
    }

    public Short getDataFontHeight() {
        return this.dataFontHeight;
    }

    public void setDataFontHeight(Short dataFontHeight) {
        this.dataFontHeight = dataFontHeight;
    }

    public String getCrossHeadText() {
        return this.crossHeadText;
    }

    public void setCrossHeadText(String crossHeadText) {
        this.crossHeadText = crossHeadText;
    }

    public String getDataCellType() {
        return this.dataCellType;
    }

    public void setDataCellType(String dataCellType) {
        this.dataCellType = dataCellType;
    }

    public Short getDataCellAlign() {
        return this.dataCellAlign;
    }

    public void setDataCellAlign(Short dataCellAlign) {
        this.dataCellAlign = dataCellAlign;
    }
}
