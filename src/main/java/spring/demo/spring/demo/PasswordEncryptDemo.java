package spring.demo.spring.demo;

import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class PasswordEncryptDemo {
	
	private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private static final String myEncryptionKey = "ThisIsSpartaThisIsSparta";
    private static KeySpec keySpec;
    private static SecretKeyFactory secretKeyFactory;
    private static Cipher cipher;
    static  byte[] arrayBytes;
    static SecretKey key;
    
    static {
    	try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static void init() throws Exception {
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        keySpec = new DESedeKeySpec(arrayBytes);
        secretKeyFactory = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);
        cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
        key = secretKeyFactory.generateSecret(keySpec);
    }


    public static String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = Base64.getEncoder().encodeToString(encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }


    public static String decrypt(String encryptedString) {
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.getDecoder().decode(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }


    public static void main(String args []) throws Exception
    {
        String target="password";
        String encrypted = encrypt(target);
        String decrypted = decrypt(encrypted);

        System.out.println("String To Encrypt: "+ target);
        System.out.println("Encrypted String:" + encrypted);
        System.out.println("Decrypted String:" + decrypted);

    }
}
