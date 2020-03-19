package com.bht.parkingmap.dbserver.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;

import com.bht.parkingmap.dbserver.configuration.AppConfiguration;

/**
 * This is util class for processing images purpose
 * such as get/save/delete images,
 * for using only in Parking Map Project here
 *
 * Currently using image store on machine only
 * In the future using cloud storage such as Amazon S3
 * to minimize role of server, minimize server storage
 *
 * Just use server to process only, not for storing, for example:
 * + communicate with RDBMS in Amazon RDS, via SSMS
 * + storing image, document in Amazon S3, via REST
 * + web-front end will interact with web-server on upper layer
 *
 * @author bht
 */
public final class ImageUtil {

    private ImageUtil() {
    }


    public enum ImageExtension {
        JPG("jpg"),
        PNG("png");

        private String extension;

        ImageExtension(String extension) {
            this.extension = extension;
        }
    }


    private static String toImagePath(@NotNull String pathFromGalleryDir, @NotNull ImageExtension fileExtension) {
        return AppConfiguration.RESOURCE_DIR + "/gallery/" + pathFromGalleryDir + '.' + fileExtension.extension;
    }


    public static byte[] getImage(@NotNull String pathFromGalleryDir, @NotNull ImageExtension fileExtension) throws IOException {
        File image = new File(toImagePath(pathFromGalleryDir, fileExtension));
        return (image.exists()) ? Files.readAllBytes(image.toPath()) : null;
    }


    public static boolean saveOrUpdateImage(byte[] imageData, @NotNull String pathFromGalleryDir, @NotNull ImageExtension fileExtension) throws IOException {
        if (imageData != null) {
            File image = new File(toImagePath(pathFromGalleryDir, fileExtension));
            deleteImage(image);
            LoggingUtil.log(Level.INFO, "GALLERY", "Save/Update", image.getPath());
            return saveImage(imageData, image, fileExtension);
        }

        LoggingUtil.log(Level.WARN, "GALLERY", "Save/Update", "FAIL due to empty content !");
        return true;
    }


    private static boolean saveImage(@NotNull byte[] imageData, @NotNull File image, @NotNull ImageExtension fileExtension) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        BufferedImage bImage = ImageIO.read(inputStream);
        return ImageIO.write(bImage, fileExtension.extension, image);
    }


    public static void deleteImage(@NotNull String pathFromGalleryDir, @NotNull ImageExtension fileExtension) throws IOException {
        deleteImage(new File(toImagePath(pathFromGalleryDir, fileExtension)));
    }


    private static void deleteImage(@NotNull File image) throws IOException {
        if (image.exists()) {
            Files.delete(image.toPath());
            LoggingUtil.log(Level.INFO, "GALLERY", "Delete", image.getPath());
        }
    }
}