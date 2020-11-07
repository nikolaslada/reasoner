package cz.nikolaslada.reasoner.config;

import cz.nikolaslada.reasoner.ReasonerApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Reasoner REST API")
                .description("Reasoner is engine for OntoWeb project. It provides Rest API for back-office application and for client queries.")
                .termsOfServiceUrl("Use at your own risk.")
                .license("Ⓒ Nikolas Lada")
                .licenseUrl("/")
                .version(ReasonerApplication.class.getPackage().getImplementationVersion())
                .build();
    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("reasoner-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cz.nikolaslada.reasoner.rest"))
                .apis(RequestHandlerSelectors.basePackage("cz.nikolaslada.reasoner.rest.swagger").negate())
                .build()
                ;
    }
}
