package com.drore.tdp.configutation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 项目名:my-shop-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/15  9:50.
 */
@Component
@Primary
@ComponentScan(basePackages = "com.drore.tdp")
@EnableSwagger2
class DocumentationConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List resource = new ArrayList();
        resource.add(swaggerResource("tdp-api接口文档", "/tdp-api-server/v2/api-docs", "1.0"));
        return resource;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}

