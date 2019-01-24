package com.drore.tdp.swagger;

import org.springframework.beans.factory.annotation.Value;
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

/**
 * 描述:
 * 项目名:my-shop-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/14  16:59.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value(value = "${swagger.title}")
    private String title;
    @Value(value = "${swagger.description}")
    private String description;
    @Value(value = "${swagger.version}")
    private String version;
    @Value(value = "${swagger.contact.name}")
    private String contactName;
    @Value(value = "${swagger.contact.url}")
    private String contactUrl;
    @Value(value = "${swagger.contact.email}")
    private String contactEmail;
    @Value(value = "${swagger.base-package}")
    private String basePackage;
    @Value(value = "${swagger.terms-of-service-url}")
    private String termsOfServiceUrl;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(contactName, contactUrl, contactEmail);
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(contact)
                .version(version)
                .build();
    }
}
