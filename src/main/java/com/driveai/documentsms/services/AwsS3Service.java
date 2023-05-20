package com.driveai.documentsms.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Service
public class AwsS3Service {
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

    // Version de LUIS y Carla para probar endpoint de front
    public String uploadFile(String keyName, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.deleteObject("drive-ai-ccm", keyName);
            amazonS3.putObject("drive-ai-ccm", keyName, file.getInputStream(), metadata);
            return "File uploaded: " + keyName;
        } catch (IOException ioe) {
            System.out.println("\n\n\n IOEXCPETION \n\n\n");
//            logger.error("IOException: " + ioe.getMessage());
        } catch (AmazonServiceException serviceException) {
            System.out.println("\n\n\n SERVICEXCEPTION \n\n\n");
//            logger.info("AmazonServiceException: "+ serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            System.out.println("\n\n\n CLIENTEXCPETION \n\n\n");
//            logger.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        }
        return "File not uploaded: " + keyName;
    }

}
