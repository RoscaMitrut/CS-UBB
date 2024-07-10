package utils;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
public class Crypt {
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
	private static final String SECRET_KEY = "Kds9GJksdi3fnaGR";

	public static String encrypt(String input) {
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedBytes = cipher.doFinal(input.getBytes());
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(String encryptedInput) {
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedInput));
			return new String(decryptedBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
