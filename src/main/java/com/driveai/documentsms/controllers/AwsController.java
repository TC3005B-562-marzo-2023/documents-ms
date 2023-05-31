package com.driveai.documentsms.controllers;
import com.driveai.documentsms.dto.CreateDocumentDto;
import com.driveai.documentsms.dto.UpdateDocumentDto;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.models.S3Asset;
import com.driveai.documentsms.services.AwsS3Service;
import com.driveai.documentsms.services.DocumentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/s3")
public class AwsController {

    private final AwsS3Service awsService;
    private final DocumentService documentService;

    public AwsController(@Qualifier("awsServiceImpl") AwsS3Service awsService, DocumentService documentService) {
        this.awsService = awsService;
        this.documentService = documentService;
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

    @ApiResponse(responseCode = "200", description = "Upload document to s3", content = { @Content(schema = @Schema(implementation = S3Asset.class))})
    @PostMapping("/upload-document")
    public ResponseEntity<?> uploadDocument(@RequestParam(value = "filePath") String filePath, @RequestParam(value = "file") MultipartFile file, @RequestParam(value="externalTable") String externalTable, @RequestParam(value="externalId") int externalId, @RequestParam(value="reqDocId") int reqDocId) {
        try {
            if (!file.isEmpty()) {
                String s3FileName = awsService.uploadFile("drive-ai-ccm", filePath, file, externalTable, externalId, reqDocId);
                S3Asset documentAsset = awsService.getS3ObjectAsset("drive-ai-ccm", s3FileName);
                return new ResponseEntity<>(documentAsset, HttpStatus.OK);
            }
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    @ApiResponse(responseCode = "200", description = "Upload image or images to s3", content = { @Content(schema = @Schema(implementation = S3Asset.class))})
    @PostMapping("/upload-images")
    public ResponseEntity<?> uploadImages(@RequestParam Map<String, MultipartFile> formData, @RequestParam(value = "filePath") String filePath) {
        try {
            List<S3Asset> storageUrls = new ArrayList<>();

            for (Map.Entry<String, MultipartFile> entry : formData.entrySet()) {
                MultipartFile file = entry.getValue();

                if (!file.isEmpty()) {
                    String s3FileName = awsService.uploadImage("public-drive-ai", filePath, file);
                    S3Asset imgAsset = awsService.getS3ObjectAsset("public-drive-ai", s3FileName);
                    storageUrls.add(imgAsset);
                }
            }

            if (storageUrls.isEmpty()) return new ResponseEntity<>("No files present", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(storageUrls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @PostMapping("/create-update-document")
        public ResponseEntity<?> updateDocument(
                @RequestParam(value = "filePath", required = false) String filePath,
                @RequestParam(value = "newFile") MultipartFile newFile,
                @RequestParam(value = "externalTable") String externalTable,
                @RequestParam(value = "externalId") int externalId,
                @RequestParam(value = "reqDocId") int reqDocId,
                Principal principal
        ) {
            try {
                int oldDocumentId = documentService.findDocumentByExternalTableIdAndReqDocId(externalTable, externalId, reqDocId);
                String bucketName = "drive-ai-ccm";
                String defaultFilePath = "";
                if (filePath != null) defaultFilePath = filePath;
                String s3FileName = awsService.uploadFile(bucketName, defaultFilePath, newFile, externalTable, externalId, reqDocId);
                S3Asset documentAsset = awsService.getS3ObjectAsset(bucketName, s3FileName);

                JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
                Jwt principalJwt=(Jwt) token.getPrincipal();
                String email = principalJwt.getClaim("email");

               if(oldDocumentId != -1){
                   Document oldDocument = documentService.findDocumentById(oldDocumentId, email);
                   String storageUrl = oldDocument.getStorageUrl();
                   URL url = new URL(storageUrl);
                   String objectKey = url.getPath().substring(1);
                   awsService.deleteObject(bucketName, objectKey);

                   UpdateDocumentDto updateDocumentDto = new UpdateDocumentDto();
                   updateDocumentDto.setStorageUrl("https://" + bucketName + ".s3.amazonaws.com/" + s3FileName);
                   updateDocumentDto.setOcrChecked(oldDocument.getOcrChecked());
                   updateDocumentDto.setStatus(oldDocument.getStatus());
                   documentService.updateDocumentById(oldDocumentId, updateDocumentDto, email);
                } else {
                   CreateDocumentDto createDocumentDto = new CreateDocumentDto();
                   createDocumentDto.setDocumentRequiredId(reqDocId);
                   createDocumentDto.setExternalId(externalId);
                   createDocumentDto.setExternalTable(externalTable);
                   createDocumentDto.setStorageUrl("https://" + bucketName + ".s3.amazonaws.com/" + s3FileName);
                   documentService.saveDocument(createDocumentDto, email);
               }

                return new ResponseEntity<>(documentAsset, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
