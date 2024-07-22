package com.loababa.api.auth.domain.member.impl.model;

public record MemberProfile(
        String nickname,
        ProfileImageURL profileImageURL,
        MemberType memberType
) {

    public static MemberProfile createLossamProfile(
            String nickname, String profileImageURL
    ) {
        return new MemberProfile(nickname, new ProfileImageURL(profileImageURL), MemberType.LOSSAM);
    }

    public record ProfileImageURL(
            String value
    ) {
        private static final String DEFAULT_PROFILE_IMAGE_URL = "default-profile-image-url";

        public ProfileImageURL {
            if (value == null) {
                value = DEFAULT_PROFILE_IMAGE_URL;
            }
        }
    }
}
