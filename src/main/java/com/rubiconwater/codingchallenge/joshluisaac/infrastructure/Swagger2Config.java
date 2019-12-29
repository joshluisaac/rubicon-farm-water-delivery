package com.rubiconwater.codingchallenge.joshluisaac.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

  @Bean
  public Docket swaggerConfig() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .paths(PathSelectors.ant("/api/**"))
        .apis(RequestHandlerSelectors.basePackage("com.rubiconwater.codingchallenge.joshluisaac"))
        .build()
        .apiInfo(createApiInfo());
  }

  private ApiInfo createApiInfo() {
    return new ApiInfoBuilder()
        .contact(new Contact("Joshua Nwankwo", "", "nwankwo.joshua@gmail.com"))
        .title("Water Ordering and Delivery API")
        .description("Water Ordering and Delivery API")
        .build();
  }
}
