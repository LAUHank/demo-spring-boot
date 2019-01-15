package cn.lhl.test.action;

import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.misc.BASE64Encoder;
import cn.lhl.util.HttpUtil;

/**
 * Created by Administrator on 2018/8/16.
 */
@RequestMapping("/test")
@Controller
public class TestCotroller {
	
	private static final Logger LOGGER = Logger.getLogger(TestCotroller.class);
	
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test/test";
    }
    
    @RequestMapping(value = "/test2")
    public void test2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	// http://127.0.0.1:8180/test/test2?httpPath=0A1B&httpFileName=2019.doc
    	// http://127.0.0.1:8180/test/test2?httpPath=&httpFileName=file.doc
    	
		String httpPath = request.getParameter("httpPath");
		String httpFileName = request.getParameter("httpFileName");
		httpPath = (httpPath == null ? "" : httpPath.trim());
		httpFileName = (httpFileName == null ? "" : httpFileName.trim());
		
		String fileName = new String(httpFileName);
		String agent = request.getHeader("User-Agent");
        if (agent.contains("Firefox")) { // 火狐浏览器
            fileName = "=?UTF-8?B?" + new BASE64Encoder().encode(fileName.getBytes("utf-8")) + "?=";
        } else { // IE及其他浏览器
            fileName = URLEncoder.encode(fileName, "utf-8");
        }
        // 告诉浏览器这是一个下载文件的servlet
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        
        ServletOutputStream out = response.getOutputStream();
        
        try {
    		// HttpUtil hu = new HttpUtil("img.lhl.cn", 80, "lhl", "123");
    		HttpUtil hu = new HttpUtil();
    		hu.downloadFile(httpPath, httpFileName, out);
    	} catch (Exception e) {
    		LOGGER.error("异常", e);
    	}
    }
}
