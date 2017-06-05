package firstmob.firstbank.com.firstagent;

/**
 * Created by deeru on 14-08-2015.
 */

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.*;
import javax.crypto.spec.*;
public class AES {

    private Cipher cipher;
    private static SecretKeySpec secretKey;
    private static SecretKeySpec appkey;
    private static byte[] key;

    public AES(byte [] keyraw) throws Exception{
        if(keyraw == null){
            byte[] bytesOfMessage = "".getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(bytesOfMessage);

            secretKey = new SecretKeySpec(bytes, "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        }
        else{

            secretKey = new SecretKeySpec(keyraw, "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        }
    }

    public AES(String myKey) throws Exception{
        /*byte[] bytesOfMessage = passphrase.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        skeySpec = new SecretKeySpec(thedigest, "AES");


        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");*/

        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            Log.v("Key length ====> " , Integer.toString(key.length));
            sha = MessageDigest.getInstance("SHA-512");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit

            secretKey = new SecretKeySpec(key, "AES");
            Log.d("Secret Key ====> " , secretKey.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public AES()throws Exception{
        byte[] bytesOfMessage = "".getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        secretKey = new SecretKeySpec(thedigest, "AES");

        secretKey = new SecretKeySpec(new byte[16], "AES");
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    }

    public String encrypt (byte[] plaintext) throws Exception{
        //returns byte array encrypted with key
Log.d("Encrypted Starts","");
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        String ciphertext =  Base64.encodeToString(cipher.doFinal(plaintext),Base64.NO_WRAP);



        return ciphertext;
    }

    public String decrypt (String ciphertext) throws Exception{
        //returns byte array decrypted with key
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        String ptxt =  new String(cipher.doFinal(Base64.decode(ciphertext,Base64.NO_WRAP)));

            return ptxt;
    }
}
