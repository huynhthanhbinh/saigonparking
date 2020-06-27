package com.bht.saigonparking.service.parkinglot.service.extra.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.constraints.NotEmpty;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.parkinglot.service.extra.S3Service;
import com.google.common.io.ByteSource;

import lombok.AllArgsConstructor;

/**
 *
 * this class implements all services relevant to amazon S3
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
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3Client;

    @Qualifier("bucketName")
    private final String bucketName;

    @Override
    public InputStream getFile(@NotEmpty String filePath, boolean warnOnFail) {
        try (InputStream inputStream = amazonS3Client.getObject(bucketName, filePath).getObjectContent()) {

            LoggingUtil.log(Level.DEBUG, "SERVICE", "Success", String.format("getS3File(\"%s\")", filePath));

            return inputStream;

        } catch (AmazonClientException | IOException exception) {

            if (warnOnFail) {
                LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
                LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL", String.format("getS3File(\"%s\")", filePath));
            }

            return null;
        }
    }

    @Override
    public void saveFile(byte[] fileData, @NotEmpty String filePath, boolean warnOnFail) {
        try (InputStream inputStream = ByteSource.wrap(fileData).openStream()) {

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileData.length);
            amazonS3Client.putObject(bucketName, filePath, inputStream, metadata);

            LoggingUtil.log(Level.DEBUG, "SERVICE", "Success", String.format("saveS3File(\"%s\")", filePath));

        } catch (AmazonClientException | IOException exception) {

            if (warnOnFail) {
                LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
                LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL", String.format("saveS3File(\"%s\")", filePath));
            }
        }
    }

    @Override
    public void deleteFile(@NotEmpty String filePath, boolean warnOnFail) {
        try {
            amazonS3Client.deleteObject(bucketName, filePath);

            LoggingUtil.log(Level.DEBUG, "SERVICE", "Success", String.format("deleteS3File(\"%s\")", filePath));

        } catch (AmazonClientException exception) {

            if (warnOnFail) {
                LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
                LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL", String.format("deleteS3File(\"%s\")", filePath));
            }
        }
    }
}