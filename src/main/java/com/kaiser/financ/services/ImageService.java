package com.kaiser.financ.services;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    BufferedImage resize(BufferedImage sourceImg, int size);

    BufferedImage cropSquare(BufferedImage sourceImg);

    InputStream getInputStream(BufferedImage img, String extension);

    BufferedImage pngToJpg(BufferedImage img);

    BufferedImage getJpgImageFromFile(MultipartFile uploadedFile);

}
