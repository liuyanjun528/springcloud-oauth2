/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月17日 下午5:34:17
 * @version 1.0.0
 */
public final class AmountUtils {

	public static final String CURRENCY_REGEX = "/^(-1|([0]\\.([0-9][1-9]|[1-9][0-9]))|([1-9]\\d*)(\\.\\d{2})?)$/";

	/**
	 * 将金额分转换为元:保留2位小数
	 *
	 * @param inputYuanDecimal
	 * @return
	 */
	public static String changeToYuan(Integer inputYuanDecimal) {
		if (inputYuanDecimal == null) {
			return null;
		}
		if (inputYuanDecimal < 0) {
			return String.valueOf(inputYuanDecimal);
		}
		return String.format("%.2f", inputYuanDecimal / 100d);
	}

	/**
	 * 将金额分转换为元:保留2位小数
	 *
	 * @param inputYuanDecimal
	 * @return
	 */
	public static String changeToYuan(Long inputYuanDecimal) {
		if (inputYuanDecimal == null) {
			return null;
		}
		if (inputYuanDecimal < 0) {
			return String.valueOf(inputYuanDecimal);
		}
		return String.format("%.2f", inputYuanDecimal / 100d);
	}

	/**
	 * 将金额元转换为分
	 *
	 * @param amount
	 * @return
	 */
	public static String changeY2F(String amount) {
		if (StringUtils.isEmpty(amount) || Double.valueOf(amount) < 0) {
			return amount;
		}
		return BigDecimal.valueOf(Double.valueOf(amount)).multiply(new BigDecimal(100)).toBigInteger().toString();
	}
}
