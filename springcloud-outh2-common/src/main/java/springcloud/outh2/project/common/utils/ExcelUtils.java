package springcloud.outh2.project.common.utils;//package com.youdu.wuhan2020.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jxls.exception.ParsePropertyException;
//import net.sf.jxls.transformer.XLSTransformer;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * <p>
// * </p>
// *
// * @author weiqing.lu
// * @version 1.0.0
// * @date 2017年12月7日 下午19:18:08
// */
//
//@SuppressWarnings("deprecation")
//@Slf4j
//public class ExcelUtils {
//
//    public ExcelUtils() {
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public static List<Map> readDataFromExcel(HSSFWorkbook workbook, String[] fields, int startRow, int startCol) {
//        ArrayList<Map> list = new ArrayList();
//        HSSFSheet sheet = workbook.getSheetAt(0);
//        int rowCount = sheet.getPhysicalNumberOfRows();
//        HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);
//
//        for (int i = startRow; i <= rowCount; ++i) {
//            HSSFRow _row = sheet.getRow(i);
//            if (_row != null) {
//                HashMap map = new HashMap();
//                int col = _row.getPhysicalNumberOfCells();
//                col = col < fields.length ? col : fields.length;
//
//                for (int j = startCol; j < col; ++j) {
//                    Object cellValue = null;
//                    HSSFCell cell = _row.getCell(j);
//                    if (cell != null) {
//                        HSSFCellStyle s = cell.getCellStyle();
//                        short dataFormat = s.getDataFormat();
//                        if (cell.getCellType() == 1) {
//                            cellValue = cell.getStringCellValue();
//                        } else if (cell.getCellType() == 0) {
//                            cellValue = cell.getNumericCellValue();
//                            if (dataFormat != 0) {
//                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                    cellValue = cell.getDateCellValue();
//                                } else if (dataFormat > 0) {
//                                    DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                    cellValue = decimalFormat.format(cell.getNumericCellValue());
//                                }
//                            }
//                        } else if (cell.getCellType() == 2) {
//                            String ret = "";
//                            int retType = formulaEvaluator.evaluateFormulaCell(cell);
//
//                            try {
//                                switch (retType) {
//                                    case 0:
//                                        ret = String.valueOf(cell.getNumericCellValue());
//                                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                            Date dateCellValue = cell.getDateCellValue();
//                                            String dataFormatString = s.getDataFormatString();
//                                            dataFormatString = dataFormatString.replaceAll("y+", "yyyy").replaceAll("d+", "dd").replace("m", "M");
//                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormatString);
//                                            ret = simpleDateFormat.format(dateCellValue);
//                                        } else if (dataFormat > 0) {
//                                            DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                            ret = decimalFormat.format(cell.getNumericCellValue());
//                                        }
//                                        break;
//                                    case 1:
//                                        ret = cell.getRichStringCellValue().getString();
//                                    case 2:
//                                    case 3:
//                                    default:
//                                        break;
//                                    case 4:
//                                        ret = String.valueOf(cell.getBooleanCellValue());
//                                        break;
//                                    case 5:
//                                        ret = "";
//                                }
//                            } catch (Exception var22) {
//                                log.error("Excel模板公式:" + var22.getMessage());
//                            }
//
//                            cellValue = ret;
//                        }
//                    }
//
//                    map.put(fields[j - startCol], cellValue);
//                }
//
//                list.add(map);
//            }
//        }
//
//        return list;
//    }
//
//    public static Workbook parse(String templatePath, Map<String, Object> params) {
//        XLSTransformer transformer = new XLSTransformer();
//
//        try {
//            FileInputStream fin = new FileInputStream(templatePath);
//            Workbook workbook = transformer.transformXLS(fin, params);
//            log.info("模板文件" + templatePath + "解析成功");
//            return workbook;
//        } catch (FileNotFoundException var5) {
//            throw new RuntimeException("模板文件" + templatePath + "不存在");
//        } catch (InvalidFormatException var6) {
//            throw new RuntimeException("模板文件存在错误表达式");
//        }
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public static List<Map> readDataFromExcelFor(HSSFWorkbook workbook, String[] fields, int startRow, int startCol) {
//        ArrayList<Map> list = new ArrayList();
//        HSSFSheet sheet = workbook.getSheetAt(0);
//        int rowCount = sheet.getPhysicalNumberOfRows();
//        HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);
//
//        for (int i = startRow; i <= rowCount - 1; ++i) {
//            HSSFRow _row = sheet.getRow(i);
//            if (_row != null) {
//                HashMap map = new HashMap();
//                int col = fields.length;
//
//                for (int j = startCol; j < col; ++j) {
//                    Object cellValue = null;
//                    HSSFCell cell = _row.getCell(j);
//                    if (cell != null) {
//                        try {
//                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                                String str = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
//                                cell.setCellValue(str);
//                            }
//                        } catch (Exception e) {
//                            log.error(e.getMessage(), e);
//                        }
//
//                        HSSFCellStyle s = cell.getCellStyle();
//                        short dataFormat = s.getDataFormat();
//                        if (cell.getCellType() == 1) {
//                            cellValue = cell.getStringCellValue();
//                        } else if (cell.getCellType() == 0) {
//                            cellValue = cell.getNumericCellValue();
//                            if (dataFormat != 0) {
//                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                    cellValue = cell.getDateCellValue();
//                                } else if (dataFormat > 0) {
//                                    DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                    cellValue = decimalFormat.format(cell.getNumericCellValue());
//                                }
//                            }
//                        } else if (cell.getCellType() == 2) {
//                            String ret = "";
//                            int retType = formulaEvaluator.evaluateFormulaCell(cell);
//
//                            try {
//                                switch (retType) {
//                                    case 0:
//                                        ret = String.valueOf(cell.getNumericCellValue());
//                                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                            Date dateCellValue = cell.getDateCellValue();
//                                            String dataFormatString = s.getDataFormatString();
//                                            dataFormatString = dataFormatString.replaceAll("y+", "yyyy").replaceAll("d+", "dd").replace("m", "M");
//                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormatString);
//                                            ret = simpleDateFormat.format(dateCellValue);
//                                        } else if (dataFormat > 0) {
//                                            DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                            ret = decimalFormat.format(cell.getNumericCellValue());
//                                        }
//                                        break;
//                                    case 1:
//                                        ret = cell.getRichStringCellValue().getString();
//                                    case 2:
//                                    case 3:
//                                    default:
//                                        break;
//                                    case 4:
//                                        ret = String.valueOf(cell.getBooleanCellValue());
//                                        break;
//                                    case 5:
//                                        ret = "";
//                                }
//                            } catch (Exception var23) {
//                                log.error("Excel模板公式:" + var23.getMessage());
//                            }
//
//                            cellValue = ret;
//                        }
//                    }
//
//                    map.put(fields[j - startCol], cellValue);
//                }
//
//                list.add(map);
//            }
//        }
//
//        return list;
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
//    public static List<Map> readDataFromExcelFor(Workbook workbook, String[] fields, int startRow, int startCol) {
//        ArrayList<Map> list = new ArrayList();
//        Sheet sheet = workbook.getSheetAt(0);
//        int rowCount = sheet.getPhysicalNumberOfRows();
//        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
//
//        for (int i = startRow; i <= rowCount - 1; ++i) {
//            Row _row = sheet.getRow(i);
//            if (_row != null) {
//                HashMap map = new HashMap();
//                boolean isNullRow = true;
//                int col = fields.length;
//
//                for (int j = startCol; j < col; ++j) {
//                    Object cellValue = null;
//                    Cell cell = _row.getCell(j);
//                    if (cell != null) {
//                        CellStyle s = cell.getCellStyle();
//                        short dataFormat = s.getDataFormat();
//                        switch (cell.getCellType()) {
//                            case 0:
//                                cellValue = cell.getNumericCellValue();
//                                cellValue = BigDecimal.valueOf(cell.getNumericCellValue());
//                                if (dataFormat != 0) {
//                                    if (DateUtil.isCellDateFormatted(cell)) {
//                                        cellValue = cell.getDateCellValue();
//                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                        cellValue = simpleDateFormat.format(cellValue);
//                                    } else if (dataFormat > 0) {
//                                        DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", "").replace("@", ""));
//                                        cellValue = decimalFormat.format(cell.getNumericCellValue());
//                                    }
//                                }
//                                break;
//                            case 1:
//                                cellValue = cell.getStringCellValue();
//                                if (isNullRow) {
//                                    isNullRow = !StringUtils.isNotBlank(cellValue.toString());
//                                }
//                                break;
//                            case 2:
//                                String ret = "";
//
//                                try {
//                                    ret = cell.getStringCellValue();
//                                } catch (IllegalStateException var21) {
//                                    if (dataFormat != 0) {
//                                        if (DateUtil.isCellDateFormatted(cell)) {
//                                            cellValue = cell.getDateCellValue();
//                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                            simpleDateFormat.format(cellValue);
//                                        } else if (dataFormat > 0) {
//                                            DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                            decimalFormat.format(cell.getNumericCellValue());
//                                        }
//                                    }
//                                }
//
//                                cellValue = ret;
//                                break;
//                            default:
//                                cellValue = cell.getStringCellValue();
//                        }
//                    }
//
//                    map.put(fields[j - startCol], cellValue);
//                }
//
//                if (map.get(fields[0]) != null && !isNullRow) {
//                    list.add(map);
//                }
//            }
//        }
//
//        return list;
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    public static List<Map> readDataFromExcelFor(XSSFWorkbook workbook, String[] fields, int startRow, int startCol) {
//        ArrayList<Map> list = new ArrayList();
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        int rowCount = sheet.getPhysicalNumberOfRows();
//        XSSFFormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator(workbook);
//
//        for (int i = startRow; i <= rowCount - 1; ++i) {
//            XSSFRow _row = sheet.getRow(i);
//            if (_row != null) {
//                HashMap map = new HashMap();
//                int col = fields.length;
//
//                for (int j = startCol; j < col; ++j) {
//                    Object cellValue = null;
//                    XSSFCell cell = _row.getCell(j);
//                    if (cell != null) {
//                        try {
//                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                                String str = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
//                                cell.setCellValue(str);
//                            }
//                        } catch (Exception e) {
//                            log.error(e.getMessage(), e);
//                        }
//
//                        XSSFCellStyle s = cell.getCellStyle();
//                        short dataFormat = s.getDataFormat();
//                        if (cell.getCellType() == 1) {
//                            cellValue = cell.getStringCellValue();
//                        } else if (cell.getCellType() == 0) {
//                            cellValue = cell.getNumericCellValue();
//                            if (dataFormat != 0) {
//                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                    cellValue = cell.getDateCellValue();
//                                } else if (dataFormat > 0) {
//                                    DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                    cellValue = decimalFormat.format(cell.getNumericCellValue());
//                                }
//                            }
//                        } else if (cell.getCellType() == 2) {
//                            String ret = "";
//                            int retType = formulaEvaluator.evaluateFormulaCell(cell);
//
//                            try {
//                                switch (retType) {
//                                    case 0:
//                                        ret = String.valueOf(cell.getNumericCellValue());
//                                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                            Date dateCellValue = cell.getDateCellValue();
//                                            String dataFormatString = s.getDataFormatString();
//                                            dataFormatString = dataFormatString.replaceAll("y+", "yyyy").replaceAll("d+", "dd").replace("m", "M");
//                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormatString);
//                                            ret = simpleDateFormat.format(dateCellValue);
//                                        } else if (dataFormat > 0) {
//                                            DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                            ret = decimalFormat.format(cell.getNumericCellValue());
//                                        }
//                                        break;
//                                    case 1:
//                                        ret = cell.getRichStringCellValue().getString();
//                                    case 2:
//                                    case 3:
//                                    default:
//                                        break;
//                                    case 4:
//                                        ret = String.valueOf(cell.getBooleanCellValue());
//                                        break;
//                                    case 5:
//                                        ret = "";
//                                }
//                            } catch (Exception var23) {
//                                log.error("Excel模板公式:" + var23.getMessage());
//                            }
//
//                            cellValue = ret;
//                        }
//                    }
//
//                    map.put(fields[j - startCol], cellValue);
//                }
//
//                list.add(map);
//            }
//        }
//
//        return list;
//    }
//
//    @SuppressWarnings("rawtypes")
//    public static void excprtExcel(Map beans, String srcPath, OutputStream os) {
//        XLSTransformer transformer = new XLSTransformer();
//
//        try {
//            FileInputStream in = new FileInputStream(srcPath);
//            Workbook workbook = transformer.transformXLS(in, beans);
//            workbook.write(os);
//        } catch (Exception var13) {
//            var13.printStackTrace();
//            throw new RuntimeException();
//        } finally {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException var12) {
//                    var12.printStackTrace();
//                }
//            }
//
//        }
//
//    }
//
//    public void createExcel(String srcFilePath, Map<String, Object> beanParams, String destFilePath) {
//        XLSTransformer transformer = new XLSTransformer();
//
//        try {
//            transformer.transformXLS(srcFilePath, beanParams, destFilePath);
//        } catch (InvalidFormatException var6) {
//            var6.printStackTrace();
//        } catch (ParsePropertyException var7) {
//            var7.printStackTrace();
//        } catch (IOException var8) {
//            var8.printStackTrace();
//        }
//
//    }
//
//    @SuppressWarnings({"unused"})
//    public static String toHtmlString(HSSFWorkbook workbook, String sheetName) throws UnsupportedEncodingException {
//        HSSFSheet sheet = workbook.getSheet(sheetName);
//        HSSFRow row = sheet.getRow(0);
//        int rows = sheet.getLastRowNum();
//        int cols = row.getLastCellNum();
//        int dstart = -1;
//        int dend = 1;
//
//        for (int i = 0; i < rows; ++i) {
//            boolean find = false;
//
//            for (int j = 0; j < cols && !find; ++j) {
//                row = sheet.getRow(i);
//                if (row != null) {
//                    HSSFCell cell = row.getCell((short) j);
//                    if (cell != null && cell.getCellType() == 1 && cell.getStringCellValue() != null && cell.getStringCellValue().startsWith("D{")) {
//                        if (dstart == -1) {
//                            dstart = i;
//                        }
//
//                        find = true;
//                    }
//                }
//            }
//        }
//
//        return ConvertToHtml(sheet, workbook);
//    }
//
//    private static String getColor(short c) {
//        if (c >= 8 && c <= 63) {
//            String color = ((HSSFColor) HSSFColor.getIndexHash().get(new Integer(c))).getHexString();
//            String[] cs = color.split(":");
//            color = "#";
//
//            for (int j = 0; j < cs.length; ++j) {
//                if (cs[j].length() == 1) {
//                    color = color + cs[j] + cs[j];
//                } else if (cs[j].length() == 4) {
//                    color = color + cs[j].substring(2);
//                } else {
//                    color = color + cs[j];
//                }
//            }
//
//            return color;
//        } else {
//            return "";
//        }
//    }
//
//    @SuppressWarnings({"rawtypes"})
//    private static String ConvertToHtml(HSSFSheet sheet, HSSFWorkbook workbook) throws UnsupportedEncodingException {
//        int row = sheet.getLastRowNum() + 1;
//        HSSFRow _row = sheet.getRow(0);
//        int col = _row.getPhysicalNumberOfCells();
//        String[][][] tdinfo = new String[row][col][2];
//        Map style = new HashMap();
//
//        int max;
//        int i;
//        int j;
//        for (i = 0; i < sheet.getNumMergedRegions(); ++i) {
//            Region m = sheet.getMergedRegionAt(i);
//            max = m.getRowFrom();
//            i = m.getRowTo();
//            int cs = m.getColumnFrom();
//            int ce = m.getColumnTo();
//            System.out.println("rs:" + max + " re:" + i + " cs:" + cs + " ce:" + ce);
//            tdinfo[max][cs][0] = "";
//            if (i > max) {
//                tdinfo[max][cs][0] = tdinfo[max][cs][0] + " rowspan='" + (i - max + 1) + "'";
//            }
//
//            if (ce > cs) {
//                tdinfo[max][cs][0] = tdinfo[max][cs][0] + " colspan='" + (ce - cs + 1) + "'";
//            }
//
//            for (i = max; i <= i; ++i) {
//                for (j = cs; j <= ce; ++j) {
//                    if (i != max || j != cs) {
//                        tdinfo[i][j] = null;
//                    }
//                }
//            }
//        }
//
//        float[] width = new float[col];
//        int widthsum = 0;
//        max = 0;
//
//        for (i = 0; i < col; ++i) {
//            width[i] = (float) sheet.getColumnWidth(i);
//            if (width[i] >= width[max]) {
//                max = i;
//            }
//
//            widthsum = (int) ((float) widthsum + width[i]);
//        }
//
//        width[max] = 0.0F;
//        HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);
//
//        for (i = 0; i < row; ++i) {
//            _row = sheet.getRow(i);
//            if (_row != null) {
//                for (j = 0; j < col; ++j) {
//                    if (tdinfo[i][j] != null) {
//                        HSSFCell cell = _row.getCell(j);
//                        if (cell != null) {
//                            HSSFCellStyle s = cell.getCellStyle();
//                            if (tdinfo[i][j][0] == null) {
//                                tdinfo[i][j][0] = "";
//                            }
//
//                            short dataFormat = s.getDataFormat();
//                            tdinfo[i][j][0] = tdinfo[i][j][0] + " class='" + getCssByStyle(s, workbook, style) + "'";
//                            if (cell.getCellType() == 1) {
//                                tdinfo[i][j][1] = cell.getStringCellValue();
//                            } else if (cell.getCellType() == 0) {
//                                tdinfo[i][j][1] = cell.getNumericCellValue() + "";
//                                if (dataFormat != 0) {
//                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                        Date dateCellValue = cell.getDateCellValue();
//                                        String dataFormatString = s.getDataFormatString();
//                                        dataFormatString = dataFormatString.replaceAll("y+", "yyyy").replaceAll("d+", "dd").replace("m", "M");
//                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormatString);
//                                        tdinfo[i][j][1] = simpleDateFormat.format(dateCellValue);
//                                    } else if (dataFormat > 0) {
//                                        DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                        tdinfo[i][j][1] = decimalFormat.format(cell.getNumericCellValue());
//                                    }
//                                }
//                            } else if (cell.getCellType() == 2) {
//                                String ret = "";
//                                int retType = formulaEvaluator.evaluateFormulaCell(cell);
//
//                                try {
//                                    switch (retType) {
//                                        case 0:
//                                            ret = String.valueOf(cell.getNumericCellValue());
//                                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                                Date dateCellValue = cell.getDateCellValue();
//                                                String dataFormatString = s.getDataFormatString();
//                                                dataFormatString = dataFormatString.replaceAll("y+", "yyyy").replaceAll("d+", "dd").replace("m", "M");
//                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormatString);
//                                                ret = simpleDateFormat.format(dateCellValue);
//                                            } else if (dataFormat > 0) {
//                                                DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                                ret = decimalFormat.format(cell.getNumericCellValue());
//                                            }
//                                            break;
//                                        case 1:
//                                            ret = cell.getRichStringCellValue().getString();
//                                        case 2:
//                                        case 3:
//                                        default:
//                                            break;
//                                        case 4:
//                                            ret = String.valueOf(cell.getBooleanCellValue());
//                                            break;
//                                        case 5:
//                                            ret = "";
//                                    }
//                                } catch (Exception var21) {
//                                    log.error("Excel模板公式:" + var21.getMessage());
//                                }
//
//                                tdinfo[i][j][1] = ret;
//                            }
//                        } else {
//                            tdinfo[i][j] = null;
//                        }
//                    }
//                }
//            }
//        }
//
//        StringBuffer br = new StringBuffer();
//        br.append("<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>");
//        br.append("<head><meta http-equiv=Content-Type content='text/html; charset=gb2312'><meta name=ProgId content=Excel.Sheet>");
//        br.append("<style>");
//        Iterator it = style.values().iterator();
//
//        while (it.hasNext()) {
//            String[] css = (String[]) ((String[]) it.next());
//            br.append(css[1]);
//        }
//
//        br.append("</style></head><body onload='' style=\"text-align: center;background: url('../../EF/Images/dmshuiyin.png') repeat\" onselectstart=\"return false\"  oncontextmenu=\"return false;\" ><center>");
//        br.append("<table cellspacing='0' cellpadding='0' style='border-collapse:collapse;width:100%;'>");
//
//        for (i = 0; i < col; ++i) {
//            if (i != max) {
//                br.append("<col width='" + Math.rint((double) (width[i] / (float) widthsum * 100.0F)) + "%'>");
//            } else {
//                br.append("<col>");
//            }
//        }
//
//        for (i = 0; i < row; ++i) {
//            br.append("<tr>");
//
//            for (j = 0; j < col; ++j) {
//                if (tdinfo[i][j] != null) {
//                    if (tdinfo[i][j][0] == null) {
//                        tdinfo[i][j][0] = "";
//                    }
//
//                    if (tdinfo[i][j][1] == null) {
//                        tdinfo[i][j][1] = " ";
//                    }
//
//                    br.append("<td " + tdinfo[i][j][0] + ">" + tdinfo[i][j][1].replaceAll("\n", "<br>") + "</td>");
//                }
//            }
//
//            br.append("</tr>");
//        }
//
//        br.append("</table></center>");
//        br.append("</body></html>");
//        return br.toString();
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    private static String getCssByStyle(HSSFCellStyle s, HSSFWorkbook workbook, Map style) {
//        String[] css;
//        if (style.containsKey(s)) {
//            css = (String[]) ((String[]) style.get(s));
//            return css[0];
//        } else {
//            css = new String[]{"c" + style.size(), null};
//            StringBuffer cssinfo = new StringBuffer();
//            switch (s.getAlignment()) {
//                case 1:
//                    cssinfo.append("text-align:left;");
//                    break;
//                case 2:
//                    cssinfo.append("text-align:center;");
//                    break;
//                case 3:
//                    cssinfo.append("text-align:right;");
//            }
//
//            cssinfo.append("background-color:" + getColor(s.getFillForegroundColor()) + ";");
//            cssinfo.append("border-top:" + s.getBorderTop() + "px solid #000000;");
//            cssinfo.append("border-left:" + s.getBorderLeft() + "px solid #000000;");
//            cssinfo.append("border-right:" + s.getBorderLeft() + "px solid #000000;");
//            cssinfo.append("border-bottom:" + s.getBorderBottom() + "px solid #000000;");
//            cssinfo.append("padding-top:4px;");
//            cssinfo.append("padding-bottom:2px;");
//            HSSFFont font = workbook.getFontAt(s.getFontIndex());
//            cssinfo.append("font-size:" + (font.getFontHeightInPoints() + 3) + "pt;");
//            if (700 == font.getBoldweight()) {
//                cssinfo.append("font-weight: bold;");
//            }
//
//            cssinfo.append("font-family: " + font.getFontName() + ";");
//            if (font.getItalic()) {
//                cssinfo.append("font-style: italic;");
//            }
//
//            String fontcolor = getColor(font.getColor());
//            if (fontcolor.trim().length() > 0) {
//                cssinfo.append("color: " + fontcolor + ";");
//            }
//
//            css[1] = "." + css[0] + "{" + cssinfo.toString() + "}";
//            style.put(s, css);
//            return css[0];
//        }
//    }
//
//    @SuppressWarnings({"unused", "rawtypes"})
//    private static void fillDetail(HSSFSheet sheet, int start, int dnum, Map m) {
//        for (int i = 0; i < dnum; ++i) {
//            HSSFRow row = sheet.getRow(start + i);
//
//            for (int j = 0; j <= row.getLastCellNum(); ++j) {
//                HSSFCell cell = row.getCell((short) j);
//                if (cell != null && cell.getCellType() == 1 && cell.getStringCellValue() != null && cell.getStringCellValue().startsWith("D{")) {
//                    String key = cell.getStringCellValue();
//                    key = key.replaceAll("(D\\{)|(\\}$)", "");
//                    cell.setCellValue((String) m.get(key));
//                }
//            }
//        }
//
//    }
//
//    @SuppressWarnings({"unused", "rawtypes"})
//    private static void fillHeader(HSSFSheet sheet, int dstart, Map m) {
//        for (int i = 0; i < dstart; ++i) {
//            HSSFRow row = sheet.getRow(i);
//
//            for (int j = 0; j <= row.getLastCellNum(); ++j) {
//                HSSFCell cell = row.getCell((short) j);
//                if (cell != null && cell.getCellType() == 1 && cell.getStringCellValue() != null && cell.getStringCellValue().startsWith("H{")) {
//                    String key = cell.getStringCellValue();
//                    key = key.replaceAll("(T\\{)|(H\\{)|(\\}$)", "");
//                    cell.setCellValue((String) m.get(key));
//                }
//            }
//        }
//
//    }
//
//    @SuppressWarnings({"rawtypes", "unused", "unchecked"})
//    private static List getMergeds(HSSFSheet sheet, int dstart, int dend) {
//        List mergeds = new ArrayList();
//
//        for (int i = 0; i < sheet.getNumMergedRegions(); ++i) {
//            Region m = sheet.getMergedRegionAt(i);
//            if (dstart <= m.getRowFrom() && m.getRowTo() <= dend) {
//                mergeds.add(m);
//            }
//        }
//
//        return mergeds;
//    }
//
//    @SuppressWarnings({"rawtypes"})
//    public static void RowCopy(HSSFSheet sheet, int start, int rows, int to, List mergeds) {
//        int i;
//        for (i = 0; i < rows; ++i) {
//            HSSFRow row = sheet.getRow(start + i);
//            HSSFRow newrow = sheet.createRow(to + i);
//
//            for (int j = 0; j <= row.getLastCellNum(); ++j) {
//                HSSFCell cell = row.getCell((short) j);
//                HSSFCell newcell = newrow.createCell((short) j);
//                if (cell != null) {
//                    switch (cell.getCellType()) {
//                        case 1:
//                            newcell.setCellValue(cell.getStringCellValue());
//                        default:
//                            newcell.setCellStyle(cell.getCellStyle());
//                    }
//                }
//            }
//        }
//
//        for (i = 0; i < mergeds.size(); ++i) {
//            Region m = (Region) mergeds.get(i);
//            Region _m = new Region(m.getRowFrom() - start + to, m.getColumnFrom(), m.getRowTo() - start + to, m.getColumnTo());
//            sheet.addMergedRegion(_m);
//        }
//
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    public static Map<String, List<Map>> readDataFromExcelForManySheet(HSSFWorkbook workbook, List<String> fieldliList, int sheetNumber) {
//        Map<String, List<Map>> resultMap = new HashMap();
//
//        for (int sheetIndex = 0; sheetIndex < sheetNumber; ++sheetIndex) {
//            ArrayList<Map> list = new ArrayList();
//            HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
//            String fieldsParam = (String) fieldliList.get(sheetIndex);
//            String[] fields = fieldsParam.split(",");
//            int rowCount = sheet.getPhysicalNumberOfRows();
//            HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);
//
//            for (int i = 1; i <= rowCount; ++i) {
//                HSSFRow _row = sheet.getRow(i);
//                if (_row != null) {
//                    HashMap map = new HashMap();
//                    int col = _row.getLastCellNum();
//                    col = col < fields.length ? col : fields.length;
//
//                    for (int j = 0; j < col; ++j) {
//                        Object cellValue = null;
//                        HSSFCell cell = _row.getCell(j);
//                        if (cell != null) {
//                            try {
//                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                                    String str = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
//                                    cell.setCellValue(str);
//                                }
//                            } catch (Exception e) {
//                                log.error(e.getMessage(), e);
//                            }
//
//                            HSSFCellStyle s = cell.getCellStyle();
//                            short dataFormat = s.getDataFormat();
//                            if (cell.getCellType() == 1) {
//                                cellValue = cell.getStringCellValue();
//                            } else {
//                                Date dateCellValue;
//                                if (cell.getCellType() == 0) {
//                                    cellValue = cell.getNumericCellValue();
//                                    if (dataFormat != 0) {
//                                        if (dataFormat == 178) {
//                                            double value = cell.getNumericCellValue();
//                                            dateCellValue = DateUtil.getJavaDate(value);
//                                            SimpleDateFormat deFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
//                                            cellValue = deFormat.format(dateCellValue);
//                                        } else if (dataFormat > 0) {
//                                            DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                            cellValue = decimalFormat.format(cell.getNumericCellValue());
//                                        }
//                                    }
//                                } else if (cell.getCellType() == 2) {
//                                    String ret = "";
//                                    int retType = formulaEvaluator.evaluateFormulaCell(cell);
//
//                                    try {
//                                        switch (retType) {
//                                            case 0:
//                                                ret = String.valueOf(cell.getNumericCellValue());
//                                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                                    dateCellValue = cell.getDateCellValue();
//                                                    String dataFormatString = s.getDataFormatString();
//                                                    dataFormatString = dataFormatString.replaceAll("y+", "yyyy").replaceAll("d+", "dd").replace("m", "M");
//                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormatString);
//                                                    ret = simpleDateFormat.format(dateCellValue);
//                                                } else if (dataFormat > 0) {
//                                                    DecimalFormat decimalFormat = new DecimalFormat(s.getDataFormatString().replace("\\", "").replace("\"", ""));
//                                                    ret = decimalFormat.format(cell.getNumericCellValue());
//                                                }
//                                                break;
//                                            case 1:
//                                                ret = cell.getRichStringCellValue().getString();
//                                            case 2:
//                                            case 3:
//                                            default:
//                                                break;
//                                            case 4:
//                                                ret = String.valueOf(cell.getBooleanCellValue());
//                                                break;
//                                            case 5:
//                                                ret = "";
//                                        }
//                                    } catch (Exception var26) {
//                                        log.error("Excel模板公式:" + var26.getMessage());
//                                    }
//
//                                    cellValue = ret;
//                                }
//                            }
//                        }
//
//                        map.put(fields[j - 0], cellValue);
//                    }
//
//                    list.add(map);
//                }
//            }
//
//            resultMap.put("result" + sheetIndex, list);
//        }
//
//        return resultMap;
//    }
//}
