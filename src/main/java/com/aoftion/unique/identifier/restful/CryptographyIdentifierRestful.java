package com.aoftion.unique.identifier.restful;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.aoftion.unique.identifier.dto.CipherText;
import com.aoftion.unique.identifier.dto.PlainText;
import com.aoftion.unique.identifier.service.CryptographyIdentifier;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CryptographyIdentifierRestful {
    @Autowired
    private CryptographyIdentifier cryptographyIdentifier;

    @Value("${app.unique.identifier.x-api-key}")
    private String xApiKey;

    public ResponseEntity<Object> generate(Map<String, String> requestHeaders, PlainText plainText) {
        try {
            authorizeRequestHeader(requestHeaders);

            String cipherText = cryptographyIdentifier.generate(plainText.getPlain_text());
            return ResponseEntity.status(HttpStatus.OK)
                .header("X-Authorization", cipherText)
                .body("Successful");
        } catch (HttpClientErrorException ex) {
            log.error("HttpClientErrorException generate,", ex);
            RestfulError error = new RestfulError(HttpStatus.UNAUTHORIZED, "/generate", ex.getStatusText());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (Exception ex) {
            log.error("Exception generate", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown Error");
        }
    }

    public ResponseEntity<Object> validate(Map<String, String> requestHeaders, CipherText cipherText) {
        try {
            authorizeRequestHeader(requestHeaders);

            String plainText = cryptographyIdentifier.validate(cipherText.getCipher_text());
            return ResponseEntity.status(HttpStatus.OK)
                .header("X-Authorization", cipherText.getCipher_text())
                .body(plainText);
        } catch (HttpClientErrorException ex) {
            log.error("HttpClientErrorException validate", ex);
            RestfulError error = new RestfulError(HttpStatus.UNAUTHORIZED, "/validate", ex.getStatusText());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (Exception ex) {
            log.error("Exception validate", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    } 

    private void authorizeRequestHeader(Map<String, String> requestHeaders) {
        String invalidate = requestHeaders.get("x-api-key") == null ? "x-api-key" :
            requestHeaders.get("x-authorization") == null ? "x-authorization" : "null";
        log.debug("invalidate is {}", invalidate);

        if ( invalidate.equals("x-api-key") || requestHeaders.get("x-api-key").trim().isEmpty() ) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "required x-api-key");
        } else if ( !requestHeaders.get("x-api-key").equals(this.xApiKey) ) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "missed x-api-key");
        } else if ( invalidate.equals("x-aauthorization") || requestHeaders.get("x-authorization").trim().isEmpty() ) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "required x-authorization");
        }
    }
}
 