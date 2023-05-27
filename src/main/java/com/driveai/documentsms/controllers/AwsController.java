package com.driveai.documentsms.controllers;

import com.driveai.documentsms.models.S3Asset;
import com.driveai.documentsms.services.AwsS3Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/s3")
public class AwsController {

    private final AwsS3Service awsService;

    public AwsController(@Qualifier("awsServiceImpl") AwsS3Service awsService) {
        this.awsService = awsService;
    }

    @GetMapping("/getS3FileContent")
    public ResponseEntity<String> getS3FileContent(@RequestParam(value = "bucketName") String bucketName, @RequestParam(value = "fileName") String fileName) throws IOException {
        return new ResponseEntity<>(awsService.getS3FileContent(bucketName, fileName), HttpStatus.OK);
    }

    @GetMapping("/listS3Files")
    public ResponseEntity<List<S3Asset>> getS3Files(@RequestParam(value = "bucketName") String bucketName) throws IOException {
        List<S3Asset> list= new ArrayList<>();
        HttpStatus status=  HttpStatus.OK;
        try {
            list =  awsService.getS3Files(bucketName);
        } catch (Exception e) {
            status =  HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(list, status);
    }

    @GetMapping("/downloadS3File")
    public ResponseEntity<ByteArrayResource> downloadS3File(@RequestParam(value = "bucketName") String bucketName, @RequestParam(value = "filePath") String filePath, @RequestParam(value = "fileName") String fileName)
            throws IOException {
        byte[] data = awsService.downloadFile(bucketName, fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/deleteObject")
    public ResponseEntity<String> deleteFile(@RequestParam(value = "bucketName") String bucketName, @RequestParam(value = "fileName") String fileName) {
        awsService.deleteObject(bucketName, fileName);
        return new ResponseEntity<>("File deleted", HttpStatus.OK);
    }

    @GetMapping("/moveFile")
    public ResponseEntity<String> moveFile(@RequestParam(value = "bucketName") String bucketName,
                                           @RequestParam(value = "fileName") String fileKey,
                                           @RequestParam(value = "fileNameDest") String fileKeyDest) {
        awsService.moveObject(bucketName, fileKey, fileKeyDest);
        return new ResponseEntity<>("File moved", HttpStatus.OK);
    }

    @PostMapping("/upload-files")
    public ResponseEntity<?> uploadFiles(@RequestParam Map<String, MultipartFile> formData, @RequestParam(value = "bucketName") String bucketName, @RequestParam(value = "filePath") String filePath) {
        try {
            List<S3Asset> storageUrls = new ArrayList<>();

            for (Map.Entry<String, MultipartFile> entry : formData.entrySet()) {
                MultipartFile file = entry.getValue();

                if (!file.isEmpty()) {
                    String s3FileName = awsService.uploadFile(bucketName, filePath, file);
                    S3Asset imgAsset = awsService.getS3ObjectAsset(bucketName, s3FileName);
                    storageUrls.add(imgAsset);
                }
            }

            return new ResponseEntity<>(storageUrls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update-file")
    public ResponseEntity<?> updateFile(
            @RequestParam(value = "bucketName") String bucketName,
            @RequestParam(value = "filePath") String filePath,
            @RequestParam(value = "fileName") String fileName,
            @RequestParam(value = "newFile") MultipartFile newFile) {
        try {
            // Delete the existing file
            awsService.deleteObject(bucketName, filePath + fileName);

            // Upload the new file
            String s3FileName = awsService.uploadFile(bucketName, filePath, newFile);
            S3Asset updatedFile = awsService.getS3ObjectAsset(bucketName, s3FileName);

            return new ResponseEntity<>(updatedFile, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}