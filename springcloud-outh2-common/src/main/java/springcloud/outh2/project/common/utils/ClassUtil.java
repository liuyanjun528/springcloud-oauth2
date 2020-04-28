/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年1月25日 下午12:24:19
 * @version 1.0.0
 */
public class ClassUtil {

	/**
	 * 获得超类的泛型参数的实际类型
	 *
	 * @param clazz
	 * @return 实际类型集合
	 */
	public static List<Class<?>> getActualTypeList(Class<?> clazz) {
		List<Class<?>> actualList = new ArrayList<Class<?>>();
		Type type = clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type actualTypeAry = parameterizedType.getActualTypeArguments()[0];
			actualList = getActualTypeList(actualTypeAry);
		}
		return actualList;
	}

	public static List<Class<?>> getActualTypeList(Type actualTypeAry) {
		List<Class<?>> actualList = new ArrayList<Class<?>>();
		if (actualTypeAry instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) actualTypeAry;
			actualTypeAry = parameterizedType.getActualTypeArguments()[0];
			if (actualTypeAry instanceof Class<?>) {
				actualList.add((Class<?>) parameterizedType.getRawType());
				actualList.add((Class<?>) actualTypeAry);
			} else {
				actualList.add((Class<?>) parameterizedType.getRawType());
				actualList.addAll(getActualTypeList(actualTypeAry));
			}
		} else if (actualTypeAry instanceof Class<?>) {
			actualList.add((Class<?>) actualTypeAry);
		}
		return actualList;
	}
}
