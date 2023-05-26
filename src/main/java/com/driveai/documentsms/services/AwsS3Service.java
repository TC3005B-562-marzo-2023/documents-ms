package com.driveai.documentsms.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.driveai.documentsms.models.S3Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public interface AwsS3Service {
    String uploadFile(String bucketName, String filePath, MultipartFile file);
    String getS3FileContent(String bucketName, String fileName) throws IOException;
    List<S3Asset> getS3Files(String bucketName) throws IOException;
    byte[] downloadFile(String bucketName, String fileName) throws IOException;
    void moveObject(String bucketName, String fileKey, String destinationFileKey);
    void deleteObject (String bucketName, String fileKey);
    /*
    private AmazonS3 amazonS3;
    @Autowired
    public void AwsS3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String generatePreSignedUrl(String filePath,
                                       String bucketName,
                                       HttpMethod httpMethod) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10); //validity of 10 minutes
        return amazonS3.generatePresignedUrl(bucketName, filePath, calendar.getTime(), httpMethod).toString();
    }

     */
}
