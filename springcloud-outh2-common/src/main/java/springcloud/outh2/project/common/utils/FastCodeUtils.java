/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.Key;

/**
 * 编码工具类
 * <p>
 * 主要用于数据加解密处理，以及掩码数据处理
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月17日 下午5:07:00
 * @version 1.0.0
 */
@Slf4j
public final class FastCodeUtils {

	private static final Charset CHARSET = Charset.forName("UTF-8");

	private static final String DEFAULT_KEY = "1MEhD58VjFeFARU7BIbOYXNGCz5uQNp6";

	private static final String DEFAULT_CRYPTO_ALG = "DESede/ECB/PKCS5Padding";

	private static final char MASK_CHAR = '*';

	private static final Key KEY = loadKey();

	private FastCodeUtils() {
	}

	/**
	 * 使用加密算法对数据加密，加密后的密文以 Base64 表示。 CodecUtils
	 *
	 * @param plain
	 *            原文数据
	 * @return 以 Base64 表示的加密后密文
	 */
	public static String encrypt(String plain) {
		if (!StringUtils.isNotEmpty(plain)) {
			return plain;
		}
		try {
			byte[] in = plain.getBytes(CHARSET);
			Cipher cipher = Cipher.getInstance(DEFAULT_CRYPTO_ALG);
			cipher.init(Cipher.ENCRYPT_MODE, KEY);
			byte[] out = cipher.doFinal(in);
			return Base64.encodeBase64String(out);
		} catch (Exception e) {
			log.error("Encrypt error, plain = {}, mode = {}", plain, e);
			return null;
		}
	}

	/**
	 * 使用 加密算法对 Base64 密文进行解密。
	 *
	 * @param base64CipherText
	 *            以 Base64 表示的密文数据
	 * @return 解密后的原文数据
	 */
	public static String decrypt(String base64CipherText) {
		if (!StringUtils.isNotEmpty(base64CipherText)) {
			return base64CipherText;
		}
		byte[] bytes = decrypt2Bytes(base64CipherText);
		if (bytes == null) {
			return null;
		}
		return new String(bytes, CHARSET);
	}

	private static byte[] decrypt2Bytes(String base64CipherText) {
		try {
			byte[] in = Base64.decodeBase64(base64CipherText);
			Cipher cipher = Cipher.getInstance(DEFAULT_CRYPTO_ALG);
			cipher.init(Cipher.DECRYPT_MODE, KEY);
			return cipher.doFinal(in);
		} catch (Exception e) {
			log.error("Encrypt error, base64CipherText = {}, mode = {}", base64CipherText, e);
			return null;
		}
	}

