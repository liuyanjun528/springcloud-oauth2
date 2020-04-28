package springcloud.outh2.project.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springcloud.outh2.project.authentication.service.LoginService;
import springcloud.outh2.project.common.exception.CommonException;
import springcloud.outh2.project.common.result.FastResponse;
import springcloud.outh2.project.common.result.ResponseUtils;
import springcloud.outh2.project.common.result.ValidatorUtils;
import springcloud.outh2.project.web.entity.User;
import springcloud.outh2.project.web.entity.UserLoginDTO;

import java.io.IOException;

/**
 * @author Sir_小三
 * @date 2020/4/28--10:02
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public FastResponse<UserLoginDTO> login(@RequestBody User user) throws CommonException , IOException {
        ValidatorUtils.validate(user.getName(), "账号");
        ValidatorUtils.validate(user.getPassword(), "密码");
        UserLoginDTO login = loginService.login(user.getName(), user.getPassword());
        return ResponseUtils.success(login);
    }

}
