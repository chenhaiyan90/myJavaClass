package com.spring.common.util;

import com.spring.common.exception.BusinessException;
import com.spring.common.util.enums.Contants;
import org.apache.axis.wsdl.symbolTable.ContainedAttribute;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 */
public class ExcelUtils {

    public interface ExportInstance {
        List<Map<String, Object>> getExportData();

        Map<String, String> getFakeAttr();
    }

    /**
     * 导出工具
     * @param titles
     * @param contents
     * @param exportInstance
     * @param response
     * @throws IOException
     */
    public static void exportExcel(String fileName, String[] titles, String[] contents,
                                   HttpServletResponse response, ExportInstance exportInstance) {
        if (titles == null) throw new BusinessException("导出的excel的Title不能为空.");
        if (contents == null) throw new BusinessException("导出的excel的contents不能为空.");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        try {
            List<Map<String, Object>> activeList = exportInstance.getExportData();
            Workbook book = ExcelUtils.exportExcel(fileName, activeList, titles, contents, exportInstance.getFakeAttr());
            OutputStream ouputStream = response.getOutputStream();
            book.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            throw new BusinessException("导出失败:", e);
        }
    }

    /**
     * @param sheetName  第一个sheet名称
     * @param list       导出数据
     * @param titles     表格标题
     * @param fieldNames 取值列名
     * @param fakeAttr   用来把数据库中的0,1这种假属性替换成现实的值 比如 1：成功 0：失败
     * @return
     * @describe 导出数据到excel表
     * @auto ambitor_luo
     * @date 2015年3月31日
     */
    public static <T> HSSFWorkbook exportExcel(List<T> list, String sheetName,
                                               String[] titles, String[] fieldNames, Map<String, String> fakeAttr) {

        HSSFWorkbook wb = new HSSFWorkbook();
        int sheetNum = 0, rowNum = 1;
        // 对每个表生成一个新的sheet,并以表名命名
        HSSFSheet sheet = null;
        for (int i = 0; i < list.size(); i++) {

            if (i % 65530 == 0) {
                rowNum = 1;
                sheetNum++;
                String currentSheetName = sheetName == null ? "sheet" + sheetNum : sheetName + sheetNum;
                sheet = wb.createSheet(currentSheetName);
                // 设置表头的说明
                HSSFRow topRow = sheet.createRow(0);
                // 设置标题
                for (int t = 0; t < titles.length; t++) {
                    setCellGBKValue(topRow.createCell(t), titles[t]);
                }
            }

            HSSFRow row = sheet.createRow(rowNum);
            T t = list.get(i);
            for (int j = 0; j < fieldNames.length; j++) {
                //去除显示null的问题
                try {
                    Field uidF = StringUtils.getField(t.getClass(), fieldNames[j]);
                    uidF.setAccessible(true);
                    Object temp = uidF.get(t);
                    // 转换为默认时间格式
                    temp = dateTimeToString(temp);
                    Object value = temp == null ? "" : temp;
                    if (fakeAttr.containsKey(String.valueOf(value))) value = fakeAttr.get(value);
                    setCellGBKValue(row.createCell(j), value);
                } catch (Exception e) {
                    throw new BusinessException("导出失败，找不到字段" + fieldNames[j]);
                }
            }
            rowNum++;
        }
        return wb;
    }

    /**
     * @param sheetName  第一个sheet名称
     * @param list       导出数据
     * @param titles     表格标题
     * @param fieldNames 取值列名
     * @param fakeAttr   用来把数据库中的0,1这种假属性替换成现实的值 比如 1：成功 0：失败
     * @return
     * @describe 导出数据到excel表
     * @auto ambitor_luo
     * @date 2015年3月31日
     */
    public static Workbook exportExcel(String sheetName, List<Map<String, Object>> list,
                                       String[] titles, String[] fieldNames, Map<String, String> fakeAttr) {
        Workbook wb = null;
        if (sheetName.endsWith("xls")) wb = new HSSFWorkbook();
        else if (sheetName.endsWith("xlsx")) wb = new XSSFWorkbook();
        else wb = new XSSFWorkbook();
        int k = 0;
        // 对每个表生成一个新的sheet,并以表名命名
        if (sheetName == null) {
            sheetName = "sheet1";
        }
        Sheet sheet = wb.createSheet("sheet1");
        // 设置表头的说明
        Row topRow = sheet.createRow(0);
        // 设置标题
        for (int i = 0; i < titles.length; i++) {
            setCellGBKValue(topRow.createCell(i), titles[i]);
        }

        k = 1;
        for (Map<String, Object> map : list) {
            Row row = sheet.createRow(k);
            for (int i = 0; i < fieldNames.length; i++) {
                //去除显示null的问题
                Object temp = map.get(fieldNames[i]);

                // 转换为默认时间格式
                temp = dateTimeToString(temp);
                Object value = temp == null ? "" : temp;
                if (fakeAttr.containsKey(String.valueOf(value))) value = fakeAttr.get(String.valueOf(value));
                setCellGBKValue(row.createCell(i), value);
            }
            k++;
        }
        return wb;
    }

