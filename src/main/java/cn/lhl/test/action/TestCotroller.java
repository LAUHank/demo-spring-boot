package cn.lhl.test.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import sun.misc.BASE64Encoder;
import cn.lhl.test.domain.TestDemo;
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
	public void test2(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// http://127.0.0.1:8180/test/test2?httpPath=0A1B&httpFileName=2019.doc
		// http://127.0.0.1:8180/test/test2?httpPath=&httpFileName=file.doc

		String httpPath = request.getParameter("httpPath");
		String httpFileName = request.getParameter("httpFileName");
		httpPath = (httpPath == null ? "" : httpPath.trim());
		httpFileName = (httpFileName == null ? "" : httpFileName.trim());

		String fileName = new String(httpFileName);
		String agent = request.getHeader("User-Agent");
		if (agent.contains("Firefox")) { // 火狐浏览器
			fileName = "=?UTF-8?B?"
					+ new BASE64Encoder().encode(fileName.getBytes("utf-8"))
					+ "?=";
		} else { // IE及其他浏览器
			fileName = URLEncoder.encode(fileName, "utf-8");
		}
		// 告诉浏览器这是一个下载文件的servlet
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName);

		ServletOutputStream out = response.getOutputStream();

		try {
			// HttpUtil hu = new HttpUtil("img.lhl.cn", 80, "lhl", "123");
			HttpUtil hu = new HttpUtil();
			hu.downloadFile(httpPath, httpFileName, out);
		} catch (Exception e) {
			LOGGER.error("异常", e);
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String uploadView() {
		// http://127.0.0.1:8180/test/upload
		return "test/upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, String note, TestDemo testDemo) throws Exception {
		
		LOGGER.info(note);
		LOGGER.info(testDemo);
		
		long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 设置允许上传的单个文件大小上限 默认是  1048576 bytes
		// multipartResolver.setMaxUploadSize(104857600);
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			
			// String note = multiRequest.getParameter("note");
			// LOGGER.info(note);
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				String fns = iter.next();
				LOGGER.info(""+fns);
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(fns);
				if (file != null && !file.isEmpty()) {
					String prefixDir = Paths.get("C:", "Users", "Administrator", "Desktop", "temp").toString();
					String mediumDir = "0A1B/2C";
					String baseDir = Paths.get(prefixDir, mediumDir).toString();
					File f = new File(baseDir);
					if (!f.exists()) {
						f.mkdirs();
					}
					String localFileName = "upload_"+Math.random()+"_"+file.getOriginalFilename();
					String path = Paths.get(baseDir, localFileName).toString();
					// 上传
					file.transferTo(new File(path));
					LOGGER.info("保存成功, 路径是[" + path + "]");
				}

			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("运行时间：" + String.valueOf(endTime - startTime) + "ms");
			return "test/ok";
		} else {
			long endTime = System.currentTimeMillis();
			LOGGER.info("运行时间：" + String.valueOf(endTime - startTime) + "ms");
			return "test/failure";
		}

	}
	
	@RequestMapping(value = "/download")
	public void download(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// http://127.0.0.1:8180/test/download?prefixPath=D:/&savePath=/home/lhl/2019.doc&fileName=2019中文4.doc

		String savePath = request.getParameter("savePath"); // 附件保存在服务器上的相对路径
		savePath = (savePath == null ? "" : savePath.trim());
		
		String prefixPath = request.getParameter("prefixPath"); // 高哥给提供的前面部分, 现在没给, 先默认是 D:/ (Windows) 或者 / (Linux) 
		prefixPath = (prefixPath == null ? "" : prefixPath.trim());
		
		String filePath = prefixPath + "/" + savePath; // 附件保存在服务器上的真实绝对路径
		
		String fileName = request.getParameter("fileName"); // 附件原来的中文名
		fileName = (fileName == null ? "" : fileName.trim());

		String agent = request.getHeader("User-Agent");
		if (agent.contains("Firefox")) { // 火狐浏览器
			fileName = "=?UTF-8?B?"
					+ new BASE64Encoder().encode(fileName.getBytes("utf-8"))
					+ "?=";
		} else { // IE及其他浏览器
			fileName = URLEncoder.encode(fileName, "utf-8");
		}
		// 告诉浏览器这是一个下载文件的servlet
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

		ServletOutputStream out = response.getOutputStream();

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
			byte[] b = new byte[bis.available()];
			int len = -1;
			while ((len = bis.read(b)) != -1) {
				out.write(b, 0, len);
			}
		} catch (Exception e) {
			LOGGER.error("异常", e);
		}
	}
}
