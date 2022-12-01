package com.aoftion.unique.identifier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import com.aoftion.unique.identifier.service.CryptographyIdentifier;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class IdentifierBatchController implements CommandLineRunner {
    @Autowired
    CryptographyIdentifier cryptographyIdentifier;

    @Override
    public void run(String... args) throws Exception {
        log.info("======================== BATCH Start ========================");
        cryptographyIdentifier.validate(cryptographyIdentifier.generate("Identifier"));
        log.info("======================== BATCH End ========================");
    }

}
