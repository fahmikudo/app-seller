package id.web.fahmikudo.test.appseller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@Configuration
@EnableSwagger2
public class AppSellerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSellerApplication.class, args);
	}


	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Aplikasi Seller",
				"API Seller",
				"V1.0.0",
				"Terms of service",
				new Contact("Fahmi Kudo", "www.fahmikudo.web.id", "fahmi.hidayatullah12@gmail.com"),
				"Apache License 2.0", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
	}




}

