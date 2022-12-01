package com.aoftion.unique.identifier.util.cryptography.cipher.operation;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class CipherDecrypting {

    protected byte[] decrypt(byte[] cipherBytes, String algorithm, SecretKey secretKey, AlgorithmParameterSpec parameterSpec)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
        byte[] plianBytes = cipher.doFinal(cipherBytes);
        return plianBytes;
    }
}
