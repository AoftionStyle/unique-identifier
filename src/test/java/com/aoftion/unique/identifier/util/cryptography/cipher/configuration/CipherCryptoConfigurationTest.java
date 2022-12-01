package com.aoftion.unique.identifier.util.cryptography.cipher.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class CipherCryptoConfigurationTest {

    @Configuration
    static class ContextConfiguration {
        // this bean will be injected into the Bean class
        @Bean
        public CipherCryptoConfiguration cipherCryptoConfiguration() {
            CipherCryptoConfiguration cipherCryptoConfiguration = new CipherCryptoConfiguration();
            CipherCryptoAlgorithmConfiguration cipherCryptoAlgorithmConfiguration = new CipherCryptoAlgorithmConfiguration();
            cipherCryptoAlgorithmConfiguration.setFactory("factory");
            cipherCryptoAlgorithmConfiguration.setSpec("spec");
            cipherCryptoConfiguration.setAlgorithm(cipherCryptoAlgorithmConfiguration);
            cipherCryptoConfiguration.setPasswording("paswording");
            cipherCryptoConfiguration.setSalting("salting");
            // set properties, etc.
            return cipherCryptoConfiguration;
        }
    }

    @Autowired
    CipherCryptoConfiguration cipherCryptoConfiguration;

    @Test
    void testGetFactory() {
        assertEquals("factory", cipherCryptoConfiguration.getAlgorithm().getFactory());
    }

    @Test
    void testGetSpec() {
        assertEquals("spec", cipherCryptoConfiguration.getAlgorithm().getSpec());
    }

    @Test
    void testGetPasswording() {
        assertEquals("paswording", cipherCryptoConfiguration.getPasswording());
    }

    @Test
    void testGetSalting() {
        assertEquals("salting", cipherCryptoConfiguration.getSalting());
    }
}
