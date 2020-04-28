package springcloud.outh2.project.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springcloud.outh2.project.common.em.CommonCodeEnum;
import springcloud.outh2.project.common.exception.CommonException;
import springcloud.outh2.project.common.result.ExceptionUtils;
import springcloud.outh2.project.common.utils.BPwdEncoderUtil;
import springcloud.outh2.project.common.utils.CollectionUtils;
import springcloud.outh2.project.web.entity.JWT;
import springcloud.outh2.project.web.entity.User;
import springcloud.outh2.project.web.entity.UserLoginDTO;
import springcloud.outh2.project.web.mapper.UserMapper;
import springcloud.outh2.project.web.utils.HttpClientUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author Sir_小三
 * @date 2020/4/28--10:08
 */
@Service
public class LoginService {
    @Autowired(required = false)
    private UserMapper userMapper;
    public UserLoginDTO login(String name, String password) throws CommonException , IOException {
        User user = new User();
        user.setName(name);
        List<User> list = userMapper.page(user, null);
        if (CollectionUtils.isNullOrEmpty(list)) {
            throw ExceptionUtils.create(CommonCodeEnum.ACCOUNT_ERROR);
        }
        //比对密码
        if (!BPwdEncoderUtil.matches(password, list.get(0).getPassword())) {
            throw ExceptionUtils.create(CommonCodeEnum.PASSWORD_ERROR);
        }
        //获取jwt
        JWT jwt = HttpClientUtils.httpPost(name, password);
        if (jwt == null) {
            throw ExceptionUtils.create(CommonCodeEnum.GET_TOKEN_ERROE);
        }
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setJwt(jwt);
        userLoginDTO.setSysUser(list.get(0));
        return userLoginDTO;
    }
}
