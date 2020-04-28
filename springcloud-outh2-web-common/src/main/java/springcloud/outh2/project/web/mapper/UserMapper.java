package springcloud.outh2.project.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import springcloud.outh2.project.web.entity.User;

@Mapper
public interface UserMapper extends springcloud.outh2.project.common.page.Pagingable<User, User> {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}