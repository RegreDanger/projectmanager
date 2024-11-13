package domain.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashingUtils {
	private static final int SALT_LENGTH = 16;
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;
	
	public static byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_LENGTH];
		random.nextBytes(salt);
		return salt;
	}
	
	public static String hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] hash = factory.generateSecret(spec).getEncoded();
		return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
	}
	
	public static boolean compareHash(String str, String storedHash) throws NoSuchAlgorithmException, InvalidKeySpecException  {
		String[] parts = storedHash.split(":");
		byte[] salt = Base64.getDecoder().decode(parts[0]);
		byte[] hash = Base64.getDecoder().decode(parts[1]);
		KeySpec spec = new PBEKeySpec(str.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] testHash = factory.generateSecret(spec).getEncoded();
		
		return MessageDigest.isEqual(hash, testHash);
	}
	
}
