package com.bht.saigonparking.service.parkinglot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 *
 * @author bht
 */
@Component
public final class AwsConfiguration {

    @Bean("bucketName")
    public String getBucketName(@Value("${aws.bucket}") String bucketName) {
        return bucketName;
    }

    @Bean
    public AWSCredentialsProvider getAWSCredentials(@Value("${aws.access.key.id}") String awsKeyId,
                                                    @Value("${aws.access.key.secret}") String awsKeySecret) {
        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsKeyId, awsKeySecret));
    }

    @Bean
    public AmazonS3 amazonS3Client(AWSCredentialsProvider awsCredentialsProvider,
                                   @Value("${aws.region}") String awsRegion) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion)
                .build();
    }
}