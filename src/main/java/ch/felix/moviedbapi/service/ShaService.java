package ch.felix.moviedbapi.service;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Wetwer
 * @project hermann
 */
@Service
public class ShaService {

    public String encode(String s) {
        try {
            return getEncoded(s).toString();
        } catch (Exception ex) {
            new RuntimeException(ex).printStackTrace();
            return "";
        }
    }

    private StringBuffer getEncoded(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuffer hexString = new StringBuffer();
        for (byte hashA : getDigest(s)) {
            String hex = Integer.toHexString(0xff & hashA);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString;
    }

    private byte[] getDigest(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return MessageDigest.getInstance("SHA-256").digest(s.getBytes("UTF-8"));
    }
}
