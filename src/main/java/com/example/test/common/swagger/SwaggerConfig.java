package com.example.test.common.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


	Contact contact = new Contact("ABC School", "https://www.abc.com/", "webd.abhi@gmail.com");

	@SuppressWarnings("rawtypes")
	List<VendorExtension> vendorExtensions = new ArrayList<>();

	ApiInfo apiInfo = new ApiInfo("API for School Management System",
			"SMS API", "1.0", "https://www.abc.com/", contact,
			"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", vendorExtensions);

	@Bean
	public Docket apiDocket() {

		return new Docket(DocumentationType.SWAGGER_2)
				.protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPS")))
				.apiInfo(apiInfo).select().apis(RequestHandlerSelectors
				.basePackage("com.example.test"))
				.paths(PathSelectors.any())
				.build();
	}

}
