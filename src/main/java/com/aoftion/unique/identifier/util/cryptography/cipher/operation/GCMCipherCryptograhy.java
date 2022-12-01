package com.aoftion.unique.identifier.util.cryptography.cipher.operation;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import com.aoftion.unique.identifier.util.cryptography.cipher.CipherStringCryptography;
import com.aoftion.unique.identifier.util.cryptography.cipher.configuration.CipherCryptoConfiguration;
import com.aoftion.unique.identifier.util.cryptography.cipher.configuration.CipherCryptographyFactory;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GCMCipherCryptograhy implements CipherStringCryptography {
    CipherCryptoConfiguration cipherCryptoConfiguration;
    CipherCryptographyFactory cipherCryptographyFactory;

    private static final String algorithm = "AES/GCM/NoPadding";
    // byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    public GCMCipherCryptograhy(CipherCryptoConfiguration configuration, CipherCryptographyFactory factory) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.cipherCryptoConfiguration = configuration;
        this.cipherCryptographyFactory = factory;
        validate();
    }

    private void validate() {
        if ( this.cipherCryptoConfiguration == null ) {
            log.error("cipherCryptoConfiguration is null");
            throw new IllegalArgumentException("Required when `" + CipherCryptoConfiguration.class.getSimpleName() + "` is null");
        } else if (this.cipherCryptographyFactory == null ) {
            log.error("cipherCryptographyFactory is null");
            throw new IllegalArgumentException("Required when `" + CipherCryptographyFactory.class.getSimpleName() + "` is null");
        }
    }

    @Override
    public String encrypt(String plainText) throws Exception {
        log.debug("plainText: {}", plainText);
        SecretKey secretKey = cipherCryptographyFactory.getKeyFromPassword(cipherCryptoConfiguration.getPasswording(), cipherCryptoConfiguration.getSalting());
        AlgorithmParameterSpec parameterSpec = new GCMParameterSpec(128, cipherCryptoConfiguration.getPasswording().getBytes()); // 128 bit auth tag length
        byte[] cipherBytes = new CipherEncrypting().encrypt(plainText, algorithm, secretKey, parameterSpec);
        String cipherText = Base64.getEncoder().encodeToString(cipherBytes);
        log.debug("cipherText: {}", cipherText);
        return cipherText;
    }

    @Override
    public String decrypt(String cipherText) throws Exception {
        log.debug("cipherText: {}", cipherText);
        SecretKey secretKey = cipherCryptographyFactory.getKeyFromPassword(cipherCryptoConfiguration.getPasswording(), cipherCryptoConfiguration.getSalting());
        AlgorithmParameterSpec parameterSpec = new GCMParameterSpec(128, cipherCryptoConfiguration.getPasswording().getBytes()); // 128 bit auth tag length

        byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
        byte[] plainBytes = new CipherDecrypting().decrypt(cipherBytes, algorithm, secretKey, parameterSpec);
        String plainText = new String(plainBytes, StandardCharsets.UTF_8);
        log.debug("plainText: {}", plainText);
        return plainText;
    }
}
