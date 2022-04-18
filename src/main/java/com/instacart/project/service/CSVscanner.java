package com.instacart.project.service;

import com.instacart.project.model.FileDetail;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.*;
import java.util.Scanner;

public class CSVscanner {

    public void scanCSVfromLocal(String file_path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file_path));
        sc.useDelimiter(",");
        while(sc.hasNext()) {
            System.out.println(sc.next());
        }
        sc.close();
    }

    public void scanCSVfromS3(FileDetail file, String database) throws IOException {
        String bucket_name = file.getBucket_name();
        String key = file.getFile_path()+file.getFile_name();

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "",
                "");

        Region region = Region.US_EAST_2;
        S3Client s3 = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(region)
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket_name)
                .key(key)
                .build();

        ResponseInputStream inputStream = s3.getObject(getObjectRequest);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String l;
        String query = "INSERT INTO departments ("+reader.readLine()+") VALUES ";
        while((l = reader.readLine()) != null) {
            System.out.println(l);
            query = query + "(" + l + "),";
        }
        query = query + "()";
        System.out.println(query);


//        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
//        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);
//        listBucketsResponse.buckets().stream().forEach(x -> System.out.println(x.name()));
    }
}
