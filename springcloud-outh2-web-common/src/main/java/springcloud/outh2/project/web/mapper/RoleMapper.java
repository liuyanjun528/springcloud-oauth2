package springcloud.outh2.project.web.mapper;

import org.apache.ibatis.annotations.Mapper;

import springcloud.outh2.project.common.page.Pagingable;
import springcloud.outh2.project.web.entity.Role;

@Mapper
public interface RoleMapper extends Pagingable<Role, Role> {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}
