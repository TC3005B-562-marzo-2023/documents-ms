package com.driveai.documentsms.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.util.StringUtils;
import com.driveai.documentsms.models.S3Asset;
import com.driveai.documentsms.repositories.S3Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AwsServiceImpl implements AwsS3Service {

    private static final Logger log = LoggerFactory.getLogger(AwsServiceImpl.class);

    private final S3Repository s3Repository;

    @Autowired
    public AwsServiceImpl(S3Repository s3Repository) {

        this.s3Repository = s3Repository;
    }

    public List<S3Asset> getS3Files(String bucket) {

        return s3Repository.listObjectsInBucket(bucket);
    }

    public String getS3FileContent(String bucketName, String fileName) throws IOException {
        return getAsString(s3Repository.getObject(bucketName, fileName));
    }

    public S3Asset getS3ObjectAsset(String bucketName, String fileName) throws IOException {
        return s3Repository.getObjectAsset(bucketName, fileName);
    }

    private static String getAsString(InputStream is) throws IOException {
        if (is == null)
            return "";
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, StringUtils.UTF8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            is.close();
        }
        return sb.toString();
    }

    @Override
    public byte[] downloadFile(String bucketName, String fileName) throws IOException {
        return s3Repository.downloadFile(bucketName, fileName);
    }

    @Override
    public void moveObject(String bucketName, String fileKey, String destinationFileKey) {
        s3Repository.moveObject(bucketName, fileKey, destinationFileKey);
    }

    @Override
    public void deleteObject(String bucketName, String fileKey) {
        s3Repository.deleteObject(bucketName, fileKey);
    }

    @Override
    public String uploadFile(String bucketName, String filePath, MultipartFile file, String externalTable, int externalId, int reqDocId) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName, extension, originalFileName;

        originalFileName = file.getOriginalFilename();
        extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        fileName = externalTable + "-" + externalId + "-" + "docReqId-" + reqDocId + extension;

        return s3Repository.uploadFile(bucketName, filePath + fileName, fileObj);
    }

    @Override
    public String uploadImage(String bucketName, String filePath, MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName;
        UUID uuid = UUID.randomUUID();
        fileName = uuid + "_" + file.getOriginalFilename();
        return s3Repository.uploadFile(bucketName, filePath + fileName, fileObj);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    @Override
    public String getPreSignedURL(String filePath, String bucketName, HttpMethod httpMethod) {
        return s3Repository.generateViewPreSignedURL(filePath, bucketName, httpMethod);
    }
}