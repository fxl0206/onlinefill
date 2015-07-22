package org.online.util;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.text.DecimalFormat;
    import java.util.ArrayList;
import java.util.Iterator;
    import java.util.List;
    import org.apache.poi.hssf.usermodel.HSSFDateUtil;
    import org.apache.poi.hssf.usermodel.HSSFWorkbook;
    import org.apache.poi.ss.usermodel.Cell;
    import org.apache.poi.ss.usermodel.Row;
    import org.apache.poi.ss.usermodel.Sheet;
    import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
    /** *//**
    * 
    * 
    Title:[POI基础上的Excel数据读取工具]
    * 
    Description: [支持Excell2003,Excell2007,自动格式化数值型数据,自动格式化日期型数据]
    * 
    Copyright 2009 RoadWay Co., Ltd.
    * 
    All right reserved.
    * 
    Created by [惠万鹏] [Jan 20, 2010]
    * 
    Midified by [modifier] [modified time]
    * 
    * 
    所需Jar包列表
    * 
    poi-3.6-20091214.jar
    * 
    poi-contrib-3.6-20091214.jar
    * 
    poi-examples-3.6-20091214.jar
    * 
    poi-ooxml-3.6-20091214.jar
    * 
    poi-ooxml-schemas-3.6-20091214.jar
    * 
    poi-scratchpad-3.6-20091214.jar
    * 
    xmlbeans-2.3.0.jar
    * 
    * 
    * @version 1.0
    */
    public class POIExcelUtil
    {
    /** *//** 总行数 */
    private int totalRows = 0;
    /** *//** 总列数 */
    private int totalCells = 0;
    /** *//** 构造方法 */
    public POIExcelUtil()
    {}
    /** *//**
    * 
    * 
    Description:[根据文件名读取excel文件]
    * 
    Created by [Huyvanpull] [Jan 20, 2010]
    * 
    Midified by [modifier] [modified time]
    * 
    * 
    * @param fileName
    * @return
    * @throws Exception
    */
    public List read(File dbfile,String fileName)
    {
    List dataLst = new ArrayList();
    /** *//** 检查文件名是否为空或者是否是Excel格式的文件 */
    if (fileName == null || !fileName.matches("^.+\\.(?i)((xls)|(xlsx))$"))
    {
    return dataLst;
    }
    boolean isExcel2003 = true;
    /** *//** 对文件的合法性进行验证 */
    if (fileName.matches("^.+\\.(?i)(xlsx)$"))
    {
    isExcel2003 = false;
    }
    /** *//** 检查文件是否存在 */
    File file =dbfile;
    if (file == null || !file.exists())
    {
    return dataLst;
    }
    try
    {
    /** *//** 调用本类提供的根据流读取的方法 */
    dataLst = read(new FileInputStream(file), isExcel2003);
    }
    catch (Exception ex)
    {
    ex.printStackTrace();
    }
    /** *//** 返回最后读取的结果 */
    return dataLst;
    }
    /** *//**
    * 
    * 
    Description:[根据流读取Excel文件]
    * 
    Created by [Huyvanpull] [Jan 20, 2010]
    * 
    Midified by [modifier] [modified time]
    * 
    * 
    * @param inputStream
    * @param isExcel2003
    * @return
    */
    public List read(InputStream inputStream,
    boolean isExcel2003)
    {
    List dataLst = null;
    try
    {
    /** *//** 根据版本选择创建Workbook的方式 */
    Workbook wb = isExcel2003 ? new HSSFWorkbook(inputStream)
    : new XSSFWorkbook(inputStream);
    dataLst = read(wb);
    }
    catch (IOException e)
    {
    e.printStackTrace();
    }
    return dataLst;
    }
    /** *//**
    * 
    * 
    Description:[得到总行数]
    * 
    Created by [Huyvanpull] [Jan 20, 2010]
    * 
    Midified by [modifier] [modified time]
    * 
    * 
    * @return
    */
    public int getTotalRows()
    {
    return totalRows;
    }
    /** *//**
    * 
    * 
    Description:[得到总列数]
    * 
    Created by [Huyvanpull] [Jan 20, 2010]
    * 
    Midified by [modifier] [modified time]
    * 
    * 
    * @return
    */
    public int getTotalCells()
    {
    return totalCells;
    }
    /** *//**
    * 
    * 
    Description:[读取数据]
    * 
    Created by [Huyvanpull] [Jan 20, 2010]
    * 
    Midified by [modifier] [modified time]
    * 
    * 
    * @param wb
    * @return
    */
    private List read(Workbook wb)
    {
    boolean flag=true;
    int realdataCount=0;
    List dataLst = new ArrayList();
    /** *//** 得到第一个shell */
    Sheet sheet = wb.getSheetAt(0);
    this.totalRows = sheet.getPhysicalNumberOfRows();
System.out.println(this.totalRows);
    if (this.totalRows >= 1 && sheet.getRow(0) != null)
    {
    this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
    }
    /** *//** 循环Excel的行 */
    for (int r = 0; r < this.totalRows; r++)
    {
    Row row = sheet.getRow(r);
    if (row == null)
    {
    continue;
    }
    ArrayList rowLst = new ArrayList();
    /** *//** 循环Excel的列 */
    
    for (short c = 0; c < this.getTotalCells(); c++)
    {
    	
    Cell cell = row.getCell(c);
    String cellValue = "";
    if (cell == null)
    {
    rowLst.add(cellValue);
    continue;
    }
    /** *//** 处理数字型的,自动去零 */
    if (Cell.CELL_TYPE_NUMERIC == cell.getCellType())
    {
    /** *//** 在excel里,日期也是数字,在此要进行判断 */
    if (HSSFDateUtil.isCellDateFormatted(cell))
    {
    //cellValue = DateUtil.get4yMdHms(cell.getDateCellValue());
    }
    else
    {
    cellValue = getRightStr(cell.getNumericCellValue() + "");
    }
    }
    /** *//** 处理字符串型 */
    else if (Cell.CELL_TYPE_STRING == cell.getCellType())
    {
    cellValue = cell.getStringCellValue();
    }
    /** *//** 处理布尔型 */
    else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType())
    {
    cellValue = cell.getBooleanCellValue() + "";
    }
    /** *//** 其它的,非以上几种数据类型 */
    else
    {
    cellValue = cell.toString() + "";
    }
    rowLst.add(cellValue);
    }
    dataLst.add(rowLst);
    }
    return dataLst;
    }
    /** *//**
    * 
    * 
    Description:[正确地处理整数后自动加零的情况]
    * 
    Created by [Huyvanpull] [Jan 20, 2010]
    * 
    Midified by [modifier] [modified time]
    * 
    * 
    * @param sNum
    * @return
    */
    private String getRightStr(String sNum)
    {
    DecimalFormat decimalFormat = new DecimalFormat("#.000000");
    String resultStr = decimalFormat.format(new Double(sNum));
    if (resultStr.matches("^[-+]?\\d+\\.[0]+$"))
    {
    resultStr = resultStr.substring(0, resultStr.indexOf("."));
    }
    return resultStr;
    }
    /** *//**
    * 
    * 
    Description:[测试main方法]
    * 
    Created by [Huyvanpull] [Jan 20, 2010]
    * 
    Midified by [modifier] [modified time]
    * 
    * 
    * @param args
    * @throws Exception
    */
    
    }