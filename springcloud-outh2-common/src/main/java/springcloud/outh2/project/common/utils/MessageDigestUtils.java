/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月26日 下午9:11:21
 * @version 1.0.0
 */
public final class MessageDigestUtils {

	public static final String md5(String data) {
		Assert.notEmpty(data, "data");
		String md5 = DigestUtils.md5Hex(data.getBytes());
		return md5;
	}

	public static final String md5(String data, String salt) {
		Assert.notEmpty(data, "data");
		Assert.notEmpty(salt, "salt");
		String md5 = md5(data + salt);
		return md5;
	}

	public static final String sha1(String data) {
		Assert.notEmpty(data, "data");
		String sha1 = DigestUtils.sha1Hex(data.getBytes());
		return sha1;
	}

	public static final String sha1(String data, String salt) {
		Assert.notEmpty(data, "data");
		Assert.notEmpty(salt, "salt");
		String sha1 = sha1(data + salt);
		return sha1;
	}

}
