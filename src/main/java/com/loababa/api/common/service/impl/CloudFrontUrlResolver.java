package com.loababa.api.common.service.impl;

import org.springframework.stereotype.Component;

import static com.loababa.api.common.config.WebConfig.CDN_DOMAIN;

@Component
public class CloudFrontUrlResolver {

    public String resolve(String filePath) {
        return CDN_DOMAIN + filePath;
    }

}
