package com.ll.JollyJourney;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JollyJourneyApplicationTests {
	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Test
	void contextLoads() {
		System.out.println("Access Key: " + accessKey);
		System.out.println("Secret Key: " + secretKey);
		System.out.println("Region: " + region);
		System.out.println("Bucket: " + bucket);

		// Assert statements to ensure the environment variables are set
		assert accessKey != null && !accessKey.isEmpty();
		assert secretKey != null && !secretKey.isEmpty();
		assert region != null && !region.isEmpty();
		assert bucket != null && !bucket.isEmpty();
	}
}