package com.aoftion.unique.identifier.util.cryptography.cipher.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "cryptography.secret-key")
@Validated
public class CipherCryptoConfiguration {
    private CipherCryptoAlgorithmConfiguration algorithm;
    private String passwording;
    private String salting;
}
