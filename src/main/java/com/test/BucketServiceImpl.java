package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class BucketServiceImpl implements BucketService {

	Logger LOG = LogManager.getLogger(BucketServiceImpl.class);

	@Autowired
	AmazonS3 s3Client;

	@Override
	public List<Bucket> getBucketList() {
		LOG.info("getting bucket list... ");
		return s3Client.listBuckets();
	}

	@Override
	public boolean validateBucket(String bucketName) {
		List<Bucket> bucketList = getBucketList();
		LOG.info("Bucket list:" + bucketList);
		return bucketList.stream().anyMatch(m -> bucketName.equals(m.getName()));
	}

	@Override
	public void getObjectFromBucket(String bucketName, String objectName) throws IOException {
		S3Object s3Object = s3Client.getObject(bucketName, objectName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		FileOutputStream fos = new FileOutputStream(new File("../" + objectName));
		byte[] read_buf = new byte[1024];
		int read_len = 0;
		while ((read_len = inputStream.read(read_buf)) > 0) {
			fos.write(read_buf, 0, read_len);
		}
		inputStream.close();
		fos.close();
	}

	@Override
	public void createBucket(String bucketName) {
		try {
			s3Client.createBucket(bucketName);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public String putObjectIntoBucket(String bucketName, MultipartFile file) throws IOException {

		try {
			File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
			file.transferTo(tempFile);
			URI uri = tempFile.toURI();
			s3Client.putObject(bucketName, file.getOriginalFilename(), new File(uri));

			return "success";
		} catch (Exception e) {
			System.out.println("hii error");
			return "Error "+e.getMessage();
			//throw new RuntimeException(e.getLocalizedMessage());
		}

	}

	@Override
	public byte[] download(String bucketName, String objectName) throws IOException {
		S3Object s3Object = s3Client.getObject(bucketName, objectName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();

		return inputStream.readAllBytes();
	}

}
