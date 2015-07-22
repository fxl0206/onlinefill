package Junit4.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.online.model.Tablelist;
import org.online.services.TablelistService;
import org.online.util.POIExcelUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class ExcelTest {
@Test
public void readExcel()
{
	ApplicationContext ctx=new ClassPathXmlApplicationContext("config.xml");
	TablelistService orgdao=(TablelistService)ctx.getBean("tablelistService");
	File file=new File("C:/TEMP/v2.xlsx");
	List dataList=new POIExcelUtil().read(file,"v2.xlsx");
	List ls=new ArrayList();
	//System.out.print("start");
	Iterator its=dataList.iterator();
	while(its.hasNext())
	{
		//System.out.println(its.next());
		ls=(ArrayList)its.next();
		for(int kk=0;kk<ls.size();kk++)
		{
			System.out.print(ls.get(kk)+"###");
		}
		System.out.println("");
	}
	//System.out.println(orgdao.test("tadirid","dir5",0));
	//orgdao.injectTest();
//	 try {   
//		    File file = new File("D:/test.xlsx");   
//		    //打开excel文件   
//		    Workbook book = Workbook.getWorkbook(file);   
//		    // 获得第一个工作表对象   
//		    Sheet sheet = book.getSheet(0);   
// 
//		    int collen=sheet.getColumns();  
//		    int rowlen=sheet.getRows();
//		    //得到三个单元格
//		    for(int ii=0;ii<rowlen;ii++)
//		    {
//		    	for( int jj=0;jj<collen;jj++)
//		    	System.out.print("\t"+sheet.getCell(jj,ii).getContents()+"\t");
//		    	System.out.println();
//		    }
//		    book.close();  
//		    file=null;
//		   } catch (Exception e) {   
//		    System.out.println(e);   
//		   }   

	
}
}
