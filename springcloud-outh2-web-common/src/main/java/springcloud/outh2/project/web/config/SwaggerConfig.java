package springcloud.outh2.project.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    ///swagger  有默认映射路径   swagger-ui.html  映射到  classpath:/META-INF/resources/  我们无需做配置
    public class Swagger2Config {
        @Value("${spring.application.name}")//动态读取项目名称
        private String projectName;
        @Value("${swagger.enable}")
        private boolean swaggerEnable;

        @Bean
        public Docket createRestfulApi() {// api文档实例

            return new Docket(DocumentationType.SWAGGER_2)// 文档类型：DocumentationType.SWAGGER_2
                    .enable(swaggerEnable)//配置false不会生成swaggerApi，生产环境中可以在配置文件中配置一个变量然后value读取，禁止生成api，以保证接口安全，不直接暴露出去
                    .apiInfo(apiInfo())// api信息
                    .select()// 构建api选择器     会扫描注解了又controller的类
                    .apis(RequestHandlerSelectors.basePackage("springcloud.outh2.project"))// api选择器选择api的包
                    .paths(PathSelectors.any())// api选择器选择包路径下任何api显示在文档中
                    .build()
                    .globalOperationParameters(getPrameterList());// 设置api 请求头
        }

        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    //页面标题   这里可以读取配置文件项目名称，动态显示不同项目的api
                    .title(projectName + " 服务 Swagger2  API接口文档")
                    //创建人
                    //版本号
                    .version("1.0")
                    //描述
                    .description("API 接口详情描述")
                    .build();
        }

        private List<Parameter> getPrameterList() {
            ParameterBuilder ticketPar = new ParameterBuilder();
            List<Parameter> pars = new ArrayList<>();
            ticketPar.name("Authorization").description("oAuth2 JWT ")
                    .modelRef(new ModelRef("string")).parameterType("header")
                    .required(false).build(); //header中的ticket参数非必填，传空也可以
            pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数
            return pars;
        }

    }
}
