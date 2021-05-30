package com.hunza.event.caterer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ishaan.solanki
 * <p>
 * SwaggerConfig class is responsible for swagger configurations
 * <p>To access swagger-ui 'localhost:8088/swagger-ui'</p>
 * <p>It is responsible to hold the metadata against the swagger configuration object</p>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    /**
     * Docket, Springfox’s, primary api configuration mechanism is initialized for swagger specification 2.0
     * select() returns an instance of ApiSelectorBuilder to give fine grained control over the endpoints exposed via swagger
     * apis() allows selection of RequestHandler's using a predicate. The example here uses an any predicate (default). Out of the box predicates provided are any, none, withClassAnnotation, withMethodAnnotation and basePackage
     * paths() allows selection of Path's using a predicate. The example here uses an any predicate (default). Out of the box we provide predicates for regex, ant, any, none.
     * Configures with api operations and api selection builder. To complete building the api select.
     *
     * @return this Docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hunza.event.caterer"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    /**
     * It holds the caterer service api metadata information.
     *
     * @return {@link ApiInfo}
     */
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Caterer Application",
                "An assignment for Hunza Consulting",
                "V1",
                "Terms of service",
                "ishaansolanki6@gmail.com",
                "License of API",
                "https://swagger.io/docs/");
        return apiInfo;
    }
}