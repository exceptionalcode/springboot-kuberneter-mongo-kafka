package com.hunza.event.caterer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author ishaan.solanki
 * <p>
 * Class {@link CatererServiceApplication} is a boot start class to the caterer-service.
 * <p>It is an entry point to this spring boot caterer-service.</p>
 */
@EnableCaching
@SpringBootApplication
public class CatererServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatererServiceApplication.class, args);
    }

}
