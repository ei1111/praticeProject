package notice.pratice.utils;

import notice.pratice.exception.checkedException.DecodingException;
import notice.pratice.exception.checkedException.EncodingException;
import notice.pratice.exception.checkedException.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesClass {
    private SecretKeySpec secretKey;
    private IvParameterSpec IV;
    public static final String reqSecretKey = "aeskey1234567898";
    public static final String iv = "aesiv12345678912";

    public AesClass() {
        try {
            //바이트 배열로부터 SecretKey를 구축
            this.secretKey = new SecretKeySpec(reqSecretKey.getBytes("UTF-8"), "AES");
            this.IV = new IvParameterSpec(iv.getBytes());
        } catch (Exception e) {
            throw new UnsupportedEncodingException(e.toString(), "500");
        }
    }

    //AES CBC PKCS5Padding 암호화(Hex | Base64)
    public String AesCBCEncode(String plainText)  throws EncodingException {
        try {
            //Cipher 객체 인스턴스화(Java에서는 PKCS#5 = PKCS#7이랑 동일)
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");

            //Cipher 객체 초기화
            c.init(Cipher.ENCRYPT_MODE, secretKey, IV);

            //Encrpytion/Decryption
            byte[] encrpytionByte = c.doFinal(plainText.getBytes("UTF-8"));

            //Hex Encode
            return Hex.encodeHexString(encrpytionByte);
        } catch (Exception e) {
            throw new EncodingException(e.toString(), "500");
        }
    }

    //AES CBC PKCS5Padding 복호화(Hex | Base64)
    public String AesCBCDecode(String encodeText) {
        try {
            //Cipher 객체 인스턴스화(Java에서는 PKCS#5 = PKCS#7이랑 동일)
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");

            //Cipher 객체 초기화
            c.init(Cipher.DECRYPT_MODE, secretKey, IV);

            //Decode Hex
            byte[] decodeByte = Hex.decodeHex(encodeText.toCharArray());

            return new String(c.doFinal(decodeByte), "UTF-8");
        } catch (Exception e) {
            throw new DecodingException(e.toString(), "500");
        }
    }

    //암호화
    public static String encrypt(String str){
        AesClass aesClass = new AesClass();
        return aesClass.AesCBCEncode(str);
    }

    //복호화
    public static String decrypt(String str){
        AesClass aesClass = new AesClass();
        return aesClass.AesCBCDecode(str);
    }
}
