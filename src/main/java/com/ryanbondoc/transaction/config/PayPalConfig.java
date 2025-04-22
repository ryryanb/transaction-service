package com.ryanbondoc.transaction.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode; // "sandbox" or "live"

    @Bean
    public PayPalHttpClient payPalClient() {
        PayPalEnvironment environment = "live".equalsIgnoreCase(mode)
                ? new PayPalEnvironment.Live(clientId, clientSecret)
                : new PayPalEnvironment.Sandbox(clientId, clientSecret);
        return new PayPalHttpClient(environment);
    }
}
