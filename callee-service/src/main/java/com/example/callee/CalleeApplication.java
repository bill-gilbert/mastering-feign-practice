package com.example.callee;
import com.example.callee.config.CalleeConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({CalleeConfig.class})
public class CalleeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalleeApplication.class, args);
    }
}