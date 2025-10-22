package com.test.chakray.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordCipher {

    private static final String ALG = "AES";
    private static final String TRANS = "AES/GCM/NoPadding";
    private static final int GCM_TAG_BITS = 128;
    private static final int IV_LEN = 12;

    private final SecretKey key;
    private final SecureRandom rnd = new SecureRandom();

    public PasswordCipher(@Value("${security.aes256.key-base64}") String keyBase64) {
        byte[] k = Base64.getDecoder().decode(keyBase64);
        if (k.length != 32) {
            throw new IllegalArgumentException("AES-256 key must be 32 bytes (base64 decoded).");
        }
        this.key = new SecretKeySpec(k, ALG);
    }

    public String encrypt(String plain) {
        try {
            byte[] iv = new byte[IV_LEN];
            rnd.nextBytes(iv);
            Cipher c = Cipher.getInstance(TRANS);
            c.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_BITS, iv));
            byte[] ct = c.doFinal(plain.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            // iv:ciphertext base64
            return Base64.getEncoder().encodeToString(iv) + ":" + Base64.getEncoder().encodeToString(ct);
        } catch (Exception e) {
            throw new IllegalStateException("Encryption error", e);
        }
    }

    public String decrypt(String enc) {
        try {
            String[] parts = enc.split(":", 2);
            if (parts.length != 2) throw new IllegalArgumentException("Invalid cipher text format");
            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] ct = Base64.getDecoder().decode(parts[1]);
            Cipher c = Cipher.getInstance(TRANS);
            c.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_BITS, iv));
            byte[] pt = c.doFinal(ct);
            return new String(pt, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Decryption error", e);
        }
    }
}
