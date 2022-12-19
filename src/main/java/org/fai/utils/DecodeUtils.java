package org.fai.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.fai.constants.FrameworkConstants;

/**
 * Helps to decode the base64 encoded string.
 * 
 */
public final class DecodeUtils {
	
	/**
	 * Private constructor to avoid external instantiation
	 */
//	private DecodeUtils() {}
	
	/**
	 * Accepts and base64 string,decode and return to the caller
	 * 
	 * @param encodedString Base64 encoded string
	 * @return String Decoded base64 string
	 */
	
	public static void main (String args[]) throws InvalidKeySpecException, NoSuchAlgorithmException, 
    IllegalBlockSizeException, InvalidKeyException, BadPaddingException, 
    InvalidAlgorithmParameterException, NoSuchPaddingException {
    
		String input = "Chinnu@1210";
		SecretKey key = DecodeUtils.getKeyFromPassword(input,FrameworkConstants.getSaltValue());
	    IvParameterSpec ivParameterSpec = DecodeUtils.generateIv();
	    String algorithm = "AES/CBC/PKCS5Padding";
	    String cipherText="PpsB+fuR61zVYDmPROxFmg=="; //= DecodeUtils.encrypt(algorithm, input, key, ivParameterSpec);
	    String plainText = DecodeUtils.decrypt(algorithm, cipherText, key, ivParameterSpec);
	    System.out.println("Cipher text "+cipherText);
	    System.out.println("Plain text "+plainText);
   
}
	public static String getDecodedString(String encodedString) {
		return new String(Base64.getDecoder().decode(encodedString.getBytes()));
	}
	public static SecretKey getKeyFromPassword(String password, String salt)
		    throws NoSuchAlgorithmException, InvalidKeySpecException {
		    
		    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
		        .getEncoded(), "AES");
		    return secret;
		}
	public static IvParameterSpec generateIv() {
	    byte[] iv = new byte[16];
	    new SecureRandom().nextBytes(iv);
	    return new IvParameterSpec(iv);
	}
	public static String encrypt(String algorithm, String input, SecretKey key,
		    IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
		    InvalidAlgorithmParameterException, InvalidKeyException,
		    BadPaddingException, IllegalBlockSizeException {
		    
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		    byte[] cipherText = cipher.doFinal(input.getBytes());
		    return Base64.getEncoder()
		        .encodeToString(cipherText);
		}
	public static String decrypt(String algorithm, String cipherText, SecretKey key,
		    IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
		    InvalidAlgorithmParameterException, InvalidKeyException,
		    BadPaddingException, IllegalBlockSizeException {
		    
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.DECRYPT_MODE, key, iv);
		    byte[] plainText = cipher.doFinal(Base64.getDecoder()
		        .decode(cipherText));
		    return new String(plainText);
		}

	
}
