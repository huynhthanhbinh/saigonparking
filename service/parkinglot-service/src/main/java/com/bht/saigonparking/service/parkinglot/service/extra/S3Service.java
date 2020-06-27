package com.bht.saigonparking.service.parkinglot.service.extra;

import java.io.InputStream;

import javax.validation.constraints.NotEmpty;

/**
 *
 * common methods to interact with Amazon S3 Cloud Storage
 *
 * @author bht
 */
public interface S3Service {

    InputStream getFile(@NotEmpty String filePath, boolean warnOnFail);

    void saveFile(byte[] fileData, @NotEmpty String filePath, boolean warnOnFail);

    void deleteFile(@NotEmpty String filePath, boolean warnOnFail);
}