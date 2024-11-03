package com.rfrancos.crm.service;

import com.amazonaws.services.s3.AmazonS3;
import com.rfrancos.crm.service.impl.S3ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class S3ServiceImplTest {

    @InjectMocks
    private S3ServiceImpl s3Service;

    @Mock
    private AmazonS3 s3Client;

    private final String bucketName = "test-bucket";

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        setPrivateField(s3Service, "bucketName", bucketName);
    }

    private void setPrivateField(Object target, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void uploadFile_ShouldReturnFileUrl() throws MalformedURLException {
        String key = "test-file";
        InputStream inputStream = new ByteArrayInputStream("test data".getBytes());

        when(s3Client.getUrl(bucketName, key)).thenReturn(new java.net.URL("https://test-bucket.s3.amazonaws.com/test-file"));
        String result = s3Service.uploadFile(key, inputStream);

        assertEquals("https://test-bucket.s3.amazonaws.com/test-file", result);
        verify(s3Client).putObject(bucketName, key, inputStream, null);
    }
}
