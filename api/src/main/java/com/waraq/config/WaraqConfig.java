package com.waraq.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WaraqConfig {

    private static final String RESOURCE_BUNDLE_NAME = "messages";

    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(RESOURCE_BUNDLE_NAME);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    @Bean
    public AmazonS3 amazonS3Client(@Value("${waraq.spaces.access}") String accessKey,
                                   @Value("${waraq.spaces.secret}") String secretKey,
                                   @Value("${waraq.spaces.endpoint}") String endpoint,
                                   @Value("${waraq.spaces.region}") String region) {
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(accessKey, secretKey));

        return AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .build();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
