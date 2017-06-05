package security;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecurityImplementation {
    // private static final String CIPHER_ALG = "RSA/ECB/PKCS1Padding";
    // private static final String PKI_PROVIDER = "BC";
    // private static final String SIGNATURE_ALG = "SHA1withRSA";
    // private static final String KEYFACTORY_ALG = "RSA";
    // private static final String MESSAGEDGST_ALG= "SHA-256";

    //static String NET_URL = "https://mbanking.nationalbank.co.ke:8443";
 public static String NET_URL = "http://196.32.226.78:8010";
  public static X509EncodedKeySpec publicKeySpec = null;

  public static byte[] gh;

   {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String base64Encode(byte[] binaryData) {

        String ciphertext =  Base64.encodeToString((binaryData),0);
        return ciphertext;
    }

    public static byte[] base64Decode(String base64String) {
        //Base64.decode(base64String,0);
        return  Base64.decode(base64String,0);
    }

    private static FileReader getPVTKeyFile(File pvtFile) {
        FileReader pvtFileReader = null;
        try {
            pvtFileReader = new FileReader(pvtFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            pvtFileReader = null;
        }

        return pvtFileReader;
    }

    private static byte[] getPUBKeyData(String pubKeyFileName)
            throws IOException {

        try {
            //File pubKeyFile = new File(pubKeyFileName);
            String v = "http://196.32.226.78:8010/natmobileapi/public.der";
            URL url = new URL(v );

            InputStream pubKeyStream = url.openStream(); // new
            // FileInputStream(pubKeyFile);
            byte[] pubKey = new byte[512];
            pubKeyStream.read(pubKey);

            if (pubKeyStream != null) {
                pubKeyStream.close();
            }

            return pubKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class DownloadFileFromURL extends AsyncTask<String,String,String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // dialog = ProgressDialog.show(getActivity(), "", "Retrieving Contacts...please be patient,it might take some time", true);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            try {
                //File pubKeyFile = new File(pubKeyFileName);


                String v = NET_URL+"/nbkselfreg/public.der";
                URL url = new URL(v );

                InputStream pubKeyStream = url.openStream(); // new
                // FileInputStream(pubKeyFile);
                byte[] pubKey = new byte[512];
                pubKeyStream.read(pubKey);

                if (pubKeyStream != null) {
                    pubKeyStream.close();
                }

                gh = pubKey;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }



        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            if(!(gh == null)) {
                publicKeySpec = new X509EncodedKeySpec(gh);
            }else{
                Log.d("Public Key Not Found","Not Found") ;
            }


        }

    }

    /* private */public static PublicKey getPublicKey(String pubKeyFileName,
                                                      String keyFactoryAlg, String pkiProvider)
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidKeySpecException, IOException {

        new DownloadFileFromURL().execute();

        KeyFactory keyFactory = KeyFactory.getInstance(keyFactoryAlg,
                pkiProvider);
        if(!(publicKeySpec == null)) {
            Log.d("public key spec", publicKeySpec.toString());
        }
        return keyFactory.generatePublic(publicKeySpec);
    }

    private static PrivateKey getPrivateKey(String pvtKeyFileName)
            throws IOException {
        FileReader pvtFileReader = getPVTKeyFile(new File(pvtKeyFileName));
        PEMReader pvtPemReader = getPvtPemReader(pvtFileReader);
        KeyPair keyPair = (KeyPair) pvtPemReader.readObject();

        pvtPemReader.close();
        pvtFileReader.close();
        pvtPemReader = null;
        pvtFileReader = null;

        return keyPair.getPrivate();

    }

    public static String encrypt(String encrptedData, PublicKey pubKey,
                                 String cipherAlg, String pkiProvider)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchProviderException {

        byte[] encryptionByte = null;
        Cipher encryptCipher = Cipher.getInstance(cipherAlg, pkiProvider);
        encryptCipher.init(Cipher.ENCRYPT_MODE, pubKey);
        encryptionByte = encryptCipher.doFinal(encrptedData.getBytes());
        return base64Encode(encryptionByte);

    }

    public static byte[] decryptSessionKey(String decrptedData,
                                           String pvtKeyFileName, String cipherAlg, String pkiProvider)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchProviderException, IOException {

        byte[] decryptionByte = null;
        PrivateKey privateKey = getPrivateKey(pvtKeyFileName);
        Cipher decryptCipher = Cipher.getInstance(cipherAlg, pkiProvider);
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        decryptionByte = decryptCipher.doFinal(base64Decode(decrptedData));
        return decryptionByte;

    }

    public static byte[] decryptResponseWithSessionKey(byte[] sessionKey,
                                                       String symetricKeyAlg, String pkiProvider, String encXML)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchProviderException {

        SecretKeySpec symmKeySpec = new SecretKeySpec(sessionKey,
                symetricKeyAlg);
        Cipher symmCipher = Cipher.getInstance(symetricKeyAlg, pkiProvider);
        symmCipher.init(Cipher.DECRYPT_MODE, symmKeySpec);
        byte[] xmlData = symmCipher.doFinal(base64Decode(encXML));
        return xmlData;

    }

    private static PEMReader getPvtPemReader(Reader pvtFile) {

        return new PEMReader(pvtFile);

    }

    public static byte[] generateSessionKey(String keyGeneratorAlg,
                                            String pkiProvider, int symetricKeySize)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator kgen = KeyGenerator.getInstance(keyGeneratorAlg,
                pkiProvider);// this.getEncryptionStrategy());
        kgen.init(symetricKeySize);
        SecretKey key = kgen.generateKey();
        byte[] symmKey = key.getEncoded();
        return symmKey;
    }

    public static String encryptSessionKey(byte[] sessionKey,
                                           String pubKeyFileName, String cipherAlg, String keyFactoryAlg,
                                           String pkiProvider) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException,
            NoSuchProviderException, InvalidKeySpecException, IOException {
        byte[] encryptionByte = null;
        PublicKey pubKey = getPublicKey(pubKeyFileName, keyFactoryAlg,
                pkiProvider);
        Cipher encryptCipher = Cipher.getInstance(cipherAlg, pkiProvider);
        encryptCipher.init(Cipher.ENCRYPT_MODE, pubKey);
        encryptionByte = encryptCipher.doFinal(sessionKey);
        return base64Encode(encryptionByte);
    }

    public static String encryptRequestWithSessionKey(byte[] sessionKey,
                                                      String symetricKeyAlg, String pkiProvider, byte[] xmlRequestData)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchProviderException {
        SecretKeySpec symmKeySpec = new SecretKeySpec(sessionKey,
                symetricKeyAlg);
        Cipher symmCipher = Cipher.getInstance(symetricKeyAlg, pkiProvider);
        symmCipher.init(Cipher.ENCRYPT_MODE, symmKeySpec);
        byte[] encXMLPidData = symmCipher.doFinal(xmlRequestData);
        return base64Encode(encXMLPidData);
    }

}
