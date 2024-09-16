package com.loababa.api.common.service;

import com.loababa.api.common.constant.AwsS3Folder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;
import java.util.UUID;

import static com.loababa.api.common.config.AwsConfig.AwsS3Properties;
import static com.loababa.api.common.config.AwsConfig.REGION;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    public static final Duration AWS_S3_PRESIGNED_URL_EXPIRATION_DURATION = Duration.ofMinutes(10);

    private final AwsS3Properties awsS3Properties;
    private final AwsCredentials awsCredentials;

    public String createPresignedGetUrl(AwsS3Folder folder, String fileExtension) {
        try (S3Presigner presigner = S3Presigner.builder()
                .region(REGION)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build()
        ) {

            String keyName = generateKey(folder, fileExtension);

            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(awsS3Properties.bucketName())
                    .key(keyName)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(AWS_S3_PRESIGNED_URL_EXPIRATION_DURATION)
                    .getObjectRequest(objectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);

            return presignedRequest.url()
                    .toExternalForm();
        }
    }

    private String generateKey(AwsS3Folder folder, String fileExtension) {
        return awsS3Properties.publicBucketPrefix() + "/" + folder + "/" + UUID.randomUUID() + "." + fileExtension;
    }

}
