package com.ryit.commons.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    // 定义分隔符
    private static final String splitor = ";";

    /**
     * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("createRestApi")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .select()
                //此处添加需要扫描接口的包路径
                .apis(basePackage(new String[]{
                        "com.ryit.goods.controller",
                        "com.ryit.order.controller",
                        "com.ryit.security.controller",
                        "com.ryit.users.controller",
                        "com.ryit.talk.controller",
                        "com.ryit.message.controller"
                }))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     */
    private ApiInfo apiInfo() {
        ApiInfo build = new ApiInfoBuilder()
                .title("拼钢APP商城系统")
                .description("拼钢APP商城系统接口文档说明")
                .contact(new Contact("@软艺信息", "http://www.baidu.com", "670610973@qq.com"))
                .version("1.0")
                .build();
        return build;
    }

    /**
     * 重写basePackage方法，使能够实现多包访问
     *
     * @param
     * @author jinhaoxun
     * @since 2019/1/26
     */
    public static Predicate<RequestHandler> basePackage(final String[] basePackages) {
        String basePackage = StringUtils.join(basePackages, splitor);
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}
