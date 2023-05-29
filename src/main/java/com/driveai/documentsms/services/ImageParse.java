package com.driveai.documentsms.services;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.http.impl.io.AbstractMessageWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@Service
public class ImageParse {
    public static String parseImage(MultipartFile file) throws TesseractException, IOException {
        Tesseract tesseract = new Tesseract();
        try {
        if (!file.isEmpty()) {
            File convFile = new File(file.getOriginalFilename());
            file.transferTo(convFile);
            tesseract.setDatapath("target/classes/tessdata");
            String text = tesseract.doOCR(convFile);
            // path of your image file
            System.out.print(text);
            }
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return "test";

    }
}