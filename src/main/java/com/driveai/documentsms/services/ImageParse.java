package com.driveai.documentsms.services;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageParse {
    public static String recognizeText(InputStream inputStream) throws IOException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("target/classes/tessdata");
        tesseract.setLanguage("eng");
        BufferedImage image = ImageIO.read(inputStream);

        try {
            return tesseract.doOCR(image);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
