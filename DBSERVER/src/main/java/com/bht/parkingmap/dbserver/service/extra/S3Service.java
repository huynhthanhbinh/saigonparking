package com.bht.parkingmap.dbserver.service.extra;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.constraints.NotEmpty;

/**
 * common methods to interact with Amazon S3 Cloud Storage
 *
 * @author bht
 */
public interface S3Service {

    InputStream getFile(@NotEmpty String filePath, boolean warnOnFail);

    void saveFile(byte[] fileData, @NotEmpty String filePath, boolean warnOnFail) throws IOException;

    void deleteFile(@NotEmpty String filePath, boolean warnOnFail);
}