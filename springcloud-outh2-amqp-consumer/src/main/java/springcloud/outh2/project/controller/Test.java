package springcloud.outh2.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanjun.liu
 * @date 2020/8/2--14:07
 */
@RestController
//@RequestMapping("/test")
public class Test {

    @GetMapping("test")
    public String test(){
        return "这是消费者测试controller 不需要令牌 令牌";
    }

    @GetMapping("test2")
    public String test2(){
        return "这是消费者测试controller 需要令牌 令牌才可访问";
    }
}
