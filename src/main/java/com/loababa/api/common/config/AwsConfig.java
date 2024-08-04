package com.loababa.api.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@EnableConfigurationProperties({
        AwsConfig.AwsIAMProperties.class,
        AwsConfig.AwsCloudFrontProperties.class,
        AwsConfig.AwsS3Properties.class
})
public class AwsConfig {

    public static final Region REGION = Region.AP_NORTHEAST_2;

    @ConfigurationProperties("aws.iam")
    public record AwsIAMProperties(
            String accessKey,
            String secretKey
    ) {

    }

    @Bean
    public AwsCredentials awsCredentials(AwsIAMProperties awsIAMProperties) {
        return AwsBasicCredentials.builder()
                .accessKeyId(awsIAMProperties.accessKey)
                .secretAccessKey(awsIAMProperties.secretKey)
                .build();
    }

    @Bean
    public S3Client s3Client(AwsCredentials awsCredentials) {
        return S3Client.builder()
                .region(REGION)
                .credentialsProvider(() -> awsCredentials)
                .build();
    }

    @ConfigurationProperties("aws.s3")
    public record AwsS3Properties(
            String publicBucketPrefix,
            String bucketName
    ) {

    }

    @ConfigurationProperties("aws.cloudfront")
    public record AwsCloudFrontProperties(
            String domain
    ) {

    }

}
