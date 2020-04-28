package springcloud.outh2.project.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloud.outh2.project.web.entity.User;
import springcloud.outh2.project.web.utils.SecurityContextHolderUtils;


/**
 * @author Sir_小三
 * @date 2020/4/24--11:04
 */
@RestController
public class OrderController {
    @GetMapping("/order")
    public String order(){
       return "我是订单接口";
    }

    @GetMapping("/hello")
    public String hello(){

        return "我是hello接口,访问接口用户名为："+ SecurityContextHolderUtils.getUser().getName();
    }


    /**当jwt令牌中包含指定角色时，才可访问此接口，若没有对应角色则报权限不足403
     *hasAnyRole为hasRole下面的方法，参数为多个字符串，可点进源码查看，ROLR_前缀好像需要加，不加不起作用
     * hasAnyAuthority可行也是hasAuthority下的方法，同理
     * 他们两个是等价的
     * @return
     */
    //@PreAuthorize("hasRole('ADMIN')")//不可行
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")//可行
    @GetMapping("/test")
    //hasAnyAuthority可行
    //@PreAuthorize("hasAuthority('ROLE_ADMIN2','ROLE_USER')")//不行
    public String test() {
        User user = SecurityContextHolderUtils.getUser();
        return "我是需要对应角色才可访问的接口---api,登陆用户账号是："+user.getName();
    }

}
