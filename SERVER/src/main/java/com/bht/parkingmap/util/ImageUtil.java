package com.bht.parkingmap.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;

import com.bht.parkingmap.configuration.AppConfiguration;

/**
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


    public static byte[] getImage(@NotNull String pathFromGalleryDir, @NotNull ImageExtension fileExtension) throws IOException {
        return Files.readAllBytes(new File(AppConfiguration.RESOURCE_DIR +
                "/gallery/" + pathFromGalleryDir + '.' + fileExtension.extension).toPath());
    }


    public static void saveImage(@NotNull byte[] imageData, @NotNull String pathFromGalleryDir, @NotNull ImageExtension fileExtension) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        BufferedImage bImage = ImageIO.read(inputStream);
        ImageIO.write(bImage, fileExtension.extension, new File(AppConfiguration.RESOURCE_DIR +
                "/gallery/" + pathFromGalleryDir + '.' + fileExtension.extension));
    }
}