package com.test;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.Bucket;

@RestController
public class BucketController {

	@Autowired
	BucketService bucketService;

	@GetMapping("/getBucketList")
	public Object getBucketList() {
		List<Bucket> bucketList = bucketService.getBucketList();
		System.out.println("bucketList:" + bucketList);
		return bucketList.stream().map(p -> p.getName()).collect(Collectors.toList());
	}

	@GetMapping("/downloadObj")
	public ResponseEntity<ByteArrayResource> downloadObjects(@RequestParam("bucketName") String bucketName,
			@RequestParam("fileName") String fileName) throws Exception {

		byte[] content = bucketService.download(bucketName, fileName);
		ByteArrayResource resource = new ByteArrayResource(content);
		return ResponseEntity.ok().contentLength(content.length)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(resource);

	}

	@PostMapping("/uploadObj")
	public ResponseEntity<Object> uploadObject(MultipartFile file, @RequestParam("bucketName") String bucketName) throws Exception {

		String res = bucketService.putObjectIntoBucket(bucketName, file);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	@PostMapping("/createBucket")
	public String createBucket(@RequestParam("bucketName") String bucketName) {
		bucketService.createBucket(bucketName);
		return "done";
	}

}
