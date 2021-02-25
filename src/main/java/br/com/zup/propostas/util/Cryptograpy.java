package br.com.zup.propostas.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Cryptograpy {
	
	private final String ALGORITHM = "AES/ECB/PKCS5Padding";
	
    @Value("${encryptor.secret.key}")
    private String KEY;

    public String encode(String value) {
        Key key = new SecretKeySpec(KEY.getBytes(), "AES");
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            final String encrypted = new String(Base64.encodeBase64(c.doFinal(value.getBytes())), "UTF-8");
            return encrypted;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decode(String value) {
    	
        Key key = new SecretKeySpec(KEY.getBytes(), "AES");
        
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            final String decrypted = new String(c.doFinal(Base64.decodeBase64(value.getBytes("UTF-8"))));
            return decrypted;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}