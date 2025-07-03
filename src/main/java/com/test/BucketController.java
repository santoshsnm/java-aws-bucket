package com.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.services.s3.model.Bucket;

@Controller
public class BucketController {

	@Autowired
	BucketService bucketService;

	@GetMapping
	public void getBucketList() {
		List<Bucket> bucketList = bucketService.getBucketList();
		System.out.println("bucketList:" + bucketList);
	}

	@GetMapping("/downloadObj")
	public void downloadObject(@RequestParam("bucketName") String bucketName, @RequestParam("objName") String objName)
			throws Exception {
		bucketService.getObjectFromBucket(bucketName, objName);
	}

	@PostMapping("/uploadObj")
	public void uploadObject(@RequestParam("bucketName") String bucketName, @RequestParam("objName") String objName)
			throws Exception {
		bucketService.putObjectIntoBucket(bucketName, objName, "opt/test/v1/uploadfile.txt");
	}

	@PostMapping("/createBucket")
	public String createBucket(@RequestParam("bucketName") String bucketName) {
		bucketService.createBucket(bucketName);
		return "done";
	}

}
