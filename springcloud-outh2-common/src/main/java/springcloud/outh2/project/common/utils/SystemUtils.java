/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * <p>
 * 系统工具类
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月17日 下午5:04:51
 * @version 1.0.0
 */
public final class SystemUtils {

	private SystemUtils() {
	}

	public static Map<String, String> getSystemProperties() {
		return toMap(System.getProperties());
	}

	public static Map<String, String> getSystemEnv() {
		return toMap(System.getenv());
	}

	private static Map<String, String> toMap(Map<String, String> properties) {
		Map<String, String> map = new TreeMap<>();
		map.putAll(properties);
		return map;
	}

	private static Map<String, String> toMap(Properties properties) {
		Map<String, String> map = new TreeMap<>();
		for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements();) {
			String name = String.valueOf(e.nextElement());
			map.put(name, properties.getProperty(name));
		}
		return map;
	}
}
