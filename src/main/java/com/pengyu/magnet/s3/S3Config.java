package com.pengyu.magnet.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.s3.mock}")
    private boolean mock;

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.access-key}")
    private String minioAccessKey;

    @Value("${minio.secret-key}")
    private String minioSecretKey;
    @Value("${minio.minio-region}")
    private String minioRegion;

    @Bean
    public S3Client s3Client() {
        if (mock) {
            return new FakeS3();
        }

        return S3Client.builder()
                .endpointOverride(URI.create(minioEndpoint))  // 设置 MinIO 的 endpoint
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(minioAccessKey, minioSecretKey)))  // 设置 MinIO 访问凭证
                .region(Region.of(minioRegion))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();




//        return S3Client.builder()
//                .region(Region.of(awsRegion))
//                .build();
    }

}
