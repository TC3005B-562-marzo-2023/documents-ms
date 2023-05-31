package com.driveai.documentsms.repositories;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;

import com.driveai.documentsms.models.S3Asset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class S3RepositoryImpl implements S3Repository {

    private final AmazonS3 s3Client;

    @Autowired
    public S3RepositoryImpl(AmazonS3 s3Client) {

        this.s3Client = s3Client;
    }

    private static final Logger log = LoggerFactory.getLogger(S3RepositoryImpl.class);


    @Override
    public List<S3Asset> listObjectsInBucket(String bucket) {
        List<S3Asset>  items =
                s3Client.listObjectsV2(bucket).getObjectSummaries().stream()
                        .parallel()
                        .map(S3ObjectSummary::getKey)
                        .map(key -> mapS3ToObject(bucket, key))
                        .collect(Collectors.toList());

        log.info("Found " + items.size() + " objects in the bucket " + bucket);
        return items;
    }

    private S3Asset mapS3ToObject(String bucket, String key) {
        return S3Asset.builder()
                .name(s3Client.getObjectMetadata(bucket, key).getUserMetaDataOf("name"))
                .key(key)
                .url(s3Client.getUrl(bucket, key))
                .build();
    }

    @Override
    public S3ObjectInputStream getObject(String bucketName, String fileName) throws IOException {
       /*
        if (!s3Client.doesBucketExistV2(bucketName)) {
            log.error("No Bucket Found");
            return null;
        }*/
        S3Object s3object = s3Client.getObject(bucketName, fileName);
        return s3object.getObjectContent();
    }

    @Override
    public byte[] downloadFile(String bucketName, String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void moveObject(String bucketName, String fileKey, String destinationFileKey) {
        CopyObjectRequest copyObjRequest = new CopyObjectRequest(bucketName, fileKey, bucketName, destinationFileKey);
        s3Client.copyObject(copyObjRequest);
        deleteObject(bucketName, fileKey);
    }

    @Override
    public void deleteObject (String bucketName, String fileKey) {
        s3Client.deleteObject(bucketName, fileKey);
    }

    public String uploadFile(String bucketName, String fileName, File fileObj) {
        if (bucketName.equals("public-drive-ai")) {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj).withCannedAcl(CannedAccessControlList.PublicRead));
        } else {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        }
        fileObj.delete();
        return fileName;
    }

    @Override
    public S3Asset getObjectAsset(String bucketName, String fileName) {
        return mapS3ToObject(bucketName, fileName);
    }

    @Override
    public String generateViewPreSignedURL(String filePath, String bucketName, HttpMethod httpMethod) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 1);
        return s3Client.generatePresignedUrl(bucketName, filePath, calendar.getTime(), httpMethod).toString();
    }
}