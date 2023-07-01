package com.bchengchat.common.config;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.StrPool;
import com.bchengchat.common.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getUserDocket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Living")
                .version("1.0.0")
                .description("直播API接口")
                .build();
        ResultCode[] values = ResultCode.values();
        List<Response> responses = Stream.of(values)
                .collect(Collectors.groupingBy(ResultCode::getCode))
                .entrySet()
                .stream()
                .map(entry -> {
                    String messages = entry.getValue()
                            .stream()
                            .map(ResultCode::getMessage)
                            .collect(Collectors.joining(StrPool.COMMA));
                    return new Response(
                            entry.getKey().toString(),
                            messages, true,
                            ListUtil.empty(), ListUtil.empty(),
                            ListUtil.empty(), ListUtil.empty());
                }).collect(Collectors.toList());
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo)
                .globalResponses(HttpMethod.POST, responses)
                .globalResponses(HttpMethod.GET, responses)
                .globalResponses(HttpMethod.DELETE, responses)
                .globalResponses(HttpMethod.PUT, responses)
                /*.globalRequestParameters(new ArrayList<RequestParameter>() {{
                    add(new RequestParameterBuilder()
                            .name("token")
                            .in(ParameterType.HEADER)
                            .required(true)
                            .description("token")
                            .build());
                }})*/
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }
}
