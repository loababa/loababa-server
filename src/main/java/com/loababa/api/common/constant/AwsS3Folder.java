package com.loababa.api.common.constant;

import com.loababa.api.common.exception.CommonClientExceptionInfo;
import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AwsS3Folder {
    PROFILE_IMAGES("profile-images"),
    ;

    private final String value;

    public static AwsS3Folder from(String value) {
        for (AwsS3Folder awsS3Folder : values()) {
            if (awsS3Folder.value.equals(value)) {
                return awsS3Folder;
            }
        }
        throw new LoababaBadRequestException(
                CommonClientExceptionInfo.UNKNOWN_AWS_S3_FOLDER,
                new ServerExceptionInfo(
                        "존재하지 않는 Aws S3 폴더로 요청했습니다."
                )
        );
    }

    @Override
    public String toString() {
        return value;
    }
}
