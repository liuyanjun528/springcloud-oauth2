/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月17日 下午5:36:29
 * @version 1.0.0
 */
public final class Assert {

	private static final String IS_REQUIRED = "is required";

	private Assert() {
	}

	public static void notLesser(int num, int min, String name) {
		if (num < min) {
			throw new IllegalArgumentException(
					"'" + name + "' value is " + num + " that must be greater than or equals " + min);
		}
	}

	public static void notGreater(int num, int max, String name) {
		if (num > max) {
			throw new IllegalArgumentException(
					"'" + name + "' value is " + num + " that must be lesser than or equals " + max);
		}
	}

	public static void notEmpty(String value, String name) {
		if (Tools.isBlank(value)) {
			throw new IllegalArgumentException("'" + name + "' " + IS_REQUIRED
					+ ", the String must has text, it must not be null, empty, or blank");
		}
	}

	public static void notEmpty(Collection<?> value, String name) {
		if (Tools.isBlank(value)) {
			throw new IllegalArgumentException("'" + name + "' " + IS_REQUIRED
					+ ", the Collection must has element, it must contain at least 1 element");
		}
	}

	public static void notEmpty(Map<?, ?> value, String name) {
		if (Tools.isBlank(value)) {
			throw new IllegalArgumentException(
					"'" + name + "' " + IS_REQUIRED + ", the Map must has entry, it must contain at least 1 entry");
		}
	}

	public static void notEmpty(Object[] value, String name) {
		if (Tools.isBlank(value)) {
			throw new IllegalArgumentException("'" + name + "' " + IS_REQUIRED
					+ ", the array must has element, it must contain at least 1 element");
		}
	}

	public static void notNull(Object value, String name) {
		if (value == null) {
			throw new IllegalArgumentException("'" + name + "' " + IS_REQUIRED + ", the object must not be null");
		}
	}
}
