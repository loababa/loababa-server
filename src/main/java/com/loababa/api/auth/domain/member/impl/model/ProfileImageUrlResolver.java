package com.loababa.api.auth.domain.member.impl.model;

import com.loababa.api.common.constant.AwsS3Folder;
import com.loababa.api.common.service.impl.AwsS3UrlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.loababa.api.auth.domain.member.impl.model.MemberType.LOSSAM;
import static com.loababa.api.common.config.WebConfig.CDN_DOMAIN;

@Component
@RequiredArgsConstructor
public class ProfileImageUrlResolver {

    private final AwsS3UrlConverter awsS3UrlConverter;

    public String resolveOrDefaultProfileImageUrl(String profileImageUrl) {
        if (profileImageUrl == null) {
            return getDefaultProfileImageUrl(LOSSAM);
        }
        return awsS3UrlConverter.convertS3UrlToCloudFrontUrl(AwsS3Folder.PROFILE_IMAGES, profileImageUrl);
    }

    private String getDefaultProfileImageUrl(MemberType memberType) {
        String fileName = switch (memberType) {
            case MOKOKO -> "/mokoko.png";
            case LOSSAM -> "/lossam.png";
        };
        return CDN_DOMAIN + fileName;
    }


}
