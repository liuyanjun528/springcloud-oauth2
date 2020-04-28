package springcloud.outh2.project.web.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * <p>
 *  role
 * </p>
 *
 * @author 刘彦军 2020-04-27 13:38:37 042
 */
public class Role implements Serializable, GrantedAuthority {
    /**
     * id 描述:
     */
    private Long id;

    /**
     * name 描述:
     */
    private String name;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name == null || name.trim().isEmpty() ) ? null : name.trim();
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
