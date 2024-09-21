package com.loababa.api.common.service;

import com.loababa.api.common.config.AwsConfig;
import com.loababa.api.common.constant.AwsS3Folder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

import static com.loababa.api.common.config.AwsConfig.REGION;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    public static final Duration AWS_S3_PRESIGNED_URL_EXPIRATION_DURATION = Duration.ofMinutes(10);

    private final AwsCredentials awsCredentials;

    public String createPresignedGetUrl(AwsS3Folder folder, String fileExtension) {
        try (S3Presigner presigner = S3Presigner.builder()
                .region(REGION)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build()
        ) {
            String keyName = generateKey(folder, fileExtension);

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(AWS_S3_PRESIGNED_URL_EXPIRATION_DURATION)
                    .putObjectRequest(builder ->
                            builder.bucket(AwsConfig.AWS_S3_BUCKET_NAME)
                                    .key(keyName)
                                    .build()
                    )
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            return presignedRequest.url()
                    .toExternalForm();
        }
    }

    private String generateKey(AwsS3Folder folder, String fileExtension) {
        return AwsConfig.AWS_PUBLIC_BUCKET_PREFIX + "/" + folder + "/" + UUID.randomUUID() + "." + fileExtension;
    }

}
