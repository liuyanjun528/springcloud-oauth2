package springcloud.outh2.project.authentication.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Sir_小三
 * @date 2020/1/30--13:01
 * AuthorizationServer认证服务配置  需要继承WebSecurityConfigurerAdapter类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//使用密码模式，必须要配置，测试发现不配也行。。。。懵逼）
	@Autowired
	private UserServiceDetail userServiceDetail;

	/**
	 * 	//配置认证管理器（使用密码模式，必须要配置）
	 * 	注意！！！这里重写父类的authenticationManagerBean方法，方法名写错，栈溢出error，不能粗心
	 * @return
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}

	//密码编码器
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/**
	 * 认证用户名密码
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userServiceDetail).passwordEncoder(passwordEncoder());
	}

	/**
	 * 解决方案是将OAuth2AuthenticationProcessingFilter仅映射到资源服务器的路径，而不是其他路径。
	 * 注意不要在这个位置映射controller的路径，否则将无法认证，发放令牌
	 * @param web
	 * @throws Exception
	 */
	@Override
	public void configure(WebSecurity web){
		web.ignoring().antMatchers("/userlogin","/userlogout","/userjwt","/v2/api-docs", "/swagger-resources/configuration/ui",
				"/swagger-resources","/swagger-resources/configuration/security",
				"/swagger-ui.html","/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/index");

	}

	/**
	 * 	认证服务器需要登陆，所以放开登陆接口，其他请求全部拦截
	 * @param http
	 * @throws Exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/**").authenticated()
				.and()//解决跨域
				.cors()
				.and()
				.csrf().disable();
	}

}
