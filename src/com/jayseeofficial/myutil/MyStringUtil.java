package com.jayseeofficial.myutil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;


public abstract class MyStringUtil {

    public static String[] extendArray(String[] in) {
        String[] out = new String[in.length + 1];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }
        return out;
    }

    public static boolean containsArgument(String findee, String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase(findee)) {
                return true;
            }
        }
        return false;
    }
    
    public static String encryptString(String s, String encryption, String textEncoding) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        
        // Set up to process password
        MessageDigest md = MessageDigest.getInstance(encryption);
        
        // Set to digest our password
        md.update(s.getBytes(textEncoding));
        
        // Digest our password
        byte[] raw = md.digest();
        
        // Convert our digest to a string
        String hash = new BASE64Encoder().encode(raw);
        return hash;
    }
    
}