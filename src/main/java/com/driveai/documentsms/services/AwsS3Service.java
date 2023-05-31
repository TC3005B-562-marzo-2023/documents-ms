package com.driveai.documentsms.services;

import com.amazonaws.HttpMethod;
import com.driveai.documentsms.models.S3Asset;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public interface AwsS3Service {
    String uploadFile(String bucketName, String filePath, MultipartFile file, String externalTable, int externalId, int reqDocId);
    String uploadImage(String bucketName, String filePath, MultipartFile file);
    String getS3FileContent(String bucketName, String fileName) throws IOException;
    List<S3Asset> getS3Files(String bucketName) throws IOException;
    byte[] downloadFile(String bucketName, String fileName) throws IOException;
    void moveObject(String bucketName, String fileKey, String destinationFileKey);
    void deleteObject (String bucketName, String fileKey);
    S3Asset getS3ObjectAsset(String bucketName, String fileKey) throws IOException;
    String getPreSignedURL(String filePath, String bucketName, HttpMethod httpMethod);
}
