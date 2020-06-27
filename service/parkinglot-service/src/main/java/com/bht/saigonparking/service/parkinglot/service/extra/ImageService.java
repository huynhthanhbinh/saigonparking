package com.bht.saigonparking.service.parkinglot.service.extra;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;

/**
 * This is util class for processing images purpose
 * such as get/save/delete images,
 * for using only in Saigon Parking Project here
 *
 * Used to store images on machine only
 * Currently using cloud storage: Amazon S3
 * to minimize task of server, minimize server storage
 *
 * Just use server to process only, not for storing, for example:
 * + communicate with RDBMS in Amazon RDS, via SSMS
 * + storing image, document in Amazon S3, via REST
 * + web-front end will interact with web-server on upper layer
 *
 * @author bht
 */
public interface ImageService {

    enum ImageExtension {
        JPG("jpg"),
        PNG("png");

        @Getter
        private final String extension;

        ImageExtension(String extension) {
            this.extension = extension;
        }
    }

    byte[] getImage(@NotEmpty String pathFromGalleryDir, @NotNull ImageExtension fileExtension);


    void saveImage(byte[] imageData, @NotEmpty String pathFromGalleryDir, @NotNull ImageExtension fileExtension);


    void deleteImage(@NotEmpty String pathFromGalleryDir, @NotNull ImageExtension fileExtension);
}