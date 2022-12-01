package com.aoftion.unique.identifier.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aoftion.unique.identifier.util.cryptography.cipher.configuration.CipherCryptoConfiguration;
import com.aoftion.unique.identifier.util.cryptography.cipher.configuration.CipherCryptographyFactory;
import com.aoftion.unique.identifier.util.cryptography.cipher.operation.GCMCipherCryptograhy;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CryptographyIdentifier implements Identifierable<String, String> {
    @Autowired
    CipherCryptoConfiguration secretKeyConfiguration;

    @Autowired
    CipherCryptographyFactory secretKeyCryptography;

    @Override
    public String generate(String plainText) throws Exception {
        log.info("generate - plainText: {}", plainText);
        GCMCipherCryptograhy gcmCryptograhy = new GCMCipherCryptograhy(secretKeyConfiguration, secretKeyCryptography);
        String cipherText = gcmCryptograhy.encrypt(plainText); 
        String urlEncodeText = Base64.getUrlEncoder().withoutPadding().encodeToString(cipherText.getBytes());
        log.info("generate - encryptText: {}", cipherText);
        log.info("generate - urlEncodeText: {}", urlEncodeText);
        return urlEncodeText;
    }

    @Override
    public String validate(String urlEncodeText) throws Exception {
        log.info("validate - urlEncodeText : {}", urlEncodeText);
        String cipherText = new String(Base64.getUrlDecoder().decode(urlEncodeText.getBytes()), StandardCharsets.UTF_8);
        log.info("validate - encryptText: {}", cipherText);

        GCMCipherCryptograhy gcmCryptograhy = new GCMCipherCryptograhy(secretKeyConfiguration, secretKeyCryptography);
        String decrypString = gcmCryptograhy.decrypt(cipherText);
        log.info("validate - plainText: {}", decrypString);
        return decrypString;
    }
    
}
