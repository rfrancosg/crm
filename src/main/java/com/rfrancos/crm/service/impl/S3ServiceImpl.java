package com.rfrancos.crm.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.rfrancos.crm.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private static final Logger LOGGER = LogManager.getLogger(S3ServiceImpl.class);

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    public String uploadFile(String key, InputStream inputStream) {
        LOGGER.info("pushing image to s3 with key: " + key);
        s3Client.putObject(bucketName, key, inputStream, null);
        return getFileUrl(bucketName, key);
    }

    private String getFileUrl(String bucketName, String key) {
        return s3Client.getUrl(bucketName, key).toString();
    }
}
