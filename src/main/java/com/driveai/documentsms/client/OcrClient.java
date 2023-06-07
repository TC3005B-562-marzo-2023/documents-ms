package com.driveai.documentsms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name="tesseract-mzookkvtxq-uw", url="https://tesseract-mzookkvtxq-uw.a.run.app/")
public interface OcrClient {

    // Opcion 1: @RequestBody OcrDto ocrDto
    @PostMapping(value ="/rest/ocr",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String getOCR(@RequestParam String language, @RequestPart MultipartFile image);
}
