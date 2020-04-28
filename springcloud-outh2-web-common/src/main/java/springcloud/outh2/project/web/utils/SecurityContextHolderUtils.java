package springcloud.outh2.project.web.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import springcloud.outh2.project.web.entity.User;

/**
 * @author Sir_小三
 * @date 2020/3/26--23:27
 */
public class SecurityContextHolderUtils {

    public static User getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

}
