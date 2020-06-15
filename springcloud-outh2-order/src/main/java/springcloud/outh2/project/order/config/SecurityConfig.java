package springcloud.outh2.project.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @author Sir_小三
 * @date 2020/1/30--13:01
 * AuthorizationServer认证服务配置  需要继承WebSecurityConfigurerAdapter类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 解决方案是将OAuth2AuthenticationProcessingFilter仅映射到资源服务器的路径，而不是其他路径。
     *      * 注意不要在这个位置映射controller的路径，否则将无法认证，发放令牌
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/userlogin", "/userlogout", "/userjwt", "/v2/api-docs", "/swagger-resources/configuration/ui",
                "/swagger-resources", "/swagger-resources/configuration/security",
                "/swagger-ui.html", "/css/**", "/js/**", "/images/**", "/webjars/**", "**/favicon.ico", "/index");

    }

}
