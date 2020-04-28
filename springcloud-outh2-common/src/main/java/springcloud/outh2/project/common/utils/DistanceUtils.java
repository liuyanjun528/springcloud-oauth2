package springcloud.outh2.project.common.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DistanceUtils {
	private static double EARTH_RADIUS = 6378.137D;

	public DistanceUtils() {
	}

	private static double rad(double d) {
		return d * 3.141592653589793D / 180.0D;
	}

	public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
		Double lat1 = Double.parseDouble(lat1Str);
		Double lng1 = Double.parseDouble(lng1Str);
		Double lat2 = Double.parseDouble(lat2Str);
		Double lng2 = Double.parseDouble(lng2Str);
		double radLat1 = rad(lat1.doubleValue());
		double radLat2 = rad(lat2.doubleValue());
		double difference = radLat1 - radLat2;
		double mdifference = rad(lng1.doubleValue()) - rad(lng2.doubleValue());
		double distance = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2.0D), 2.0D)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(mdifference / 2.0D), 2.0D)));
		distance *= EARTH_RADIUS;
		distance = (double) (Math.round(distance * 10000.0D) / 10000L);
		String distanceStr = distance + "";
		distanceStr = distanceStr.substring(0, distanceStr.indexOf("."));
		return distanceStr;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getAround(String latStr, String lngStr, String raidus) {
		Map map = new HashMap();
		Double latitude = Double.parseDouble(latStr);
		Double longitude = Double.parseDouble(lngStr);
		Double minLat = new Double(0.0D);
		Double maxLat = new Double(0.0D);
		Double minLng = new Double(0.0D);
		Double maxLng = new Double(0.0D);
		if (BigDecimal.ZERO.compareTo(BigDecimal.valueOf(latitude.doubleValue())) != 0
				&& BigDecimal.ZERO.compareTo(BigDecimal.valueOf(longitude.doubleValue())) != 0) {
			Double degree = 111293.63611111112D;
			double raidusMile = Double.parseDouble(raidus);
			Double mpdLng = Double
					.parseDouble((degree.doubleValue() * Math.cos(latitude.doubleValue() * 0.017453292519943295D) + "")
							.replace("-", ""));
			Double dpmLng = 1.0D / mpdLng.doubleValue();
			Double radiusLng = dpmLng.doubleValue() * raidusMile;
			minLat = longitude.doubleValue() - radiusLng.doubleValue();
			maxLat = longitude.doubleValue() + radiusLng.doubleValue();
			Double dpmLat = 1.0D / degree.doubleValue();
			Double radiusLat = dpmLat.doubleValue() * raidusMile;
			minLng = latitude.doubleValue() - radiusLat.doubleValue();
			maxLng = latitude.doubleValue() + radiusLat.doubleValue();
		}

		map.put("minLat", minLat + "");
		map.put("maxLat", maxLat + "");
		map.put("minLng", minLng + "");
		map.put("maxLng", maxLng + "");
		return map;
	}

}
