package springcloud.outh2.project.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 其实以前都是继承WebMvcConfigurerAdapter类 不过springBoot2.0以上 WebMvcConfigurerAdapter 方法过时
 * 从源码上可以看出，WebMvcConfigurerAdapter类对WebMvcConfigurer接口进行了实现，不过都是空实现。
 * ，java版本1.8 接口可以定义defult方法，那么WebMvcConfigurerAdapter这个适配类也就没有意义了，所以被抛弃
 * 有两种替代方案：
 * 1、继承WebMvcConfigurationSupport
 * 2、实现WebMvcConfigurer
 * 注意！！！
 * 但是继承WebMvcConfigurationSupport会让Spring-boot对mvc的自动配置失效。
 * 比如说默认静态资源路径的访问会失效，以及swagger的映射路径，等等（继承需要自己重新做静态资源路径的配置）
 * 根据项目情况选择。现在大多数项目是前后端分离，
 * 并没有对静态资源有自动配置的需求所以继承WebMvcConfigurationSupport也未尝不可。
 *
 * @author 刘彦军
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private TokenHandlerInterceptor tokenHandlerInterceptor;
    // 继承WebMvcConfigurerAdapter类，注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 传入自定义的拦截器
        registry.addInterceptor(tokenHandlerInterceptor)
                // 拦截所有的请求
                .addPathPatterns("/**");
    }
}
