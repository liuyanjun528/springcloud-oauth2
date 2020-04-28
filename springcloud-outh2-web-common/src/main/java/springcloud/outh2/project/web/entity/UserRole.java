package springcloud.outh2.project.web.entity;

import java.io.Serializable;

/**
 * <p> 
 *  user_role
 * </p> 
 * 
 * @author 刘彦军 2020-04-27 13:38:37 042
 */
public class UserRole implements Serializable {
    /**
     * id 描述:
     */
    private Long id;

    /**
     * user_id 描述:
     */
    private Long userId;

    /**
     * role_id 描述:
     */
    private Long roleId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}