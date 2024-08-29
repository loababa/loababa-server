package com.loababa.api.common.ui;

import com.loababa.api.common.model.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @Hidden
    @GetMapping("/health")
    public ApiResponse<Void> checkHealth() {
        return ApiResponse.success();
    }

}
