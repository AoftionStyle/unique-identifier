package com.aoftion.unique.identifier.util.cryptography.cipher;

import com.aoftion.unique.identifier.util.cryptography.Cryptographyable;

public interface CipherStringCryptography extends Cryptographyable<String, String> {

    @Override
    public String encrypt(String plainText) throws Exception;

    @Override
    public String decrypt(String cipherText) throws Exception;
}