	/**
	 * 对数据进行 SHA1 摘要
	 *
	 * @param plain
	 *            数据
	 * @return SHA1 摘要后的 Base64 数据
	 */
	public static String hash(String plain) {
		if (!StringUtils.isNotEmpty(plain)) {
			return plain;
		}
		byte[] bytes = DigestUtils.sha1(plain);
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * 使用 加密算法对 Base64 密文进行解密，并将解密后的数据计算 SHA-1 摘要
	 *
	 * @param base64CipherText
	 *            以 Base64 表示的密文数据
	 * @return 解密后的原文数据再做摘要后的 Base64 的值
	 */
	public static String decryptAndHash(String base64CipherText) {
		if (!StringUtils.isNotEmpty(base64CipherText)) {
			return base64CipherText;
		}
		byte[] plain = decrypt2Bytes(base64CipherText);
		byte[] hash = DigestUtils.sha1(plain);
		return Base64.encodeBase64String(hash);
	}

	/**
	 * 先将以 Base64 密文的银行卡卡号解密，之后再对于银行卡卡号作掩码处理。 掩码处理规则，卡号的前面 6 位与后面 4 位使用原文，其他位数使用 "*"
	 * 表示
	 *
	 * @param base64CipherBankCard
	 *            Base64 密文的银行卡卡号解密
	 * @return 掩码的银行卡卡号
	 */
	public static String decryptAndMaskBankCard(String base64CipherBankCard) {
		if (base64CipherBankCard == null) {
			return null;
		}
		String plain = decrypt(base64CipherBankCard);
		if (plain == null) {
			return null;
		}
		return maskBankCard(plain);
	}

	/**
	 * 对于银行卡卡号作掩码处理。掩码处理规则，卡号的前面 6 位与后面 4 位使用原文，其他位数使用 "*" 表示
	 *
	 * @param cardNo
	 *            银行卡卡号
	 * @return 掩码的银行卡卡号
	 */
	public static String maskBankCard(String cardNo) {
		return mask(cardNo, 0, 4);
	}

	/**
	 * 先将以 Base64 密文的证件号码解密，之后再对于证件号码作掩码处理。 掩码处理规则，身份证号码的前面 8 位与后面 4 位使用原文，其他位数使用
	 * "*" 表示
	 *
	 * @param base64CipherIdCard
	 *            Base64 密文的证件号码解密
	 * @return 掩码的证件号码
	 */
	public static String decryptAndMaskIdCard(String base64CipherIdCard) {
		if (base64CipherIdCard == null) {
			return null;
		}
		String plain = decrypt(base64CipherIdCard);
		if (plain == null) {
			return null;
		}
		return maskIdCard(plain);
	}

	/**
	 * 对于用户姓名作掩码处理。掩码处理规则，姓名保留第一个字符和最后一个字符，其他位数使用 "*" 表示
	 *
	 * @param realName
	 *            用户姓名
	 * @return 掩码的用户姓名
	 */
	public static String maskRealName(String realName) {
		if (realName == null || realName.length() < 2) {
			return realName;
		}
		if (realName.length() == 2) {
			char[] chs = realName.toCharArray();
			chs[1] = '*';
			return new String(chs);
		}
		return mask(realName, 0, 1);
	}

	/**
	 * 对于身份证号码作掩码处理。掩码处理规则，身份证号码的前面 4 位与后面 4 位使用原文，其他位数使用 "*" 表示
	 *
	 * @param idCard
	 *            身份证号码
	 * @return 掩码的身份证号码
	 */
	public static String maskIdCard(String idCard) {
		return mask(idCard, 1, 1);
	}

	/**
	 * 对于手机号码作掩码处理。掩码处理规则，手机号码的前面 3 位与后面 4 位使用原文，其他位数使用 "*" 表示
	 *
	 * @param mobile
	 *            手机号码
	 * @return 掩码的手机号码
	 */
	public static String maskMobile(String mobile) {
		return mask(mobile, 3, 2);
	}

	/**
	 * 掩码处理工具，保留指定数量的字符，其他字符以 "*" 替代。
	 *
	 * @param str
	 *            原文字符串
	 * @param before
	 *            原文中头部需要保留的字符数量
	 * @param after
	 *            原文中尾部需要保留的字符数量
	 * @return 掩码处理后的字符串。如果原文字符串长度小于等于头部与尾部保留的字符数量之和时，不作掩码处理
	 */
	public static String mask(String str, int before, int after) {
		if (!StringUtils.isNotEmpty(str)) {
			return str;
		}
		if (str.length() <= before + after) {
			return str;
		}
		char[] chs = str.toCharArray();
		for (int i = before, k = str.length() - after; i < k; i++) {
			chs[i] = MASK_CHAR;
		}
		return new String(chs);
	}

	private static Key loadKey() {
		try {
			String key = loadDefaultKey();
			byte[] bytes = Base64.decodeBase64(key);
			DESedeKeySpec spec = new DESedeKeySpec(bytes);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
			return factory.generateSecret(spec);
		} catch (Exception e) {
			log.error("Load key cause error", e);
			return null;
		}
	}

	private static String loadDefaultKey() {
		return DEFAULT_KEY;
	}

	protected static String loadResource() throws IOException {
		BufferedReader reader = null;
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(FastCodeUtils.class.getName());
			reader = new BufferedReader(new InputStreamReader(in));
			for (String line = null; (line = reader.readLine()) != null;) {
				line = line.trim();
				if (line.length() == 0) {
					continue;
				}
				if (line.startsWith("#")) {
					continue;
				}
				return line;
			}
			return null;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

}
