package com.olabi.olabiflix.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.olabi.olabiflix.repository.FilmeRepository;

@Configuration
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
    private final FilmeRepository filmeRepository;

    public DatabaseInitializer(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Alo ? o banco tá conectado");
    }

}
