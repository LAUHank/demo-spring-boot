package cn.lhl.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"cn.lhl"})
@EnableAutoConfiguration
public class MyApp {
	public static void main(String[] args) {
		SpringApplication.run(MyApp.class, args);
	}
}
