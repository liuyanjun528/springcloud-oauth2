package springcloud.outh2.project.web.entity;



/**
 * @author Sir_小三
 * @date 2020/2/5--20:39
 */
public class UserLoginDTO {

    private JWT jwt;

    private User user;



    public JWT getJwt() {
        return jwt;
    }

    public void setJwt(JWT jwt) {
        this.jwt = jwt;
    }

    public User getSysUser() {
        return user;
    }

    public void setSysUser(User sysUser) {
        this.user = sysUser;
    }
}
