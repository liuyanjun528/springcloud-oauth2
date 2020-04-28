/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月17日 下午5:19:25
 * @version 1.0.0
 */
public enum DateFormat {

	YEAR("yyyy"),

	MOUTH("yyyy-MM"),

	DATE("yyyy-MM-dd"),
	FG_DATE("yyyy年MM月dd日"),
	DATA_TIME("yyyy-MM-dd HH:mm:ss"),

	DATA_TIME_MIS("yyyy-MM-dd HH:mm:ss SSS"),

	DATA_6BIT("yyyyMM"),

	DATE_8BIT("yyyyMMdd"),

	DATE_14BIT("yyyyMMddHHmmss"),

	DATE_17BIT("yyyyMMddHHmmssSSS"),

	DAY_00("yyyy-MM-dd 00:00:00"),

	DAY_18("yyyy-MM-dd 18:00:00"),

	DAY_24("yyyy-MM-dd 24:00:00");

	private String format;

	private DateFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
