/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年1月25日 下午5:10:41
 * @version 1.0.0
 */
public final class RSAUtils {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private static final int MAX_ENCRYPT_BLOCK = 117;// RSA最大加密明文大小

	private static final int MAX_DECRYPT_BLOCK = 128;// RSA最大解密密文大小

	// public static String pubEncrypt(String pubKeyFilePath, String plaintext)
	// throws Exception {
	// PublicKey publicKey = RSAKeyUtils.getPublicKey(pubKeyFilePath);
	// Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
	// cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	// byte[] result2 = cipher.doFinal(plaintext.getBytes());
	// return Base64.encodeBase64String(result2);
	// }
	//
	// public static String pubDecrypt(String pubKeyFilePath, String ciphertext)
	// throws Exception {
	// PublicKey publicKey = RSAKeyUtils.getPublicKey(pubKeyFilePath);
	// Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
	// cipher.init(Cipher.DECRYPT_MODE, publicKey);
	// byte[] ciphertextByte = Base64.decodeBase64(ciphertext);
	// byte[] result = cipher.doFinal(ciphertextByte);
	// return new String(result);
	// }
	//
	// public static String priEncrypt(String priKeyFilePath, String plaintext)
	// throws Exception {
	// PrivateKey privateKey = RSAKeyUtils.getPrivateKey(priKeyFilePath);
	// Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
	// cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	// byte[] result = cipher.doFinal(plaintext.getBytes());
	// return Base64.encodeBase64String(result);
	// }
	//
	// public static String priDecrypt(String priKeyFilePath, String ciphertext)
	// throws Exception {
	// PrivateKey privateKey = RSAKeyUtils.getPrivateKey(priKeyFilePath);
	// Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
	// cipher.init(Cipher.DECRYPT_MODE, privateKey);
	// byte[] ciphertextByte = Base64.decodeBase64(ciphertext);
	// byte[] result = cipher.doFinal(ciphertextByte);
	// return new String(result);
	// }

	public static String pubEncrypt(String pubKeyFilePath, String plaintext) throws Exception {
		PublicKey publicKey = RSAKeyUtils.getPublicKey(pubKeyFilePath);
		// 对数据加密
		byte[] data = plaintext.getBytes(DEFAULT_CHARSET);
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return Base64.encodeBase64String(encryptedData);
	}

	public static String pubDecrypt(String pubKeyFilePath, String ciphertext) throws Exception {
		PublicKey publicKey = RSAKeyUtils.getPublicKey(pubKeyFilePath);
		// 对数据解密
		byte[] data = Base64.decodeBase64(ciphertext);
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData, DEFAULT_CHARSET);
	}

	public static String priEncrypt(String priKeyFilePath, String plaintext) throws Exception {
		Key privateKey = RSAKeyUtils.getPrivateKey(priKeyFilePath);
		// 对数据加密
		byte[] data = plaintext.getBytes(DEFAULT_CHARSET);
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return Base64.encodeBase64String(encryptedData);
	}

	public static String priDecrypt(String priKeyFilePath, String ciphertext) throws Exception {
		PrivateKey privateKey = RSAKeyUtils.getPrivateKey(priKeyFilePath);
		// 对数据解密
		byte[] data = Base64.decodeBase64(ciphertext);
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData, DEFAULT_CHARSET);
	}

}
