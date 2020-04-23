package com.bht.saigonparking.service.user.service.extra.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.service.user.service.extra.ImageService;
import com.bht.saigonparking.service.user.service.extra.S3Service;
import com.google.common.io.ByteStreams;

import lombok.AllArgsConstructor;

/**
 *
 * this class implements all services relevant to processing image store on S3
 * S3 is a cloud storage approach for web-services, provided by amazon
 *
 * for clean code purpose,
 * using {@code @AllArgsConstructor} for Service class
 * it will {@code @Autowired} all attributes declared inside
 * hide {@code @Autowired} as much as possible in code
 * remember to mark all attributes as {@code private final}
 *
 * @author bht
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImageServiceImpl implements ImageService {

    private final S3Service s3Service;


    private static String toImagePath(@NotNull String pathFromGalleryDir, @NotNull ImageService.ImageExtension fileExtension) {
        return "gallery/" + pathFromGalleryDir + '.' + fileExtension.getExtension();
    }

    @Override
    public byte[] getImage(@NotNull String pathFromGalleryDir, @NotNull ImageService.ImageExtension fileExtension) throws IOException {
        InputStream inputStream = s3Service.getFile(toImagePath(pathFromGalleryDir, fileExtension), false);
        return (inputStream != null) ? ByteStreams.toByteArray(inputStream) : null;
    }

    @Override
    public void saveImage(byte[] imageData, @NotNull String pathFromGalleryDir, @NotNull ImageService.ImageExtension fileExtension) throws IOException {
        if (imageData != null && imageData.length > 0) {
            s3Service.saveFile(imageData, toImagePath(pathFromGalleryDir, fileExtension), true);
        }
    }

    @Override
    public void deleteImage(@NotNull String pathFromGalleryDir, @NotNull ImageService.ImageExtension fileExtension) {
        s3Service.deleteFile(toImagePath(pathFromGalleryDir, fileExtension), true);
    }
}