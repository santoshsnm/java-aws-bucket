package com.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class BucketConfig {

	@Value("${aws.access.key}")
	String awsAccessKey;

	@Value("${aws.secret.key}")
	String awsSecretKey;

    @Bean
    AmazonS3 getAmazonS3Client() {
	AWSCredentials credentails = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
		System.out.println(awsAccessKey);
		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentails))
				.withRegion(Regions.US_EAST_1).build();
	}
}
