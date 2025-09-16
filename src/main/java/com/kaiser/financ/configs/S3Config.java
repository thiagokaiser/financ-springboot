package com.kaiser.financ.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

  @Value("${aws.access_key_id}")
  private String awsId;

  @Value("${aws.secret_access_key}")
  private String awsKey;

  @Value("${s3.region}")
  private String regionName;

  @Bean
  public S3Client s3client() {
    return S3Client.builder()
        .region(Region.of(regionName))
        .credentialsProvider(
            StaticCredentialsProvider.create(AwsBasicCredentials.create(awsId, awsKey))
        )
        .build();
  }

  @Bean
  public Region s3Region() {
    return Region.of(regionName);
  }
}
