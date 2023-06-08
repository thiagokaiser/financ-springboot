package com.kaiser.financ.services;

import java.io.InputStream;
import java.net.URI;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {

    URI uploadFile(MultipartFile multipartFile);

    URI uploadFile(InputStream is, String fileName, String contentType);

}
