/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import java.math.BigDecimal;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年6月6日 下午5:31:43
 * @version 1.0.0
 */
public class GeoHashUtils {

	// 地球半径,单位米
	private static final double EARTH_RADIUS = 6378137;
	private static final BigDecimal BD2 = new BigDecimal(2);
	private static final int SCALE = 6;

	private static final BigDecimal BD180 = new BigDecimal("180");

	/**
	 * 计算地球上任意两点(经纬度)距离, 单位：米
	 */
	public static BigDecimal distance(BigDecimal lonA, BigDecimal latA, BigDecimal lonB, BigDecimal latB) {
		latA = rad(latA);
		latB = rad(latB);
		BigDecimal aDiff = nvl(latA).subtract(latB);
		BigDecimal nDif = rad(nvl(lonA).subtract(lonB));

		double sa2 = Math.sin(aDiff.divide(BD2, SCALE, BigDecimal.ROUND_HALF_UP).doubleValue());
		double sb2 = Math.sin(nDif.divide(BD2, SCALE, BigDecimal.ROUND_HALF_UP).doubleValue());
		double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(latA.doubleValue()) * Math.cos(latB.doubleValue()) * sb2 * sb2));
		return BigDecimal.valueOf(distance);
	}

	private static BigDecimal rad(BigDecimal dot) {
		return nvl(dot).multiply(BigDecimal.valueOf(Math.PI)).divide(BD180, SCALE, BigDecimal.ROUND_HALF_UP);
	}

	private static BigDecimal nvl(BigDecimal num) {
		return num == null ? BigDecimal.ZERO : num;
	}

}
