package com.aoftion.unique.identifier.restful;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.aoftion.unique.identifier.service.CryptographyIdentifier;
import com.aoftion.unique.identifier.util.cryptography.cipher.configuration.CipherCryptoConfiguration;

@ExtendWith(MockitoExtension.class)
public class CryptographyIdentifierRestfulTest_AuthorizeHeader {
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
    }

    @Test
    void testAuthrizeRequestHeader_XApiKeyIsNull() {
        Map<String, String> requestHeaderApiKey = new HashMap<String, String>();
        requestHeaderApiKey.put("x-api-key", null);

        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, 
            () -> cryptographyIdentifierRestful.authorizeRequestHeader(requestHeaderApiKey) );

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("Required x-api-key", thrown.getStatusText());
    }

    @Test
    void testAuthrizeRequestHeader_XApiKeyIsEmpty() {
        Map<String, String> requestHeaderApiKey = new HashMap<String, String>();
        requestHeaderApiKey.put("x-api-key", "  ");
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, 
            () -> cryptographyIdentifierRestful.authorizeRequestHeader(requestHeaderApiKey) );

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("Required x-api-key", thrown.getStatusText());
    }

    @Test
    void testAuthrizeRequestHeader_XApiKeyMissed() {
        Map<String, String> requestHeaderApiKey = new HashMap<String, String>();
        requestHeaderApiKey.put("x-api-key", "ABC");
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, 
            () -> cryptographyIdentifierRestful.authorizeRequestHeader(requestHeaderApiKey) );

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("Missed x-api-key", thrown.getStatusText());
    }

    @Test
    void testAuthrizeRequestHeader_XAuthorizationIsNull() {
        Map<String, String> requestHeaderApiKey = new HashMap<String, String>();
        requestHeaderApiKey.put("x-api-key", xApiKeyTest);
        requestHeaderApiKey.put("x-authorization", null);
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, 
            () -> cryptographyIdentifierRestful.authorizeRequestHeader(requestHeaderApiKey) );

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("Required x-authorization", thrown.getStatusText());
    }

    @Test
    void testAuthrizeRequestHeader_XAuthorizationIsEmpty() {
        Map<String, String> requestHeaderApiKey = new HashMap<String, String>();
        requestHeaderApiKey.put("x-api-key", xApiKeyTest);
        requestHeaderApiKey.put("x-authorization", "  ");
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, 
            () -> cryptographyIdentifierRestful.authorizeRequestHeader(requestHeaderApiKey) );

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("Required x-authorization", thrown.getStatusText());
    }
}
