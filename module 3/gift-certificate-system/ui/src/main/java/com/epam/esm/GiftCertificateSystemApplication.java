package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com"})
@SpringBootApplication
public class GiftCertificateSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiftCertificateSystemApplication.class, args);
    }

}
