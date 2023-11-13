package com.ecommerce.productservice.config;

import com.ecommerce.productservice.dto.AwtSecret;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class AppConfig {


    private final Gson gson ;

    @Value("${aws.access-key}")
    private String accessKey;
    @Value("${aws.secret-key}")
    private String secretKey;
    @Value("${aws.rds-dbName}")
    private String rdsDbName;
    @Value("${aws.regionName}")
    private String regionName;
    @Value("${aws.databaseName}")
    private String databaseName;
    private  AwtSecret getSecret() {

        Region region = Region.of(regionName);
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));

        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(rdsDbName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }

        String secret = getSecretValueResponse.secretString();
        return gson.fromJson(secret,AwtSecret.class);
    }
     @Bean
    public DataSource dataSource(){
        AwtSecret secret = getSecret();
        return DataSourceBuilder
                .create()
                //.driverClassName("com.mysql.cj.jdbc.driver)
                .url("jdbc:"+secret.getEngine()+"://"+secret.getHost()+":"+secret.getPort()+"/"+databaseName)
                .username(secret.getUsername())
                .password(secret.getPassword())
                .build();
    }
}
