package springcloud.outh2.project.web.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import springcloud.outh2.project.common.utils.CollectionUtils;
import springcloud.outh2.project.web.entity.User;
import springcloud.outh2.project.web.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

/**
 * 定义一个Interceptor 非常简单方式也有几种，我这里简单列举两种 1、类 要实现Spring 的HandlerInterceptor 接口 2、类
 * 继承实现了HandlerInterceptor 接口的类，例如 已经提供的实现了HandlerInterceptor
 * 接口的抽象类HandlerInterceptorAdapter
 *
 * @author 刘彦军 ！！！除了定义此类，还需要将自定义的拦截器，注册到WebMvcConfigurer中，拦截器才起作用
 * 之前我们在xml中配置拦截路径等，springboot我们需要继承WebMvcConfigurerAdapter类
 * 不过springBoot2.0以上 WebMvcConfigurerAdapter 方法过时， 有两种替代方案：
 * 1、继承WebMvcConfigurationSupport 2、实现WebMvcConfigurer
 * 但是继承WebMvcConfigurationSupport会让Spring-boot对mvc的自动配置失效。根据项目情况选择。
 * 现在大多数项目是前后端分离，并没有对静态资源有自动配置的需求所以继承WebMvcConfigurationSupport也未尝不可。
 */
@Slf4j
@Component
public class TokenHandlerInterceptor implements HandlerInterceptor {
    //需要提前以@bean的方式注入拦截器，否则sysUserMapper为null;
    //原因是因为拦截器的加载在springcontext之前，所以自动注入的mapper是null
    @Autowired(required = false)
    private UserMapper userMapper;

    // 最终拦截
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //log.info("最终拦截--在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行，主要是用于进行资源清理工作");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    // 后置拦截+统计总访问数
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //log.info("后置拦截--请求处理之后进行调用，但是在视图被渲染之前，即Controller方法调用之后");
    }

    //
    // 前置拦截解析令牌
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //System.out.println("前置拦截在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
            //获取用户的account，这里就是UserDetails类 中返回的SecurityUser的name属性，这个name可以扩展
            String account = userAuthentication.getName();
            User user = new User();
            user.setName(account);
            List<User> page = userMapper.page(user, null);
            User User1 = null;
            if (!CollectionUtils.isNullOrEmpty(page)) {
                User1 = page.get(0);
            }
            // 角色数组
            Collection<? extends GrantedAuthority> authorities = userAuthentication.getAuthorities();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(User1, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        return true;
    }

}
