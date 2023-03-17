package deepak.com.Onboarding.encryption;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

@Component
public class RSA {

	private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMMwqCgoC7lrNpBZLjojKtQHs1P6saf7kgwTKqg3RO8VkTE2GOWE65PlIXC49wCh4ZdHpPFG4iVzoFxYFlDx4CGiAUSse6A51VPffm+62EWFVBnYIPq8wnzsO6chFOU8eyuzCNDPv/wR7cEihV9a+mWk698pha+lkk4nzhzB57YPAgMBAAECgYEAwtIc9T/LO/3kDz3EIykYxVsORcMScY1qn3y99fp8ZnpLWigZv3TlQc/QiddESrBJH5rV6jEK5cC8DY+qbo6NWqRjZTYkfaRgxnII6DZ3KLlk3huDPmEcfZWWXXLcgbEb35J7mgVZQg2yuAWnOTEOIseKbueAMZpgeY8JDnYhWCECQQD0879TAeQ4T2woyADtX2rpOhpVKYGPpsiGfjYMgX/uzNE1ZOcPxbrFTWx2aypV0VpJKZ8xEIxqh06PNXRcpv0xAkEAy/5ZddmqUCdEa3HqopjhzwHdajQ5eSVeWFEK9kji829tk3Q4VMh4+mwP1qhof52NgJy+gVHMJaGiCSIc8RAXPwJARmqr3Zj1mpp6ZV7ZpWOAwHtLhp/f9wPjMYam0wGHXYbTvZ28LvSQtJeFTqWQDguZAN9OPDSdqSIsm06qnNgEgQJALxQrZ1RtxwPY+YcX5KCt9I10oS8eWIQ0SuOhSQe3QQFlY5N98Ks3qGvKF/tT6qfc4WLLYKMVH1Y3RC5YSZYyFwJAVMS5Ig93iUTWI2+t3HOp50WFaRjtm/8vQh+fIY028osq0/tWMUgVQ6LM6ZOOrJSftGr4KQePBc2G9AHYhUh9Sw==";

	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDMKgoKAu5azaQWS46IyrUB7NT+rGn+5IMEyqoN0TvFZExNhjlhOuT5SFwuPcAoeGXR6TxRuIlc6BcWBZQ8eAhogFErHugOdVT335vuthFhVQZ2CD6vMJ87DunIRTlPHsrswjQz7/8Ee3BIoVfWvplpOvfKYWvpZJOJ84cwee2DwIDAQAB";

	public String decrypt(String toDecode) throws Exception {

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, getParsedPrivatecKey(PRIVATE_KEY));

		byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(toDecode));
		return new String(bytes);

	}

	public PublicKey getParsedPublicKey(String publicKey) {
		try {
			publicKey = publicKey.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "")
					.replace("-----END PUBLIC KEY-----", "");
			publicKey = publicKey.trim();
			// byte[] keyDecoded = Base64.getDecoder().decode(publicKey.getBytes());
			X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PublicKey pubKey = kf.generatePublic(publicSpec);
			return pubKey;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	public String encrypt(String toEncode) throws Exception {

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, getParsedPublicKey(PUBLIC_KEY));
		byte[] encryptedData = cipher.doFinal(toEncode.getBytes(StandardCharsets.UTF_8));
		return new String(Base64.getEncoder().encode(encryptedData));
	}

	public PrivateKey getParsedPrivatecKey(String privateKey) {

		try {
			privateKey = privateKey.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "")
					.replace("-----END PRIVATE KEY-----", "");
			// privateKey = privateKey.replace("-----BEGIN PUBLIC KEY-----", "");
			// privateKey = privateKey.replace("-----END PUBLIC KEY-----", "");
			// privateKey = privateKey.replace("-----BEGIN PRIVATE KEY-----", "");
			// privateKey = privateKey.replace("-----END PRIVATE KEY-----", "");
			privateKey = privateKey.trim();
			// byte[] keyDecoded = Base64.getDecoder().decode(publicKey.getBytes());
			PKCS8EncodedKeySpec publicSpec = new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(privateKey));
			KeyFactory kf = KeyFactory.getInstance("RSA");
			RSAPrivateKey pubKey = (RSAPrivateKey) kf.generatePrivate(publicSpec);
			return pubKey;
		} catch (Exception e) {

			return null;
		}
	}

}