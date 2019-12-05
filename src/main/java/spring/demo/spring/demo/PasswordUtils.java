package spring.demo.spring.demo;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class PasswordUtils {

	private static Cipher cipher;
	private static SecretKey secretKey;
	static {
		try {
			init();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	public static void init() throws NoSuchAlgorithmException, NoSuchPaddingException {
		cipher = Cipher.getInstance("DESede");
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
		keyGenerator.init(168);
		secretKey = keyGenerator.generateKey();
	}

	private static String encode(String password) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] passwordBytes = password.getBytes();

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encodedPassword = cipher.doFinal(passwordBytes);
        return new String(encodedPassword);
    }

	private static String decode(String encodedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] chars = encodedPassword.getBytes();

		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		cipher.doFinal(chars);
		return new String(chars);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String encoded = PasswordUtils.encode("password");
		String decoded = PasswordUtils.decode(encoded);
		System.out.println(encoded);
		System.out.println(decoded);
	}
	
}
