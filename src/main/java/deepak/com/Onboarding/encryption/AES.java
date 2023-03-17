package deepak.com.Onboarding.encryption;

import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

@Component
public class AES {

	private static final Logger logger = LoggerFactory.getLogger(AES.class);

	private static final String CHARSET_NAME = "UTF-8";
	private static final String AES_NAME = "AES";
	public static final String ALGORITHM = "AES/CBC/PKCS7Padding";
	public static final String KEY = "07914953791eb0681f871d8cec17a279";
	// UAT KEY
	public static final String KEY2 = "07914953791eb0681f871d8cec17a380";
	// PROD KEY
//	public static final String KEY2 = "07914953791eb0681f871d8cec18a480";
	public static final String IV = "0000000000000000";
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static String encrypt(@NotNull String content) {
		logger.info(" Inside AES :  encrypt : content : " + content);
		if (content == null || content.isEmpty()) {
			return null;
		}
		byte[] result = null;
		try {
			logger.info(" Inside AES :  encrypt : Inside try");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(CHARSET_NAME), AES_NAME);
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
			result = cipher.doFinal(content.getBytes(CHARSET_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" Inside AES :  encrypt : result : " + result);
		logger.info(" Inside AES :  encrypt : resultString : " + result.toString());
		return Base64.getEncoder().encodeToString(result);
	}

	public static String decrypt(@NotNull String content) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(CHARSET_NAME), AES_NAME);
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(content)), CHARSET_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encrypt2(@NotNull String content) {
		logger.info(" Inside AES :  encrypt : content : " + content);
		if (content == null || content.isEmpty()) {
			return null;
		}
		byte[] result = null;
		try {
			logger.info(" Inside AES :  encrypt : Inside try");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(KEY2.getBytes(CHARSET_NAME), AES_NAME);
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
			result = cipher.doFinal(content.getBytes(CHARSET_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" Inside AES :  encrypt : result : " + result);
		logger.info(" Inside AES :  encrypt : resultString : " + result.toString());
		return Base64.getEncoder().encodeToString(result);
	}

	public static String decrypt2(@NotNull String content) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(KEY2.getBytes(CHARSET_NAME), AES_NAME);
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(content)), CHARSET_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String args[]) {
		System.out.print(AES.decrypt2("RDWM69nePEX+GqXZD+p8Ww=="));
	}
}
