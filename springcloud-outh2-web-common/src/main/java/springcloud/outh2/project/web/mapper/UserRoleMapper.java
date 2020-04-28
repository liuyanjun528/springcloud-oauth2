package springcloud.outh2.project.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import springcloud.outh2.project.web.entity.UserRole;

@Mapper
public interface UserRoleMapper extends springcloud.outh2.project.common.page.Pagingable<UserRole, UserRole> {
    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}