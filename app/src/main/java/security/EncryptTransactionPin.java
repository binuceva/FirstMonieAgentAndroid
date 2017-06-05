package security;

import javax.crypto.Cipher;


public class EncryptTransactionPin {
	public static final String key ="97206B46CE46376894703ECE161F31F2";

	public static String encrypt(String key, String password, char pad) throws Exception
	{

		byte [] byteKey = hex2byte(key);
		byte [] bytePwd = hex2byte(rightPadding(password, 16, pad));
		byte[] enc = CryptoUtil.desede(bytePwd,byteKey,Cipher.ENCRYPT_MODE);

		return Util.hexString(enc);
	}

	public static String encrypt(String password, char pad) throws Exception
	{

		byte [] byteKey = hex2byte(key);
		byte [] bytePwd = hex2byte(rightPadding(password, 16, pad));
		byte[] enc = CryptoUtil.desede(bytePwd,byteKey,Cipher.ENCRYPT_MODE);

		return Util.hexString(enc);
	}

	public static String decrypt(String password) throws Exception
	{

		byte [] byteKey = hex2byte(key);
		byte [] bytePwd = hex2byte(password);
		byte[] enc = CryptoUtil.desede(bytePwd,byteKey,Cipher.DECRYPT_MODE);

		return Util.hexString(enc);
	}

	public static String decrypt(String key, String password) throws Exception
	{

		byte [] byteKey = hex2byte(key);
		byte [] bytePwd = hex2byte(password);
		byte[] enc = CryptoUtil.desede(bytePwd,byteKey,Cipher.DECRYPT_MODE);

		return Util.hexString(enc);
	}

	public static byte[] hex2byte (String s) {
	    if (s.length() % 2 == 0) {
	        return hex2byte (s.getBytes(), 0, s.length() >> 1);
	    } else {
	    	// Padding left zero to make it even size #Bug raised by tommy
	    	return hex2byte("0"+s);
	    }
	}
	public static byte[] hex2byte (byte[] b, int offset, int len) {
	    byte[] d = new byte[len];
	    for (int i=0; i<len*2; i++) {
	        int shift = i%2 == 1 ? 0 : 4;
	        d[i>>1] |= Character.digit((char) b[offset+i], 16) << shift;
	    }
	    return d;
	}

	public static String rightPadding(String data, int len, char ch)
	{

		StringBuilder sb = new StringBuilder();
		sb.append(data);
		for (int i = 0; i < len-data.length(); i++) {
			sb.append(ch);
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		String key = "97206B46CE46376894703ECE161F31F2";
		String password = "12344321";
		String decryptPassword = "7180917ECEB87909";
		try {
			System.out.println(encrypt(key, password, 'F'));
			System.out.println(decrypt(key, decryptPassword));
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}
	}
}