    /**
     * 转换默认时间格式
     * @param temp 数据对象
     * @return 格式化后的时间字符
     */
    private static Object dateTimeToString(Object temp) {
        if (temp != null) {
            /* 如时间格式，以yyyy-MM-dd HH:mm:ss格式导出 */
            if (temp.getClass() == Date.class || temp.getClass() == java.sql.Date.class ||
                    temp.getClass() == java.sql.Timestamp.class) {
                temp = DateUtil.convertDate2String("yyyy-MM-dd HH:mm:ss", (Date) temp);
            }
        }
        return temp;
    }

    /**
     * @param sheetName  第一个sheet名称
     * @param list       导出数据
     * @param titles     表格标题
     * @param fieldNames 取值列名
     * @param startRow   从第几行开始写表格
     * @return
     * @Description 重写
     * @author ambitor_luo
     * @date 2015年11月13日
     */
    public static HSSFWorkbook exportExcel(String sheetName, List<Map<Object, Object>> list, String[] titles, String[] fieldNames, List<String> infoList, Integer startRow) {

        HSSFWorkbook wb = new HSSFWorkbook();
        try {
            HSSFSheet sheet = null;
            int k = 0;
            // 对每个表生成一个新的sheet,并以表名命名
            if (sheetName == null) {
                sheetName = "sheet1";
            }
            sheet = wb.createSheet("sheet1");
            // 设置表头的说明
            HSSFRow topRow = sheet.createRow(0);
            HSSFCellStyle topCellStyle = wb.createCellStyle();//加粗
            HSSFFont f = wb.createFont();
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            topCellStyle.setFont(f);
            //设置表外的附加信息
            for (int i = 0; i < infoList.size(); i++) {
                HSSFCell cell = topRow.createCell(i);
                //偶数加粗
                if (i % 2 == 0) {
                    cell.setCellStyle(topCellStyle);
                }
                setCellGBKValue(cell, infoList.get(i));
            }

            HSSFRow titleRow = sheet.createRow(startRow - 1);
            HSSFCellStyle cellStyle = wb.createCellStyle();//标题加上上边框
            cellStyle.setBorderTop((short) 1);
            // 设置标题
            for (int i = 0; i < titles.length; i++) {
                HSSFCell cell = titleRow.createCell(i);
                cell.setCellStyle(cellStyle);
                setCellGBKValue(cell, titles[i]);
            }

            k = 1;
            for (Map<Object, Object> map : list) {
                HSSFRow row = sheet.createRow(k + startRow - 1);
                for (int i = 0; i < fieldNames.length; i++) {
                    if (fieldNames[i].equals("number")) {
                        setCellGBKValue(row.createCell(i), k + "");
                    } else {
                        //去除显示null的问题
                        Object temp = map.get(fieldNames[i]);

                        // 转换为默认时间格式
                        temp = dateTimeToString(temp);

                        setCellGBKValue(row.createCell(i), temp == null ? "" : (temp + ""));
                    }
                }
                k++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }

    private static void setCellGBKValue(Cell cell, Object value) {
        if (value instanceof Number) {
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(Double.parseDouble(String.valueOf(value)));
        } else {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(value == null ? "" : String.valueOf(value));
        }
    }

    /**
     * @param wb
     * @param fileName
     * @param response
     * @throws IOException
     * @describe 导出
     * @auto ambitor_luo
     * @date 2015年4月1日
     */
    public static void export(HSSFWorkbook wb, String fileName, HttpServletResponse response) throws IOException {
        wb = new HSSFWorkbook();//用于禁止所有试图通过此接口导出excel的地方，如果要解禁，请删除此行代码　add by fengxing 2016-04-06
        // 设置response的编码方式
        response.setContentType("application/x-msdownload");

        // 写明要下载的文件的大小
        // response.setContentLength((int)fileName.length());

        // 设置附加文件名
        response.setHeader("Content-Disposition", "attachment;filename="
                + fileName);

        // 解决中文乱码
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
        OutputStream output = response.getOutputStream();
        wb.write(output);

        output.flush();
        output.close();

    }

    /**
     * @param sheetName
     * @param list
     * @param titles
     * @param fieldNames
     * @return
     */
    public static HSSFWorkbook exportExcel(String sheetName, List<Map<String, Object>> list,
                                           String[] titles, String[] fieldNames) {

        HSSFWorkbook wb = new HSSFWorkbook();
        int k = 0;
        // 对每个表生成一个新的sheet,并以表名命名
        if (sheetName == null) {
            sheetName = "sheet1";
        }
        HSSFSheet sheet = wb.createSheet("sheet1");
        // 设置表头的说明
        HSSFRow topRow = sheet.createRow(0);
        // 设置标题
        for (int i = 0; i < titles.length; i++) {
            setCellGBKValue(topRow.createCell(i), titles[i]);
        }

        k = 1;
        for (Map<String, Object> map : list) {
            HSSFRow row = sheet.createRow(k);
            for (int i = 0; i < fieldNames.length; i++) {
                //去除显示null的问题
                Object temp = map.get(fieldNames[i]);

                // 转换为默认时间格式
                temp = dateTimeToString(temp);
                String value = String.valueOf(temp == null ? "" : temp);
                //  if (fakeAttr.containsKey(value)) value = fakeAttr.get(value);
                try {
                    Contants.Fake enumValue = Contants.Fake.valueOf(value);
                    setCellGBKValue(row.createCell(i), enumValue.getValue());
                } catch (Exception e) {
                    setCellGBKValue(row.createCell(i), value);
                }
            }
            k++;
        }
        return wb;
    }

}
