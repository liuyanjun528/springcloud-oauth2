package springcloud.outh2.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import springcloud.outh2.project.web.config.MyTokenExceptionEntryPoint;

/**
 * @author Sir_小三
 * @date 2020/1/31--11:39
 *
 * 资源服务需要做什么事情呢？验证令牌，那些请求不需要令牌访问等
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启对方法权限控制的配置
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {
    //资源服务若配置资源id，那么资源服务去验证令牌的时候，jwt令牌中的资源id要与之对应，否则验证令牌不通过，405错误
    //感觉如果对资源的保护划分的不是太细，可以不配置。。。

    public static final String RESOURCE_ID = "res1";

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private MyTokenExceptionEntryPoint myTokenExceptionEntryPoint;

    /**
     * 验证是否携带令牌，或者令牌是否被篡改
     * 资源服务设置tokenStore，服务自己验证 ，如果使用对称加密，使用相同密钥
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);//资源id
        resources.tokenStore(tokenStore);
        resources.authenticationEntryPoint(myTokenExceptionEntryPoint);//自定义异常返回，令牌错误，或者无令牌
    }

    /**
     *  各服务具体哪些路径不需要令牌或者需要令牌，交由服务自己配置
     * /get/uUnit
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/test/**").permitAll()
                //.antMatchers("/financing//get/uUnit").permitAll()
                //本地swagger测试，放开拦截，便于测试，线上在做拦截
                .antMatchers("/order").permitAll()
                .antMatchers("/rabbitMQ").permitAll()
                .antMatchers("/**").authenticated()
                .and()//解决跨域必须配置，否则前端报跨域错误
                .cors();

    }


}
