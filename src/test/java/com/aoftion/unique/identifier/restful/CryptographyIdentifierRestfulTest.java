package com.aoftion.unique.identifier.restful;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aoftion.unique.identifier.dto.CipherText;
import com.aoftion.unique.identifier.dto.PlainText;
import com.aoftion.unique.identifier.service.CryptographyIdentifier;
import com.aoftion.unique.identifier.util.cryptography.cipher.configuration.CipherCryptoConfiguration;

@ExtendWith(MockitoExtension.class)
public class CryptographyIdentifierRestfulTest {
    @InjectMocks
    CryptographyIdentifierRestful cryptographyIdentifierRestful;

    @Mock
    CryptographyIdentifier cryptographyIdentifier;

    @Mock
    CipherCryptoConfiguration configuration;

    Map<String, String> requestHeader = new HashMap<String, String>();
    String xApiKeyTest = "APIKEYTEST";

    @BeforeEach
    void setup() throws Exception {
        cryptographyIdentifierRestful.xApiKey = xApiKeyTest;
        requestHeader.put("x-api-key", xApiKeyTest);
        requestHeader.put("x-authorization", "x-auth");
    }

    @Test
    void testGenerateSuccess() throws Exception {
        PlainText plainText = new PlainText();
        plainText.setPlain_text("test");

        when(cryptographyIdentifier.generate("test")).thenReturn("test_encrypted");

        ResponseEntity<Object> actual = cryptographyIdentifierRestful.generate(requestHeader, plainText);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals("Successful", actual.getBody());
    }

    @Test
    void testGenerateHTTPErrorClient() {
        PlainText plainText = new PlainText();
        plainText.setPlain_text("test");

        ResponseEntity<Object> actual = cryptographyIdentifierRestful.generate(new HashMap<>(), plainText);
        assertEquals(HttpStatus.UNAUTHORIZED, actual.getStatusCode());
    }

    @Test
    void testGenerateException() throws Exception {
        PlainText plainText = new PlainText();
        plainText.setPlain_text("test");

        when(cryptographyIdentifier.generate("test")).thenThrow(NullPointerException.class);

        ResponseEntity<Object> actual = cryptographyIdentifierRestful.generate(requestHeader, plainText);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }

    @Test
    void testValidateSuccess() throws Exception {
        CipherText cipherText = new CipherText();
        cipherText.setCipher_text("test_encrypted");

        when(cryptographyIdentifier.validate("test_encrypted")).thenReturn("test");
        
        ResponseEntity<Object> actual = cryptographyIdentifierRestful.validate(requestHeader, cipherText);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals("test", actual.getBody());
    }

    @Test
    void testValidateHTTPErrorClient() {
        CipherText cipherText = new CipherText();
        cipherText.setCipher_text("test_encrypted");

        ResponseEntity<Object> actual = cryptographyIdentifierRestful.validate(new HashMap<>(), cipherText);
        assertEquals(HttpStatus.UNAUTHORIZED, actual.getStatusCode());
    }

    @Test
    void testValidateException() throws Exception {
        CipherText cipherText = new CipherText();
        cipherText.setCipher_text("test_encrypted");

        when(cryptographyIdentifier.validate("test_encrypted")).thenThrow(NullPointerException.class);

        ResponseEntity<Object> actual = cryptographyIdentifierRestful.validate(requestHeader, cipherText);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }
}
