package com.aoftion.unique.identifier.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoftion.unique.identifier.dto.CipherText;
import com.aoftion.unique.identifier.dto.PlainText;
import com.aoftion.unique.identifier.restful.CryptographyIdentifierRestful;

@RestController
@RequestMapping("/aoftion/unique/identifier")
public class IdentifierRestController {
    @Autowired
    CryptographyIdentifierRestful cryptographyIdentifierRestful;

    @PostMapping(path = "/generate")
    private ResponseEntity<Object> generateIdentifier(@RequestHeader Map<String, String> requestHeaders, @RequestBody PlainText plainText) {
        return cryptographyIdentifierRestful.generate(requestHeaders, plainText);
    }

    @PutMapping(path = "/validate")
    private ResponseEntity<Object> validateIdentifier(@RequestHeader Map<String, String> requestHeaders, @RequestBody CipherText cipher_text) {
        return cryptographyIdentifierRestful.validate(requestHeaders, cipher_text);
    }
}
