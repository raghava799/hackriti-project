package com.alacriti.hackriti.samples;
/*
 * Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.alacriti.hackriti.delegate.ExcelReader;
import com.alacriti.hackriti.vo.EmployeeVO;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * This sample demonstrates how to make basic requests to Amazon S3 using the
 * AWS SDK for Java.
 * <p>
 * <b>Prerequisites:</b> You must have a valid Amazon Web Services developer
 * account, and be signed up to use Amazon S3. For more information on Amazon
 * S3, see http://aws.amazon.com/s3.
 * <p>
 * Fill in your AWS access credentials in the provided credentials file
 * template, and be sure to move the file to the default location
 * (/home/kusumavanic/.aws/credentials) where the sample code will load the credentials from.
 * <p>
 * <b>WARNING:</b> To avoid accidental leakage of your credentials, DO NOT keep
 * the credentials file in your source directory.
 *
 * http://aws.amazon.com/security-credentials
 */
public class S3Sample {

   // public static void main(String[] args) throws IOException {
	public static Map<Integer, ArrayList<EmployeeVO>> s3exp(){
		
		Map<Integer, ArrayList<EmployeeVO>> empPrefFromExcel=null;
        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (/home/kusumavanic/.aws/credentials).
         */
       /* AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/home/kusumavanic/.aws/credentials), and is in valid format.",
                    e);
        }

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion("us-west-2")
            .build();*/
		
		AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		 String bucketName = "parkingslotbucket";
	     String key = "Parking.xlsx";

       /* String bucketName = "my-first-s3-bucket-" + UUID.randomUUID();
        String key = "MyObjectKey";*/

        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon S3");
        System.out.println("===========================================\n");
        
        try {
        	//File file=null;
            /*
             * Create a new S3 bucket - Amazon S3 bucket names are globally unique,
             * so once a bucket name has been taken by any user, you can't create
             * another bucket with that same name.
             *
             * You can optionally specify a location for your bucket if you want to
             * keep your data closer to your applications or users.
             */
            /*System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);*/

            /*
             * List the buckets in your account
             */
            System.out.println("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                System.out.println(" - " + bucket.getName());
                if(bucketName.equals(bucket.getName())){
                	 S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
                     System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType()+" "+object.toString());
                     
                     System.out.println( "object.getObjectContent:"+object.getObjectContent());
                     
                     S3ObjectInputStream inputStream = object.getObjectContent();
                     
                    // File file_read=new File("../park.xslx");
                     File file_read = File.createTempFile("park", ".xlsx");
                     file_read.deleteOnExit();
                     
                     //file_read.deleteOnExit();
                     
                     boolean val = file_read.setWritable(true,false);
                     
                    
                     System.out.println("val :"+val+" "+file_read.exists());
                     System.out.println(" file_read"+file_read.getAbsolutePath()+" "+file_read.getPath()+" "+file_read.canRead()+" "+file_read.canWrite());
                     FileUtils.copyInputStreamToFile(inputStream, file_read);

                     System.out.println(file_read);

                    /* final Path filePath = Paths.get("localFile.xlsx");
                     System.out.println("filePath : "+filePath);
                     Files.copy(object.getObjectContent(), filePath);
                     final File localFile = filePath.toFile();
                     localFile.setWritable(true);
                     System.out.println(" localFile :"+localFile.canWrite()+" "+localFile.getAbsoluteFile());*/
                     // or Apache Commons IO
                     //final File localFile = new File("localFile.xlsx");
                   //  FileUtils.copyToFile(object.getObjectContent(), localFile);
         			// displayTextInputStream(object.getObjectContent());
         			
                     //S3ObjectInputStream s3is = object.getObjectContent();
                     
                     System.out.println(" file name :"+file_read);
                     
                     ExcelReader reader = new ExcelReader();
                     
                     empPrefFromExcel = reader.getEmpValues(file_read);

                     
                 /*    file= new File(key);
                     FileOutputStream fos = new FileOutputStream(file);
             	     byte[] read_buf = new byte[1024];
                     int read_len = 0;
                     while ((read_len = s3is.read(read_buf)) > 0) {
                         fos.write(read_buf, 0, read_len);
                     }
                     s3is.close();*/
                }
            }
           // System.out.println("file  "+file.getName()+" "+file.getPath());
            
            /*
             * Upload an object to your bucket - You can easily upload a file to
             * S3, or upload directly an InputStream if you know the length of
             * the data in the stream. You can also specify your own metadata
             * when uploading to S3, which allows you set a variety of options
             * like content-type and content-encoding, plus additional metadata
             * specific to your applications.
             */
           /* System.out.println("Uploading a new object to S3 from a file\n");
            try {
				s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

            /*
             * Download an object - When you download an object, you get all of
             * the object's metadata and a stream from which to read the contents.
             * It's important to read the contents of the stream as quickly as
             * possibly since the data is streamed directly from Amazon S3 and your
             * network connection will remain open until you read all the data or
             * close the input stream.
             *
             * GetObjectRequest also supports several other options, including
             * conditional downloading of objects based on modification times,
             * ETags, and selectively downloading a range of an object.
             */
            System.out.println("Downloading an object");
           /* S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            try {
				displayTextInputStream(object.getObjectContent());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

            /*
             * List objects in your bucket by prefix - There are many options for
             * listing the objects in your bucket.  Keep in mind that buckets with
             * many objects might truncate their results when listing their objects,
             * so be sure to check if the returned object listing is truncated, and
             * use the AmazonS3.listNextBatchOfObjects(...) operation to retrieve
             * additional results.
             */
            System.out.println("Listing objects");
            ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix("My"));
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
            }
            System.out.println();

            /*
             * Delete an object - Unless versioning has been turned on for your bucket,
             * there is no way to undelete an object, so use caution when deleting objects.
             */
         /*   System.out.println("Deleting an object\n");
            s3.deleteObject(bucketName, key);*/

            /*
             * Delete a bucket - A bucket must be completely empty before it can be
             * deleted, so remember to delete any objects from your buckets before
             * you try to delete them.
             */
           /* System.out.println("Deleting bucket " + bucketName + "\n");
            s3.deleteBucket(bucketName);*/
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empPrefFromExcel;
    }

    /**
     * Creates a temporary file with text data to demonstrate uploading a file
     * to Amazon S3
     *
     * @return A newly created temporary file with text data.
     *
     * @throws IOException
     */
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("emp", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("AL1032	TWOWHEELER\n");
        writer.write("AL1002	FOURWHEELER\n");
        writer.write("AL1001	TWOWHEELER\n");
        writer.write("AL1000	FOURWHEELER\n");
        writer.write("AL1042	TWOWHEELER\n");
        writer.close();

        return file;
    }

    /**
     * Displays the contents of the specified input stream as text.
     *
     * @param input
     *            The input stream to display as text.
     *
     * @throws IOException
     */
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }

}
