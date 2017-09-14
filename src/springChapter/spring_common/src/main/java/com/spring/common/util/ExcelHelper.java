 /**
 * Project Name:com.zj.common
 * File Name:ExcelHelper.java
 * Package Name:com.zj.common.utils
 * Date:2016年4月22日下午4:51:42
 * Copyright (c) 2016, Jevin.Xu All Rights Reserved.
 *
*/

package com.spring.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * ClassName:ExcelHelper <br/>
 * Date:     2016年4月22日 下午4:51:42 <br/>
 * @author   Jevin.Xu
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class ExcelHelper {

    /** 
     * Excel 2003 
     */  
    private final static String XLS = "xls";  
    /** 
     * Excel 2007 
     */  
    private final static String XLSX = "xlsx";  
    
    /** 
     * 由Excel流的Sheet导出至List 
     *  
     * @param is 
     * @param extensionName 
     * @param sheetNum 
     * @return 
     * @throws IOException 
     */  
    public static List<Map<String,String>> exportListFromExcel(InputStream is,String extensionName, int sheetNum) throws IOException {  
  
        Workbook workbook = null;
        if (extensionName.toLowerCase().equals(XLS)) {  
            workbook = new HSSFWorkbook(is);  
        } else if (extensionName.toLowerCase().equals(XLSX)) {  
            workbook = new XSSFWorkbook(is);  
        } else{
            return null;
        }
       
        return exportListFromExcel(workbook, sheetNum);  
    }  
  
    /** 
     * 由指定的Sheet导出至List 
     *  
     * @param workbook 
     * @param sheetNum 
     * @return 
     * @throws IOException 
     */  
    private static List<Map<String,String>> exportListFromExcel(Workbook workbook,  int sheetNum) {  
  
        Sheet sheet = workbook.getSheetAt(sheetNum);  
  
        // 解析公式结果  
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();  
  
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();  
  
        int minRowIx = sheet.getFirstRowNum();  //第一行
        int maxRowIx = sheet.getLastRowNum();  //最后一行
        for (int rowIx = minRowIx+1; rowIx <= maxRowIx; rowIx++) {  
            Row row = sheet.getRow(rowIx);  
            short minColIx = row.getFirstCellNum();  
            short maxColIx = row.getLastCellNum();  
            Map<String,String> m=new HashMap<>();
            for (short colIx = minColIx; colIx <= maxColIx; colIx++) {  
                Cell cell = row.getCell(new Integer(colIx));  
                CellValue cellValue = evaluator.evaluate(cell);  
                if (cellValue == null) {  
                    continue;  
                }  
                // 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了  
                // 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html  
                switch (cellValue.getCellType()) {  
                case Cell.CELL_TYPE_BOOLEAN:  
                    m.put("F"+(colIx+1), cellValue.getBooleanValue()+"");
                    break;  
                case Cell.CELL_TYPE_NUMERIC:  
                    // 这里的日期类型会被转换为数字类型，需要判别后区分处理  
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {  
                        m.put("F"+(colIx+1), cell.getDateCellValue()+"");
                    } else {   
                        m.put("F"+(colIx+1), cellValue.getNumberValue()+"");
                    }  
                    break;  
                case Cell.CELL_TYPE_STRING:  
                     Pattern p = Pattern.compile("\t|\r|\n");
                     Matcher matcher = p.matcher(cellValue.getStringValue()+"");
                    m.put("F"+(colIx+1), matcher.replaceAll(""));
                    break;  
                case Cell.CELL_TYPE_FORMULA:  
                    break;  
                case Cell.CELL_TYPE_BLANK:  
                    break;  
                case Cell.CELL_TYPE_ERROR:  
                    break;  
                }  
            }  
            list.add(m);  
        }  
        return list;  
    }  
    
    /**
     * 导出excel
     * @param response
     * @param fileName
     * @param data 
     * @throws Exception 
     */
    public  final static void exportExcelByAnnotation(HttpServletResponse response,  String fileName, List<?> data, String type) throws Exception  {  
        if(data==null||data.size()==0)
            throw new Exception("导出的数据为空");
        Object entity=data.get(0);
        Method[] allMethods=entity.getClass().getMethods();
        //过滤
        List<Method> methods=new ArrayList<>();
        for(Method m:allMethods){
            if(m.isAnnotationPresent(ExcelColumn.class)){
            	if(null == type || "".equalsIgnoreCase(type.trim())){
            		methods.add(m);
            	}else{
            		ExcelColumn column1 = m.getAnnotation(ExcelColumn.class);
            		if(type.equalsIgnoreCase(column1.type())){
            			methods.add(m);
            		}
            	}
            }
        }
        if(methods.size()==0)throw new Exception("未指定要导出的数据列");
        //按编号排序
        Collections.sort(methods,new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {              
                ExcelColumn column1=o1.getAnnotation(ExcelColumn.class);
                ExcelColumn column2=o2.getAnnotation(ExcelColumn.class);
                int flag =column1.order()-column2.order();
                if(flag>0){
                    return 1;
                }else if(flag<0){
                    return -1;
                }else{
                    return o1.getName().compareTo(o2.getName());
                }
                
            }
        });
        if(fileName==null||fileName.trim().length()==0){
            fileName=new Date().getTime()+"";
        }
        
        //fileName = URLEncoder.encode(fileName,"UTF8");//ie浏览器
        
        // 以下开始输出到EXCEL  
        fileName=new String(fileName.getBytes("gb2312"),"ISO-8859-1");
          //定义输出流，以便打开保存对话框______________________begin 
          OutputStream os = response.getOutputStream();// 取得输出流        
          response.reset();// 清空输出流        
          response.setHeader("Content-disposition", "attachment; filename="+ fileName+".xls");  // 设定输出文件头        
          response.setContentType("application/msexcel");// 定义输出类型      
          //定义输出流，以便打开保存对话框_______________________end
          HSSFWorkbook  workbook=new HSSFWorkbook();
         Sheet sheet= workbook.createSheet("sheet1");
        //生成头部标题
        Row header=sheet.createRow(0);
        int cellindex=0;
        for(Method method:methods){
            if(method.isAnnotationPresent(ExcelColumn.class)){
            ExcelColumn export=method.getAnnotation(ExcelColumn.class);            
            Cell headerCell=header.createCell(cellindex);
            headerCell.setCellValue(export.name());
            cellindex++;
           }
        }
        //生城数据行
        int rowindex=1;
        for( Object o:data){
            Row sheetRow=sheet.createRow(rowindex);
             cellindex=0;
             for(Method method:methods){
                 if(method.isAnnotationPresent(ExcelColumn.class)){
                    Object cellvalue=method.invoke(o);
                    Cell bodyCell=sheetRow.createCell(cellindex);
                    if(cellvalue!=null){
                        ExcelColumn export=method.getAnnotation(ExcelColumn.class); 
                        if (cellvalue instanceof Date)  //日期数据
                        {  
                            Date date = (Date) cellvalue;  
                           if( export.dateFormat().length()>0){//自定义格式化
                               SimpleDateFormat sdf = new SimpleDateFormat(export.dateFormat());  
                               cellvalue = sdf.format(date);
                               bodyCell.setCellValue(cellvalue.toString());
                           }else{
                               bodyCell.setCellValue(date);
                           }
                          
                        }else if(cellvalue instanceof Boolean) {
                            boolean b=(boolean)cellvalue;
                            bodyCell.setCellValue(b);
                        }else if(cellvalue instanceof Number){
                        	bodyCell.setCellValue(Double.parseDouble(cellvalue.toString()));
                        }else{
                            bodyCell.setCellValue(cellvalue.toString());
                        }
                       
                    }
                    cellindex++;
                 }
                 
             }
             rowindex++;
        }
        workbook.write(os);
        os.flush();
        os.close();
    }  
    
    /**
     * 导出excel 2007版本
     * @param response
     * @param fileName
     * @param data 
     * @throws Exception 
     */
    public  final static void exportExcelByAnnotation2007(HttpServletResponse response,  String fileName, List<?> data, String type) throws Exception  {  
        if(data==null||data.size()==0)
            throw new Exception("导出的数据为空");
        Object entity=data.get(0);
        Method[] allMethods=entity.getClass().getMethods();
        //过滤
        List<Method> methods=new ArrayList<>();
        for(Method m:allMethods){
            if(m.isAnnotationPresent(ExcelColumn.class)){
            	if(null == type || "".equalsIgnoreCase(type.trim())){
            		methods.add(m);
            	}else{
            		ExcelColumn column1 = m.getAnnotation(ExcelColumn.class);
            		if(type.equalsIgnoreCase(column1.type())){
            			methods.add(m);
            		}
            	}
            }
        }
        if(methods.size()==0)throw new Exception("未指定要导出的数据列");
        //按编号排序
        Collections.sort(methods,new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {              
                ExcelColumn column1=o1.getAnnotation(ExcelColumn.class);
                ExcelColumn column2=o2.getAnnotation(ExcelColumn.class);
                int flag =column1.order()-column2.order();
                if(flag>0){
                    return 1;
                }else if(flag<0){
                    return -1;
                }else{
                    return o1.getName().compareTo(o2.getName());
                }
                
            }
        });
        if(fileName==null||fileName.trim().length()==0){
            fileName=new Date().getTime()+"";
        }
        
        //fileName = URLEncoder.encode(fileName,"UTF8");//ie浏览器
        
        // 以下开始输出到EXCEL  
        fileName=new String(fileName.getBytes("gb2312"),"ISO-8859-1");
          //定义输出流，以便打开保存对话框______________________begin 
          OutputStream os = response.getOutputStream();// 取得输出流        
          response.reset();// 清空输出流        
          response.setHeader("Content-disposition", "attachment; filename="+ fileName+".xlsx");  // 设定输出文件头        
          response.setContentType("application/msexcel");// 定义输出类型      
          //定义输出流，以便打开保存对话框_______________________end
          XSSFWorkbook  workbook=new XSSFWorkbook();
          Sheet  sheet= workbook.createSheet("sheet1");
        //生成头部标题
          Row  header=sheet.createRow(0);
        int cellindex=0;
        for(Method method:methods){
            if(method.isAnnotationPresent(ExcelColumn.class)){
            ExcelColumn export=method.getAnnotation(ExcelColumn.class);            
            Cell headerCell=header.createCell(cellindex);
            headerCell.setCellValue(export.name());
            cellindex++;
           }
        }
        //生城数据行
        int rowindex=1;
        for( Object o:data){
        	Row  sheetRow=sheet.createRow(rowindex);
             cellindex=0;
             for(Method method:methods){
                 if(method.isAnnotationPresent(ExcelColumn.class)){
                    Object cellvalue=method.invoke(o);
                    Cell  bodyCell=sheetRow.createCell(cellindex);
                    if(cellvalue!=null){
                        ExcelColumn export=method.getAnnotation(ExcelColumn.class); 
                        if (cellvalue instanceof Date)  //日期数据
                        {  
                            Date date = (Date) cellvalue;  
                           if( export.dateFormat().length()>0){//自定义格式化
                               SimpleDateFormat sdf = new SimpleDateFormat(export.dateFormat());  
                               cellvalue = sdf.format(date);
                               bodyCell.setCellValue(cellvalue.toString());
                           }else{
                               bodyCell.setCellValue(date);
                           }
                          
                        }else if(cellvalue instanceof Boolean) {
                            boolean b=(boolean)cellvalue;
                            bodyCell.setCellValue(b);
                        }else if(cellvalue instanceof Number){
                        	bodyCell.setCellValue(Double.parseDouble(cellvalue.toString()));
                        }else{
                            bodyCell.setCellValue(cellvalue.toString());
                        }
                       
                    }
                    cellindex++;
                 }
                 
             }
             rowindex++;
        }
        workbook.write(os);
        workbook.close();
        os.flush();
        os.close();
    }  
}
