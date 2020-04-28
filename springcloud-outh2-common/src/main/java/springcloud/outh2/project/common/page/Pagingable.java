package springcloud.outh2.project.common.page;


import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Pagingable<E, R> {
	int count(@Param("entity") E var1);

	List<R> page(@Param("entity") E var1, @Param("paging") Paging var2);
}
