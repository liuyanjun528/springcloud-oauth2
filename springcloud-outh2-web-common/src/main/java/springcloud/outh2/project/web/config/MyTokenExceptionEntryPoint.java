package springcloud.outh2.project.web.config;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import springcloud.outh2.project.common.em.CommonCodeEnum;
import springcloud.outh2.project.common.result.FastResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sir_小三
 * 资源服务器会检查请求头里面是否带上了token，并去认证服务器校验这个token是否合法，是否过期
 * @Description 无效Token返回处理器, 或者不带token处理
 * @date 2020/2/20--14:01
 */
@Component
public class MyTokenExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        FastResponse fastResponse = new FastResponse(CommonCodeEnum.TOKEN_ERROR.getCode(), CommonCodeEnum.TOKEN_ERROR.getMessage());
        Object o = JSON.toJSON(fastResponse);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().write(o.toString());
    }
}
