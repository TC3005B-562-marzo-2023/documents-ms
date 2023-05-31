package com.driveai.documentsms.repositories;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.driveai.documentsms.models.S3Asset;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface S3Repository {
    String uploadFile(String bucketName, String fileName, File fileObj);
    List<S3Asset> listObjectsInBucket(String bucket);
    S3ObjectInputStream getObject(String bucketName, String fileName) throws IOException;
    byte[] downloadFile(String bucketName, String fileName) throws IOException;
    void moveObject(String bucketName, String fileKey, String destinationFileKey);
    void deleteObject(String bucketName, String fileKey);
    S3Asset getObjectAsset(String bucketName, String fileName);
    String generateViewPreSignedURL(String filePath, String bucketName, HttpMethod httpMethod);
}
