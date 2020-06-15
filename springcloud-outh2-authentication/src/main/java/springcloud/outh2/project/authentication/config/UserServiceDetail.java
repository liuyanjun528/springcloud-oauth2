package springcloud.outh2.project.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springcloud.outh2.project.common.utils.CollectionUtils;
import springcloud.outh2.project.web.entity.Role;
import springcloud.outh2.project.web.entity.SecurityUser;
import springcloud.outh2.project.web.entity.User;
import springcloud.outh2.project.web.entity.UserRole;
import springcloud.outh2.project.web.mapper.RoleMapper;
import springcloud.outh2.project.web.mapper.UserMapper;
import springcloud.outh2.project.web.mapper.UserRoleMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sir_小三
 * @date 2020/1/30--12:54
 * * 角色表实现GrantedAuthority  类，重写getAuthority方法，返回角色，并且在UserServiceDetail的实现类中，用户如果登陆成功
 * * 通过用户id，查询用户角色关联表中数据，然后在查询到具体的角色，设置到
 * * UserDetails的权限集合中Set<GrantedAuthority> authorities;
 * * spring-security 提供了一个user，也可以间接继承此类，通过构造函数设置进去权限集合
 * * 权限集合中可以是任意字符串，有重写getAuthority() 返回数据决定
 * * @author 刘彦军 2020-02-02 21:38:10 269
 * 这里，密码模式请求jwt令牌的时候，会调用验证用户信息，并生成令牌,令牌中包含用户的信息，以及角色信息
 */
@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private UserRoleMapper userRoleMapper;
    @Autowired(required = false)
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //这里角色为空，没有做角色相关查询
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        User user1 = new User();
        user1.setName(username);
        List<User> list = userMapper.page(user1, null);
        if (CollectionUtils.isNullOrEmpty(list)) {
            throw new UsernameNotFoundException("没有此用户");
        }
        //通过用户查用户角色关系表
        User user = list.get(0);
        //查询用户角色
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId().longValue());
        List<UserRole> page = userRoleMapper.page(userRole, null);
        for (int i = 0; i < page.size(); i++) {
            Role role = roleMapper.selectByPrimaryKey(page.get(i).getRoleId());
            grantedAuthorities.add(role);
        }
        System.out.println("============================>");
        SecurityUser securityUser = new SecurityUser(username, user.getPassword(), grantedAuthorities);
        return securityUser;
    }

}
