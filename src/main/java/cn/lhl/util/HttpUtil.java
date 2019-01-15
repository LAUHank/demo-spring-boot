package cn.lhl.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

public class HttpUtil {
	
	private static final Logger LOGGER = Logger.getLogger(HttpUtil.class);
	private static final Properties PROP = new Properties();
	
	static {
    	String src = "http.properties";
    	try {
			PROP.load(new InputStreamReader(HttpUtil.class.getClassLoader().getResourceAsStream(src), "utf-8"));
			LOGGER.info("加载http配置文件 ["+src+"] 成功");
		} catch (Exception e) {
			LOGGER.error("加载http配置文件 ["+src+"] 失败", e);
		}
	}
	
    // http服务器地址
    private String hostname;
    // http服务器端口号 默认 80
    private Integer port;
    // http登录账号
    private String username;
    // http登录密码
    private String password;
    // http超时时间 默认5秒
    private Integer timeOut = 5 * 1000;
    
	public HttpUtil() {
		hostname = PROP.getProperty("hostname");
		port = Integer.valueOf(PROP.getProperty("port"));
		username = PROP.getProperty("username");
		password = PROP.getProperty("password");
		LOGGER.info("HttpUtil初始化成功 - 配置文件");
	}
	
	public HttpUtil(String hostname, Integer port, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		LOGGER.info("HttpUtil初始化成功 - 直接传参");
	}
    
    public void downloadFile(String httpPath, String httpFileName, OutputStream out) throws Exception {
    	URL url = null;
    	URLConnection urlConn = null;
		// 初始化 http 连接
    	// http://img.lhl.cn:80/0A1B/2019.doc
    	url = new URL("http://" + hostname + ":" + port + "/" + httpPath + "/" + httpFileName);
    	urlConn = url.openConnection();
    	
    	// 设置超时时间
    	urlConn.setConnectTimeout(timeOut);  
    	urlConn.setReadTimeout(timeOut); 
    	
    	// 设置用户名和密码
        String auth = username + ":" + password;
        
        // 对其进行转码
        String basicAuthStr = new BASE64Encoder().encode(auth.getBytes("utf-8"));

        // 设置 request header
        urlConn.setRequestProperty("Authorization","Basic " + basicAuthStr);
        
        //开始连接
        urlConn.connect();
        
        try (BufferedOutputStream bos = new BufferedOutputStream(out);
        		BufferedInputStream  bis = new BufferedInputStream(urlConn.getInputStream());) {
        	byte[] buf = new byte[1024];
            int len = -1;
            while ((len = bis.read(buf)) != -1) {
            	bos.write(buf, 0, len);
            }
        }
        
        LOGGER.info("["+httpFileName+"]下载成功");
    }
    
    public static void main(String[] args) {
    	String httpPath = ""; // "0A1B";
    	String httpFileName = "file.doc"; //"2019.doc";
    	
		String localPath = Paths.get("C:", "Users", "Administrator", "Desktop", "temp").toString();
    	String localFileName = httpFileName + "_"+Math.random()+".doc";
    	
    	try (OutputStream out = new FileOutputStream(Paths.get(localPath, localFileName).toString());) {
    		HttpUtil hu = new HttpUtil("img.lhl.cn", 80, "lhl", "123");
    		// HttpUtil hu = new HttpUtil();
    		hu.downloadFile(httpPath, httpFileName, out);
    	} catch (Exception e) {
    		LOGGER.error("异常", e);
    	}
	}
}
