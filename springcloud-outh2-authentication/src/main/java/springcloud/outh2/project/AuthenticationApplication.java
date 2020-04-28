package springcloud.outh2.project;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springcloud.outh2.project.common.utils.BPwdEncoderUtil;

/**
 * @author Sir_小三
 * @date 2020/4/23--14:19
 */
@SpringBootApplication
@EnableEurekaClient
public class AuthenticationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class,args);
        System.out.println("客户端密钥加密存储数据库"+BPwdEncoderUtil.BCryptPassword("123456"));
    }
}
