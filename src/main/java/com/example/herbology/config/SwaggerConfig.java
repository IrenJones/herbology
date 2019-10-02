package com.example.herbology.config;

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

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.herbology.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInformation());
//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, getCustomizedResponseMessages());
    }

    private ApiInfo getApiInformation(){
        return new ApiInfo("Harry Potter REST API",
                "This is a Demo API created using Spring Boot",
                "1.0",
                "API Terms of Service URL",
                new Contact("Harry", "-", "-"),
                "API License",
                "API License URL",
                Collections.emptyList()
        );
    }

//    private List<ResponseMessage> getCustomizedResponseMessages(){
//        List<ResponseMessage> responseMessages = new ArrayList<>();
//        responseMessages.add(new ResponseMessageBuilder().code(500).message("Server has crashed!!")
//                .responseModel(new ModelRef("Error")).build());
//        responseMessages.add(new ResponseMessageBuilder().code(403).message("You shall not pass!!").build());
//        return responseMessages;
//    }
}
