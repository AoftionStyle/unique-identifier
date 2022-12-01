package com.aoftion.unique.identifier.util.cryptography.cipher.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aoftion.unique.identifier.util.cryptography.cipher.configuration.CipherCryptoConfiguration;
import com.aoftion.unique.identifier.util.cryptography.cipher.configuration.CipherCryptographyFactory;

@ExtendWith(MockitoExtension.class)
public class GCMCipherCryptograhyTest {
    @Mock
    CipherCryptoConfiguration configuration;
    @Mock
    CipherCryptographyFactory factory;

    @InjectMocks
    GCMCipherCryptograhy gcmCipherCryptograhy;

    // @BeforeAll
    // void setup() throws NoSuchAlgorithmException, InvalidKeySpecException {
    //     gcmCipherCryptograhy = new GCMCipherCryptograhy(null, null);
    // }

    @Test
    void testValidateConfiguration() throws NoSuchAlgorithmException, InvalidKeySpecException {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> 
            new GCMCipherCryptograhy(null, null));
        assertEquals("Required when `CipherCryptoConfiguration` is null", thrown.getMessage());
    }

    @Test
    void testValidateFactory() throws NoSuchAlgorithmException, InvalidKeySpecException {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> 
            new GCMCipherCryptograhy(configuration, null));
        assertEquals("Required when `CipherCryptographyFactory` is null", thrown.getMessage());
    }
}
