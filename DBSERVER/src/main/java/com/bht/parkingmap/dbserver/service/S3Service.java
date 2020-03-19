package com.bht.parkingmap.dbserver.service;

import java.io.File;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.amazonaws.services.s3.model.S3Object;

/**
 *
 * @author bht
 */
public interface S3Service {

    S3Object getFile(@NotEmpty String fileName);

    void saveFile(@NotEmpty String fileName, @NotNull File file);

    void deleteFile(@NotEmpty String fileName);
}