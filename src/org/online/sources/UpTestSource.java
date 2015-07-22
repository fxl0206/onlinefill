package org.online.sources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.core.header.FormDataContentDisposition;

@Path("/up")
public class UpTestSource {
	@POST
	@Path("/upload")
	@Produces("text/html")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public JSONObject upload(
			@FormParam("filepath") FormDataContentDisposition fileName,
			@FormParam("filepath") File file,
			@FormParam("fileName") String filename,
			@Context ServletContext context)
			throws Exception {
		JSONObject jsobj = new JSONObject();
		String realPath=context.getRealPath("/upload/");
		System.out.println(filename);
//		System.out.println(file.getAbsolutePath());
//		java.util.StringTokenizer   a   =   new   java.util.StringTokenizer(fileName.getFileName(), "."); 
//		String   str=   null; 
//		while   (a.hasMoreTokens()) 
//		{ 
//		        str   =   a.nextToken(); 
//		} 
//		filename+="."+str;
//		copyUpload(realPath,filename,file);
//		List dataList=new POIExcelUtil().read("D:/测试.xls");
//		Iterator its=dataList.iterator();
//		while(its.hasNext())
//		{
//			//System.out.println(its.next());
//			List itts=(List) its.next();
//			Iterator isst=itts.iterator();
//			while(isst.hasNext())
//			{
//				System.out.println(isst.next());
//			}
//			break;
//		}
		jsobj.put("success","true");
		jsobj.put("msg","上传成功");
		return jsobj;
	}

	public void copyUpload(String realPath, String fileName, File fupload)
			throws Exception {
		FileOutputStream fos = new FileOutputStream(realPath + "/" + fileName);
		FileInputStream fis = new FileInputStream(fupload);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();
	}
}
