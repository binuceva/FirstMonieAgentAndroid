package firstmob.firstbank.com.firstagent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


import android.util.Base64;

/**
 * Aes encryption
 */
public class AESTest {

	private SecretKeySpec secretKey;
	private byte[] key;

	private String decryptedString;
	private String encryptedString;

	private String skey = "";

	//private static ResourceBundle resourceBundle = null;

	static {
		//resourceBundle = ResourceBundle.getBundle("config");
	}

	public void setKey(String sk) {

		skey = sk;

		MessageDigest sha = null;
		try {
			key = skey.getBytes("UTF-8");

			sha = MessageDigest.getInstance("SHA-512");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit

			secretKey = new SecretKeySpec(key, "AES");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	public String getDecryptedString() {
		return decryptedString;
	}

	public void setDecryptedString(String decryptedString) {
		this.decryptedString = decryptedString;
	}

	public String getEncryptedString() {
		return encryptedString;
	}

	public void setEncryptedString(String encryptedString) {
		this.encryptedString = encryptedString;
	}

	public String encrypt(String strToEncrypt) {
		Cipher cipher = null;
        String enctxt = null;

		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);


            enctxt = Base64.encodeToString(cipher
                    .doFinal(strToEncrypt.getBytes("UTF-8")), Base64.NO_WRAP);


		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return enctxt;
	}

	public String decrypt(String strToDecrypt) {
		Cipher cipher = null;
        String ptxt = null;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

			cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
		/*	ptxt =new String(cipher.doFinal(Base64
					.decodeBase64(strToDecrypt)));*/

		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return ptxt;
	}

	public static void main(String args[]) {
		String strToEncrypt = "1234";
		// strToEncrypt = "4015";
		 final String strPssword =
		"AeH6GrLRGK2SBtNiziAdl+Z9HK+98qChhGuCaLZ7O5M";
		AESTest aes = new AESTest();

		aes.setKey(strPssword);

		aes.encrypt(strToEncrypt.trim());
		//
		System.out.println("Encrypted: " + aes.getEncryptedString());
		//
		final String strToDecrypt = aes.getEncryptedString();
		aes.decrypt(strToDecrypt);

		// System.out.println("String To Decrypt : " + strToDecrypt);
		System.out.println("Decrypted : " + aes.getDecryptedString());

	}

}
