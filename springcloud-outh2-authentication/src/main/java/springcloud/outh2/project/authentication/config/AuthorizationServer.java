package springcloud.outh2.project.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * @author Sir_小三
 * @date 2020/1/30--14:01
 * AuthorizationServer认证服务配置
 */
@Configuration
@EnableAuthorizationServer//开启认证服务/oauth/authorize,/oauth/token,/oauth/check_token,/oauth/confirm_access,/oauth/error
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    //密码模式认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    //使用密码模式，必须要配置）
//    @Autowired
//    private UserServiceDetail userServiceDetail;

    /**
     * 配置令牌访问端点得安全约束
     * 必须设置allowFormAuthenticationForClients 否则没有办法用postman获取token
     * 也需要指定密码加密方式BCryptPasswordEncoder
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()") //公开获取token的url
                .checkTokenAccess("permitAll()")//校验token的合法性
                .allowFormAuthenticationForClients();
        //.passwordEncoder(NoOpPasswordEncoder.getInstance());//不需要加密

    }

    /**
     * //配置客户端详情，客户端详情可以通过数据库查询，（校验哪些客户端可以申请令牌）
     * clientid 客户端id
     * secret 客户端安全码
     * scope 用来限制客户端得访问范围，默认为空，如果为空，那么客户端拥有全部得访问范围
     * authorizedGrantTypes  此客户端可以使用得授权类型，默认为空，oauth2提供了五种授权类型
     * authorities 此客户端可以使用 得权限  基于spring security authorities（一般不使用）
     *
     * @param clients
     * @throws Exception 这里客户端信息存在数据库
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //数据库存储方式
        clients.withClientDetails(clientDetailsService());//配置客户端信息
        //内存方式配置
//        clients.inMemory().withClient("clientId")//客户端id
//                .secret(passwordEncoder.encode("123456"))//客户端密钥，需要加密，使用数据库存，数据库的要经过加密处理
//                .resourceIds("res1")//资源id
//                .authorizedGrantTypes("authorization_code","refresh_token","password")//oauth2 四种授权类型，目前两种，授权码，密码模式
//                .scopes("all")
//                //.autoApprove(false)//授权码模式，false会跳转到授权页面，让用户授权，true不跳
//                //.redirectUris("http://www.baidu.com")//授权码模式，回调地址
//                .accessTokenValiditySeconds(7200);//客户端token有效两小时
    }

    /**
     * 配置令牌访问端点url，和令牌服务（令牌生成策略，如何生成）
     * /oauth/authorize   授权端点
     * 获取授权码
     * http://localhost:8762/oauth/authorize?response_type=code&client_id=resources-service&scope=all&client_secret=123456&redirect_uri=http://www.baidu.com
     * /oauth/token       获取令牌端点（发送post请求，获取token，code=申请的授权码）
     * <p>
     * /oauth/confirm_access  用户确认授权端点
     * /oauth/error  授权服务错误信息端点
     * /oauth/check_token  用于资源服务访问的令牌解析端点
     * /oauth/token_key  提供公有密钥的端点，如果使用jwt令牌的话
     * 授权端点url应该被spring-security 保护起来，只供授权用户访问
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//认证管理服务，密码模式必须配置，还有userServiceDetail
                .authorizationCodeServices(authorizationCodeServices(dataSource))//授权码模式(经测试,不配置不会将code自动存储到数据库)
                .tokenStore(tokenStore)
                //在这里设置jwtAccessTokenConverter，发现是ok的，可以生成jwt令牌
                .accessTokenConverter(jwtAccessTokenConverter)
                //.userDetailsService(userServiceDetail)//密码模式要配(测试发现不配也行。。。)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * @param dataSource
     * @return 设置授权码模式：授权码如何存取 采用数据库存储方式
     * 客户端通过get请求调用 例如下url：
     * http://localhost:8762/oauth/authorize?response_type=code&client_id=clientId&scope=all&client_secret=123456&redirect_uri=http://www.baidu.com
     * 通过此url来获取code，若用户未登录，跳转登陆页面，进行登陆，若登陆直接跳转至授权页面，进行授权
     * 点击授权，会跳转到指定页面，url后面会携带code，例如：https://www.baidu.com/?code=BTmxRo
     * 并且在这里code使用配置了数据库存储方式，调用此url，code也会自动被添加到数据库表中（oauth2自己提供的数据库表）
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }
    //内存方式存储授权码
//    @Bean
//    public AuthorizationCodeServices authorizationCodeServices(){
//        return new InMemoryAuthorizationCodeServices();
//    }

    /**
     * 客户端配置从数据库读取，发起授权时，会查询数据库数据进行认证，可添加n条客户端数据，获取验证码
     * 客户端拿到验证码，请求/oauth/token，获取令牌，
     * 注意！！！数据库中存储客户端密钥，需要使用BCryptPasswordEncoder加密后，在存，
     * 不然获取token的时候会报错（说不是BCryptPasswordEncoder格式）
     * （应该是将你请求的密钥123456经过加密，和数据库中的数据做比对，ok，则发送令牌）
     * 获取token需要的参数
     * "client_id":"r1",
     * "client_secret":"123456",
     * "grant_type":"authorization_code",
     * "scope":"all",
     * "redirect_uri":"http://www.baidu.com",
     * "code":"5AsJkQ",
     *
     * @return
     */
    //案例废弃掉上面内存存储方式，采用数据库存储方式对应数据库表oauth_client_details
    //给bean设置name，避免和框架中ClientDetailsService的bean冲突
    //JdbcClientDetailsService会读取数据库表oauth_client_details中的所有客户端信息，和传入的客户端信息进行匹配
    @Bean(name = "myClientDetailsService")
    public ClientDetailsService clientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }
}
