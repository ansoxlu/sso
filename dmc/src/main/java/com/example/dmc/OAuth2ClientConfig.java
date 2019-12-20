package com.example.dmc;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {
    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Bean
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setClientId("webapp");
        resourceDetails.setAccessTokenUri("http://localhost:8762/oauth/token");
        resourceDetails.setClientSecret("123456");
        resourceDetails.setScope(Arrays.asList("all"));
        return resourceDetails;
    }
//    @Bean
//    public RequestInterceptor oauth2FeignRequestInterceptor() {
//        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
//    }
//
//    @Bean
//    public OAuth2RestTemplate clientCredentialsRestTemplate() {
//        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
//    }
}
