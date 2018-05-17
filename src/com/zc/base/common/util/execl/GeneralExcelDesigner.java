package com.zc.base.common.util.execl;

import com.zc.base.common.bo.ExcelCell;
import com.zc.base.common.util.ExcelWriterUtil;
import com.zc.base.common.util.amcharts.KeyFormat;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class GeneralExcelDesigner {
    private List<?> datas;
    private String title;
    private String[] overviews;
    private GeneralExcelHeader[] headers;
    private GeneralExcelCell[] beanFields;
    private String[] totalFields;
    private String totalType;
    private Integer[] merges;
    private Integer columnLength;
    private boolean totalForRight;
    private boolean totalForBottom;
    private Map<Integer, ExcelCell> mergeCellMap;
    private HSSFCellStyle titleCellStyle;
    private HSSFCellStyle overviewCellStyle;
    private HSSFCellStyle headerCellStyle;
    private HSSFCellStyle contentCellStyle;
    private boolean across = false;


    private String acrossHeaderField;


    private KeyFormat acrossHeaderFormat;


    private String acrossValueField;


    private String acrossUniqueField;


    private Map<String, Map<String, String>> acrossMap;


    private ExcelWriterUtil excelWriter;


    private Integer[] hiddenColumnIndex;


    private Integer[] lockCellColumnIndex;


    private boolean autoSizeColumn = false;


    private String sheetName;


    private String nullCellValue;


    private Integer startRowIndex = Integer.valueOf(0);

    private List<String> dynamicHeaderList;


    public GeneralExcelDesigner() {
        this(new ExcelWriterUtil());
    }

    public GeneralExcelDesigner(ExcelWriterUtil writer) {
        setExcelWriter(writer);
    }

    protected List<?> getDatas() {
        return this.datas;
    }


    public void setDatas(List<?> datas) {
        this.datas = datas;
    }

    protected String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        if (title == null) {
            title = "";
        }
        this.title = title;
    }

    protected String[] getOverviews() {
        return this.overviews;
    }


    public void setOverviews(String... overviews) {
        this.overviews = overviews;
    }


    public GeneralExcelHeader getExcelHeader(int index) {
        return this.headers[index];
    }

    protected GeneralExcelHeader[] getHeaders() {
        return this.headers;
    }


    public void setHeaders(String... headers) {
        if (headers != null) {
            List<GeneralExcelHeader> headerList = new ArrayList();
            String[] arrayOfString;
            int j = (arrayOfString = headers).length;
            for (int i = 0; i < j; i++) {
                String value = arrayOfString[i];

                GeneralExcelHeader header = new GeneralExcelHeader();
                header.setValue(value);
                headerList.add(header);
            }
            this.headers = ((GeneralExcelHeader[]) headerList.toArray(new GeneralExcelHeader[0]));
        }
    }


    public GeneralExcelCell getExcelCell(int beanFieldIndex) {
        return this.beanFields[beanFieldIndex];
    }


    public GeneralExcelCell getExcelCell(String beanField) {
        GeneralExcelCell cell = null;
        for (int i = 0; i < this.beanFields.length; i++) {
            if ((beanField != null) && (beanField.equals(this.beanFields[i].getBeanField()))) {
                cell = this.beanFields[i];
                break;
            }
        }
        return cell;
    }

    protected GeneralExcelCell[] getBeanFields() {
        return this.beanFields;
    }


    public void setBeanFields(String... beanFields) {
        if (beanFields != null) {
            List<GeneralExcelCell> fields = new ArrayList();
            String[] arrayOfString;
            int j = (arrayOfString = beanFields).length;
            for (int i = 0; i < j; i++) {
                String field = arrayOfString[i];

                GeneralExcelCell cell = new GeneralExcelCell();
                cell.setBeanField(field);
                fields.add(cell);
            }
            this.beanFields = ((GeneralExcelCell[]) fields.toArray(new GeneralExcelCell[0]));
        }
    }

    protected String[] getTotalFields() {
        return this.totalFields;
    }


    public void setTotalFields(String[] totalFields) {
        this.totalFields = totalFields;
    }

    protected String getTotalType() {
        return this.totalType;
    }


    public void setTotalType(String totalType) {
        if (totalType == null) {
            totalType = "both";
        }

        this.totalType = totalType;
    }

    protected Integer[] getMerges() {
        return this.merges;
    }


    public void setMerges(Integer... merges) {
        this.merges = merges;
    }


    protected void setColumnLength(Integer columnLength) {
        this.columnLength = columnLength;
    }

    protected Integer getColumnLength() {
        return this.columnLength;
    }


    protected boolean isTotalForRight() {
        if (("right".equals(this.totalType)) || ("both".equals(this.totalType))) {
            this.totalForRight = true;
        } else {
            this.totalForRight = false;
        }
        return this.totalForRight;
    }


    protected boolean isTotalForBottom() {
        if (("bottom".equals(this.totalType)) || ("both".equals(this.totalType))) {
            this.totalForBottom = true;
        } else {
            this.totalForBottom = false;
        }
        return this.totalForBottom;
    }


    protected Map<Integer, ExcelCell> getMergeCellMap() {
        if (this.mergeCellMap == null)
            this.mergeCellMap = new LinkedHashMap();
        return this.mergeCellMap;
    }

    protected void setTitleCellStyle(HSSFCellStyle titleCellStyle) {
        this.titleCellStyle = titleCellStyle;
    }

    protected HSSFCellStyle getTitleCellStyle() {
        return this.titleCellStyle;
    }

    protected void setOverviewCellStyle(HSSFCellStyle overviewCellStyle) {
        this.overviewCellStyle = overviewCellStyle;
    }

    protected HSSFCellStyle getOverviewCellStyle() {
        return this.overviewCellStyle;
    }

    protected void setHeaderCellStyle(HSSFCellStyle headerCellStyle) {
        this.headerCellStyle = headerCellStyle;
    }

    protected HSSFCellStyle getHeaderCellStyle() {
        return this.headerCellStyle;
    }

    protected void setContentCellStyle(HSSFCellStyle contentCellStyle) {
        this.contentCellStyle = contentCellStyle;
    }

    protected HSSFCellStyle getContentCellStyle() {
        return this.contentCellStyle;
    }


    public void setAcross(boolean across) {
        this.across = across;
    }

    protected boolean isAcross() {
        return this.across;
    }


    public void setAcrossHeaderField(String acrossHeaderField) {
        this.acrossHeaderField = acrossHeaderField;
    }

    protected String getAcrossHeaderField() {
        return this.acrossHeaderField;
    }


    public void setAcrossHeaderFormat(KeyFormat acrossHeaderFormat) {
        this.acrossHeaderFormat = acrossHeaderFormat;
    }

    protected KeyFormat getAcrossHeaderFormat() {
        return this.acrossHeaderFormat;
    }


    public void setAcrossValueField(String acrossValueField) {
        this.acrossValueField = acrossValueField;
    }

    protected String getAcrossValueField() {
        return this.acrossValueField;
    }


    public void setAcrossUniqueField(String uniqueField) {
        this.acrossUniqueField = uniqueField;
    }

    protected String getAcrossUniqueField() {
        return this.acrossUniqueField;
    }

    protected Map<String, Map<String, String>> getAcrossMap() {
        return this.acrossMap;
    }


    protected void setAcrossMap(Map<String, Map<String, String>> acrossMap) {
        this.acrossMap = acrossMap;
    }

    public void setExcelWriter(ExcelWriterUtil writer) {
        this.excelWriter = writer;
    }

    protected ExcelWriterUtil getExcelWriter() {
        return this.excelWriter;
    }


    public void setAutoSizeColumn(boolean autoSizeColumn) {
        this.autoSizeColumn = autoSizeColumn;
    }

    protected boolean isAutoSizeColumn() {
        return this.autoSizeColumn;
    }

    protected Integer[] getHiddenColumnIndex() {
        return this.hiddenColumnIndex;
    }

    public void setHiddenColumnIndex(Integer[] hiddenColumnIndex) {
        this.hiddenColumnIndex = hiddenColumnIndex;
    }

    protected Integer[] getLockCellColumnIndex() {
        return this.lockCellColumnIndex;
    }

    public void setLockCellColumnIndex(Integer[] lockCellColumnIndex) {
        this.lockCellColumnIndex = lockCellColumnIndex;
    }


    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    protected String getSheetName() {
        return this.sheetName;
    }

    protected String getNullCellValue() {
        return this.nullCellValue;
    }


    public void setNullCellValue(String nullCellValue) {
        this.nullCellValue = nullCellValue;
    }


    public Integer getStartRowIndex() {
        return this.startRowIndex;
    }


    public void setStartRowIndex(Integer startRowIndex) {
        this.startRowIndex = startRowIndex;
    }


    protected void setDynamicHeaderList(List<String> dynamicHeaderList) {
        this.dynamicHeaderList = dynamicHeaderList;
    }


    protected List<String> getDynamicHeaderList() {
        return this.dynamicHeaderList;
    }
}
