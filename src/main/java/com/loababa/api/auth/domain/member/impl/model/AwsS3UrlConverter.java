package com.loababa.api.auth.domain.member.impl.model;

import com.loababa.api.common.constant.AwsS3Folder;
import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.common.service.impl.CloudFrontUrlResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AwsS3UrlConverter {

    private final CloudFrontUrlResolver cloudFrontUrlResolver;

    public String convertS3UrlToCloudFrontUrl(AwsS3Folder awsS3Folder, String s3Url) {
        return switch (awsS3Folder) {
            case PROFILE_IMAGES -> {
                String s = extractFilePath(awsS3Folder, s3Url);
                yield cloudFrontUrlResolver.resolve(s);
            }
        };
    }

    private String extractFilePath(AwsS3Folder awsS3Folder, String s3Url) {
        int index = s3Url.indexOf(awsS3Folder.getValue());
        if (index == -1) {
            throw new LoababaBadRequestException(
                    new ServerExceptionInfo("올바르지 않은 S3 URL 입니다. S3 URL: " + s3Url)
            );
        }
        return s3Url.substring(index);
    }

}
