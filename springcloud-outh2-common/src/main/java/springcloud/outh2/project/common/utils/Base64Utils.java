/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月26日 下午9:19:29
 * @version 1.0.0
 */
public final class Base64Utils {

	private static final String URL_SALT = "URL_FAST_MX_0904";

	public static final byte[] encode2Byte(String data) {
		return Base64.encodeBase64(data.getBytes());
	}

	public static final String encode(String data) {
		return new String(encode2Byte(data));
	}

	public static final String encodeByte(byte[] binaryData) {
		return Base64.encodeBase64String(binaryData);
	}

	public static final byte[] decode2Byte(String data) {
		return Base64.decodeBase64(data.getBytes());
	}

	public static final String decode(String data) {
		return new String(decode2Byte(data));
	}

	public static String encodeBase64URLSafeString(String url, int validSecond) {
		Assert.notNull(url, "File URL");
		validSecond = validSecond > 0 ? validSecond : 120;
		String expireTime = String.valueOf((System.currentTimeMillis() / 1000) + validSecond);
		byte[] md5Byte = DigestUtils.md5(url.concat(URL_SALT).concat(expireTime));
		String encodeValue = Base64.encodeBase64URLSafeString(md5Byte);
		return url.concat("?v=").concat(encodeValue).concat("&t=").concat(expireTime);
	}

	/**
	 * 本地图片转换成base64字符串
	 * @param imgFile	图片本地路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:40:46
	 */
	public static String ImageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理

		InputStream in = null;
		byte[] data = null;

		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);

			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();

		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
}
