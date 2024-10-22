package com.ga.springboothw;


import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class JavaDevConfig {
    @PostConstruct
    public void test() {
        System.out.println("Loading Dev Profile");
    }
}
