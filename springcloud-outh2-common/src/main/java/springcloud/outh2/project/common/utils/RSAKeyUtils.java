/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * <p>
 * RAS秘钥工具
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年10月25日 下午7:05:25
 * @version 1.0.0
 */
@Slf4j
public class RSAKeyUtils {

	private static final String ALGORITHM_RSA = "RSA";

	/**
	 * 获取公钥
	 *
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String filePath) throws Exception {
		File f = new File(filePath);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(ALGORITHM_RSA);
		return kf.generatePublic(spec);
	}

	/**
	 * 获取私钥
	 *
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String filePath) throws Exception {
		File f = new File(filePath);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(ALGORITHM_RSA);
		return kf.generatePrivate(spec);
	}

	/**
	 * 生存公钥和私钥
	 *
	 * @param publicKeyFilename
	 * @param privateKeyFilename
	 * @param password
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static void generateKey(String publicKeyFilename, String privateKeyFilename, String password) throws IOException, NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
		SecureRandom secureRandom = new SecureRandom(password.getBytes());
		keyPairGenerator.initialize(1024, secureRandom);
		KeyPair keyPair = keyPairGenerator.genKeyPair();
		byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
		log.debug("publicKey:{}", new String(Base64.getEncoder().encode(publicKeyBytes)));
		FileOutputStream fos = new FileOutputStream(publicKeyFilename);
		fos.write(publicKeyBytes);
		fos.close();
		byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
		log.debug("privateKey:{}", new String(Base64.getEncoder().encode(privateKeyBytes)));
		fos = new FileOutputStream(privateKeyFilename);
		fos.write(privateKeyBytes);
		fos.close();
	}
}
