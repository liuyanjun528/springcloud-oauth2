/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年1月28日 下午2:57:15
 * @version 1.0.0
 */
public final class AESUtils {

	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";// 默认的加密算法

	public static Key getSecretKey(final String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
		// SecureRandom 实现完全隨操作系统本身的內部狀態，除非調用方在調用 getInstance 方法之後又調用了
		// setSeed 方法；该实现在 windows 上每次生成的 key 都相同，但是在 solaris 或部分 linux
		// 系统上则不同。
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(password.getBytes());
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
		return secretKeySpec;
	}

	public static String encrypt(String plaintext, Key key) throws Exception {
		if (org.apache.commons.lang3.StringUtils.isBlank(plaintext)) {
			return plaintext;
		}
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] result = cipher.doFinal(plaintext.getBytes("UTF-8"));
		return encodeHexString(result);
	}

	public static String decrypt(String ciphertext, Key key) throws Exception {
		if (org.apache.commons.lang3.StringUtils.isBlank(ciphertext)) {
			return ciphertext;
		}
		byte[] content = decodeHexString(ciphertext);
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = cipher.doFinal(content);
		return new String(result, "UTF-8");
	}

	// ==================二进制与16进制转换 ====================

	public static String encodeHexString(byte[] byteData) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(byteData[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			buffer.append(hex.toUpperCase());
		}
		return buffer.toString();
	}

	public static byte[] decodeHexString(String hexString) {
		if (hexString.length() < 1)
			return null;
		byte[] result = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length() / 2; i++) {
			int high = Integer.parseInt(hexString.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexString.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}
