package spring.demo.spring.demo;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

	private static Cipher cipher;

	public PasswordUtils() throws NoSuchAlgorithmException, NoSuchPaddingException {
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	}

	private static String encode(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
		char[] chars = password.toCharArray();
        byte[] salt = getSalt();
         
        PBEKeySpec spec = new PBEKeySpec(chars, salt, 1000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKey = factory.generateSecret(spec);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return new String(hash);
    }

	private static String decode(String encodedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
		char[] chars = encodedPassword.toCharArray();
		byte[] salt = getSalt();
		
		PBEKeySpec spec = new PBEKeySpec(chars, salt, 1000, 256);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey secretKey = factory.generateSecret(spec);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] hash = factory.generateSecret(spec).getEncoded();
		return new String(hash);
	}
	
	private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
		String encodeed = encode("password");
		System.out.println(encodeed);
	}
	
}
