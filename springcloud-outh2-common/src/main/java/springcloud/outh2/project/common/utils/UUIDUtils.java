/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import java.util.UUID;

/**
 * <p>
 * UUID工具类<br/>
 * 32位序列号{@link #generate32BitId()} <br/>
 * 36位序列号{@link #generate36BitId()}
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年10月31日 下午4:38:32
 * @version 1.0.0
 */
public final class UUIDUtils {

	private static final String HORIZONTAL_LINE = "-";

	private static final String EMPTY = "";

	public static String generate32BitId() {
		return generate36BitId().replace(HORIZONTAL_LINE, EMPTY);
	}

	public static String generate36BitId() {
		return UUID.randomUUID().toString();
	}
}
