package org.work.twitter;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AWSS3 {

    private final String bucketName;
    private final String s3key;
    private final String s3secreteKey;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'tweetdata_'yyyyMMdd'_'HHmmss'.csv'");

    public AWSS3(String bucketName, String key, String secretKey) {
        this.bucketName = bucketName;
        this.s3key = key;
        this.s3secreteKey = secretKey;
    }

    public void upload(List<String> csvList) {

        String uploadFileName = ZonedDateTime.now(ZoneId.of("Asia/Tokyo")).format(formatter);

        AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(s3key, s3secreteKey));
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (String csv : csvList) {
                baos.write(csv.getBytes("UTF8"));
                baos.write(13);
                baos.write(10);
            }
            byte[] bytes = baos.toByteArray();

            System.out.println("Uploading a new object to S3 from a file\n");
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(bytes.length);
            meta.setContentType("text/csv");
            s3client.putObject(new PutObjectRequest(bucketName, uploadFileName, new ByteArrayInputStream(bytes), meta));

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
