package com.rfrancos.crm.service;

import java.io.InputStream;

public interface S3Service {

    String uploadFile(String key, InputStream inputStream);
}
