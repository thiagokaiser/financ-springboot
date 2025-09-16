package com.kaiser.financ.services.impl;

import com.kaiser.financ.services.AmazonS3Service;
import com.kaiser.financ.services.exceptions.FileException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

  private final Logger logger = LoggerFactory.getLogger(AmazonS3ServiceImpl.class);

  private final S3Client s3client;
  private final Region s3Region;

  @Value("${s3.bucket}")
  private String bucketName;

  public AmazonS3ServiceImpl(S3Client s3client, Region s3Region) {
    this.s3client = s3client;
    this.s3Region = s3Region;
  }

  @Override
  public URI uploadFile(MultipartFile multipartFile) {
    try {
      String fileName = multipartFile.getOriginalFilename();
      InputStream is = multipartFile.getInputStream();
      String contentType = multipartFile.getContentType();
      long contentLength = multipartFile.getSize();
      return uploadFile(is, fileName, contentType, contentLength);
    } catch (Exception e) {
      throw new FileException("Erro ao processar arquivo: " + e.getMessage());
    }
  }

  @Override
  public URI uploadFile(InputStream is, String fileName, String contentType) {
    try {
      long contentLength = is.available();
      return uploadFile(is, fileName, contentType, contentLength);
    } catch (Exception e) {
      throw new FileException("Erro ao processar arquivo: " + e.getMessage());
    }
  }

  private URI uploadFile(InputStream is, String fileName, String contentType, long contentLength) {
    try {
      logger.info("Iniciando Upload: {}", fileName);

      PutObjectRequest request = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(fileName)
          .contentType(contentType)
          .contentLength(contentLength)
          .build();

      s3client.putObject(request, RequestBody.fromInputStream(is, contentLength));

      logger.info("Upload finalizado: {}", fileName);

      // Monta a URI pública
      return new URI("https://" + bucketName + ".s3." + s3Region.id() + ".amazonaws.com/" + fileName);

    } catch (S3Exception e) {
      throw new FileException("Erro ao enviar arquivo para S3: " + e.awsErrorDetails().errorMessage());
    } catch (URISyntaxException e) {
      throw new FileException("Erro ao converter URL para URI: " + e.getMessage());
    }
  }
}
