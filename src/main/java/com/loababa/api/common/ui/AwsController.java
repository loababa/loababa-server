package com.loababa.api.common.ui;

import com.loababa.api.common.constant.AwsS3Folder;
import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.common.model.MemberCredential;
import com.loababa.api.common.service.AwsS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "AWS", description = "aws 관련 API")
public class AwsController {

    private final AwsS3Service awsS3Service;

    @Operation(description = "S3 Presigned URL 생성", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/api/v1/{category}/presigned-url")
    public ApiResponse<String> requestPresignedURL(
            MemberCredential memberCredential,
            @Schema(allowableValues = {"profile-images"},
                    description = "업로드할 파일의 카테고리")
            @PathVariable String category,
            @RequestParam
            @Pattern(regexp = "^.*\\.(jpg|jpeg|png)$",
                    message = "파일 이름은 jpg, jpeg, png 형식이어야 합니다.")
            String filename
    ) {
        String presignedPutUrl = awsS3Service.createPresignedPutUrl(
                AwsS3Folder.from(category),
                StringUtils.getFilenameExtension(filename)
        );
        return ApiResponse.success(presignedPutUrl);
    }

}
