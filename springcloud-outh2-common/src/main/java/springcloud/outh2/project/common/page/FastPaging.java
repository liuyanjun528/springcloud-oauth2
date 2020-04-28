/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.page;

import lombok.Data;

/**
 * <p>
 * WEB 分页基础类
 * </p>
 */
@Data
public class FastPaging {

	public static final int DEFAULT_PAGE = 1;

	public static final int DEFAULT_PAGE_SIZE = 10;

	private int page = DEFAULT_PAGE;

	private int pageSize = DEFAULT_PAGE_SIZE;


}
