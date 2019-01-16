package cn.lhl.app;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@ComponentScan(basePackages = { "cn.lhl" })
@EnableAutoConfiguration
public class MyApp {
	public static void main(String[] args) {
		SpringApplication.run(MyApp.class, args);
	}

	/**
	 * 配置上传文件大小的配置
	 * 
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 单个数据大小
		factory.setMaxFileSize("102400KB");
		// / 总上传数据大小
		factory.setMaxRequestSize("102400KB");
		return factory.createMultipartConfig();
	}

	@Bean(name = "multipartResolver")
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
		resolver.setResolveLazily(true);
		resolver.setMaxInMemorySize(40960);
		// 上传文件大小 50M 50*1024*1024
		resolver.setMaxUploadSize(50 * 1024 * 1024);
		return resolver;
	}
}
