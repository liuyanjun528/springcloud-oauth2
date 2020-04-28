package springcloud.outh2.project.web.entity;

import java.io.Serializable;

/**
 * <p> 
 * Users and global privileges user
 * </p> 
 * 
 * @author 刘彦军 2020-04-27 13:38:37 042
 */
public class User implements Serializable {
    /**
     * id 描述:
     */
    private Integer id;

    /**
     * name 描述:
     */
    private String name;

    /**
     * password 描述:
     */
    private String password;

    /**
     * mobile 描述:
     */
    private String mobile;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name == null || name.trim().isEmpty() ) ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = (password == null || password.trim().isEmpty() ) ? null : password.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = (mobile == null || mobile.trim().isEmpty() ) ? null : mobile.trim();
    }
}