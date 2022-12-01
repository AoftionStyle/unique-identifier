package com.aoftion.unique.identifier.util.cryptography.cipher.configuration;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CipherCryptographyFactory {
    @Autowired
    CipherCryptoConfiguration secretKeyConfiguration;

    @Bean
    private SecretKeyFactory secretKeyCryptographyFactory() throws NoSuchAlgorithmException {
        String algorithm = secretKeyConfiguration.getAlgorithm().getFactory();
        return SecretKeyFactory.getInstance(algorithm);
    }

    public SecretKey getKeyFromPassword(String passwording, String salting) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(passwording.toCharArray(), salting.getBytes(), 65536, 256);
        SecretKeyFactory secretKeyFactory = secretKeyCryptographyFactory();
        SecretKey secret = secretKeyFactory.generateSecret(spec);
        SecretKey originalKey = new SecretKeySpec(secret.getEncoded(), secretKeyConfiguration.getAlgorithm().getSpec());
        return originalKey;
    }
}
