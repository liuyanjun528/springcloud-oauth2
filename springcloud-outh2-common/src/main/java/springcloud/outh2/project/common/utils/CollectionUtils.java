/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月17日 下午3:58:32
 * @version 1.0.0
 */

@Slf4j
public final class CollectionUtils {

	private CollectionUtils() {
	}

	public static <T> boolean isNullOrEmpty(Collection<T> collection) {
		return collection == null || collection.isEmpty();
	}

	public static <T> boolean isNullOrEmpty(T[] array) {
		return array == null || array.length == 0;
	}

	public static <K, V> boolean isNullOrEmpty(Map<K, V> map) {
		return map == null || map.isEmpty();
	}

	public static <T> boolean contains(T[] array, T val) {
		for (T t : array) {
			if (val.equals(t)) {
				return true;
			}
		}
		return false;
	}

	public static <T> T head(List<T> list) {
		return list.get(0);
	}

	public static <T> T end(List<T> list) {
		return list.get(list.size() - 1);
	}

	public static <T, R> List<R> map(List<T> list, Function<? super T, R> function) {
		return list.stream().map(function).collect(Collectors.toList());
	}

	public static <K, V> LinkedHashMap<K, V> ofMap(List<V> list, Function<V, K> keyFunction) {
		LinkedHashMap<K, V> result = Maps.newLinkedHashMap();
		for (V value : list) {
			result.put(keyFunction.apply(value), value);
		}
		return result;
	}

	public static <K, V> ListMultimap<K, V> groupBy(List<V> list, Function<V, K> keyFunction) {
		ListMultimap<K, V> result = ArrayListMultimap.create();
		for (V value : list) {
			result.put(keyFunction.apply(value), value);
		}
		return result;
	}

	public static <Source, Target> List<Target> copyCollectionAs(Collection<Source> source, Class<Target> targetType) {
		if (isNullOrEmpty(source)) {
			return Lists.newArrayList();
		}
		List<Target> result = Lists.newArrayListWithExpectedSize(source.size());
		for (Source src : source) {
			Target target = null;
			try {
				target = targetType.newInstance();
				BeanUtils.copyProperties(target, src);
			} catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error("集合拷贝异常", targetType);
				}
			}
			result.add(target);
		}
		return result;
	}
}
