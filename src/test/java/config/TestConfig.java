package config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.whatsappbackendtest")
public class TestConfig {
    public static void main(String[] args) {
        SpringApplication.run(TestConfig.class, args);
    }
}
