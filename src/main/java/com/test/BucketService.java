package com.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.Bucket;

public interface BucketService {

	// get list of buckets for given user
	List<Bucket> getBucketList();

	// check if given bucket name valid
	boolean validateBucket(String bucketName);
	
	byte[] download(String bucketName, String objectName) throws FileNotFoundException, IOException;
	// download given objectName from the bucket
	void getObjectFromBucket(String bucketName, String objectName) throws IOException;

	// upload given file as objectName to S3 bucket
	String putObjectIntoBucket(String bucketName,  MultipartFile file) throws IOException;

	// create Bucket with provided name (throws exception if bucket already present)
	void createBucket(String bucket);
}
