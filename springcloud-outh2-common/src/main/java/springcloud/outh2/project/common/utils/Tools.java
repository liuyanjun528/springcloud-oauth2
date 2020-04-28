/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月17日 下午5:37:32
 * @version 1.0.0
 */
public final class Tools {

	public interface MapCollectionCallback<K, V> {
		void doCallback(K key, V value);
	}

	public static final int DEFAULT_MAX_LENGTH = 256;

	/**
	 * Millis per seconds, value = 1000
	 */
	public static final int PER_SECONDS_IN_MILLIS = 1000;

	/**
	 * Seconds per minute, value = 60
	 */
	public static final int PER_MINUTE_IN_SECONDS = 60;

	/**
	 * Half byte mask, value = 0xf
	 */
	public static final int HALF_BYTE_MASK = 0xf;

	/**
	 * Byte mask, value = 0xff
	 */
	public static final int BYTE_MASK = 0xff;

	/**
	 * Bit count of half byte, value = 4
	 */
	public static final int HALF_BYTE_BITS = 4;

	public static final int LOW_HALF_1 = 0;
	public static final int LOW_HALF_2 = 4;
	public static final int LOW_HALF_3 = 8;
	public static final int LOW_HALF_4 = 12;
	public static final int LOW_HALF_5 = 16;
	public static final int LOW_HALF_6 = 20;
	public static final int LOW_HALF_7 = 24;
	public static final int LOW_HALF_8 = 28;

	public static final int SHIFT_BIT_1 = 1;
	public static final int SHIFT_BIT_4 = 4;
	public static final int SHIFT_BIT_8 = 8;
	public static final int SHIFT_BIT_12 = 12;
	public static final int SHIFT_BIT_16 = 16;
	public static final int SHIFT_BIT_20 = 20;
	public static final int SHIFT_BIT_24 = 24;
	public static final int SHIFT_BIT_28 = 28;
	public static final int SHIFT_BIT_32 = 32;

	public static final int DECIMAL_MULTIPLES = 10;

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public static final String SUPPRESS_WARININGS_UNCHECKED = "unchecked";

	public static final String EMPTY_STRING = "";

	private Tools() {
	}

	public static String truncate(final Object object) {
		if (object == null) {
			return null;
		}
		return truncate(String.valueOf(object), DEFAULT_MAX_LENGTH);
	}

	public static String truncate(final String str) {
		return truncate(str, DEFAULT_MAX_LENGTH);
	}

	public static String truncate(final String str, final int max) {
		if (str == null || str.length() <= max) {
			return str;
		}
		return str.substring(0, max) + "... (" + str.length() + ")";
	}

	public static StringBuilder truncate(final String str, final int max, final StringBuilder builder) {
		if (str == null || str.length() <= max) {
			return builder.append(str);
		}
		return builder.append(str, 0, max).append("... (").append(str.length()).append(")");
	}

	public static long toSeconds(long millis) {
		return millis / PER_SECONDS_IN_MILLIS;
	}

	public static <K> Set<K> keySet(Map<K, ?> map) {
		if (map == null) {
			return null;
		}
		return map.keySet();
	}

	public static <K, C extends Collection<V>, V> void iterateCallback(Map<K, C> map,
			MapCollectionCallback<K, V> callback) {
		if (isBlank(map)) {
			return;
		}
		for (Map.Entry<K, C> entry : map.entrySet()) {
			Collection<V> values = entry.getValue();
			if (isBlank(values)) {
				continue;
			}
			K key = entry.getKey();
			for (V value : values) {
				callback.doCallback(key, value);
			}
		}
	}

	public static String formatWithMillis(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss,SSS");
	}

	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	public static <T> T[] cloneArray(T[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static byte[] cloneArray(byte[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static char[] cloneArray(char[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static int[] cloneArray(int[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static int size(String str) {
		return (str == null) ? 0 : str.length();
	}

	public static int size(Collection<?> collection) {
		return (collection == null) ? 0 : collection.size();
	}

	public static int size(Map<?, ?> map) {
		return (map == null) ? 0 : map.size();
	}

	public static int size(byte[] bys) {
		return (bys == null) ? 0 : bys.length;
	}

	public static int size(Object[] objects) {
		return (objects == null) ? 0 : objects.length;
	}

	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * Tools.isBlank(null)      = true
	 * Tools.isBlank("")        = true
	 * Tools.isBlank(" ")       = true
	 * Tools.isBlank("bob")     = false
	 * Tools.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 */
	public static boolean isBlank(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if a Collection object is empty ( size == 0 ) or null.
	 * </p>
	 *
	 * @param collection
	 *            the Collection object to check, may be null
	 * @return <code>true</code> if the Collection object is null or the size is
	 *         zero
	 */
	public static boolean isBlank(Collection<?> collection) {
		return (collection == null || collection.size() == 0);
	}

	/**
	 * <p>
	 * Checks if a Map object is empty ( size == 0 ) or null.
	 * </p>
	 *
	 * @param map
	 *            the Map object to check, may be null
	 * @return <code>true</code> if the Map object is null or the size is zero
	 */
	public static boolean isBlank(Map<?, ?> map) {
		return (map == null || map.size() == 0);
	}

	/**
	 * <p>
	 * Checks if a byte array is empty ( length == 0 ) or null.
	 * </p>
	 *
	 * @param bytes
	 *            the byte array to check, may be null
	 * @return <code>true</code> if the byte array is null or the length is zero
	 */
	public static boolean isBlank(byte[] bytes) {
		return (bytes == null || bytes.length == 0);
	}

	/**
	 * <p>
	 * Checks if a byte array is empty ( length == 0 ) or null.
	 * </p>
	 *
	 * @param objects
	 *            the object array to check, may be null
	 * @return <code>true</code> if the byte array is null or the length is zero
	 */
	public static boolean isBlank(Object[] objects) {
		return (objects == null || objects.length == 0);
	}

	public static String trim(String str) {
		if (str == null) {
			return null;
		}
		return str.trim();
	}

	public static String trimToNull(String str) {
		if (isBlank(str)) {
			return null;
		}
		return str.trim();
	}

	public static String join(String seperator, String... strs) {
		if (strs == null) {
			return null;
		}
		String sep = seperator == null ? EMPTY_STRING : seperator;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			if (i > 0) {
				builder.append(sep);
			}
			builder.append(strs[i]);
		}
		return builder.toString();
	}

	public static String[] split(String text, Pattern pattern) {
		if (text == null) {
			return null;
		}
		if (isBlank(text)) {
			return EmptyConstants.EMPTY_STRING_ARRAY;
		}
		return pattern.split(text.trim());
	}

	public static String toLowerCase(String str) {
		if (str == null) {
			return str;
		}
		return str.toLowerCase();
	}

	public static String toUpperCase(String str) {
		if (str == null) {
			return str;
		}
		return str.toUpperCase();
	}

	public static int parseInt(String num, int defaultValue) {
		try {
			return Integer.parseInt(num);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static boolean isInArray(String str, String[] array) {
		if (isBlank(array) || isBlank(str)) {
			return false;
		}
		for (String arr : array) {
			if (str.equals(arr)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInArrayIgnoreCase(String str, String[] array) {
		if (isBlank(array) || isBlank(str)) {
			return false;
		}
		for (String arr : array) {
			if (str.equalsIgnoreCase(arr)) {
				return true;
			}
		}
		return false;
	}

}
