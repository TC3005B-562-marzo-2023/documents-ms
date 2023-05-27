package com.driveai.documentsms.models;
import lombok.Builder;
import lombok.Value;
import java.net.URL;
@Value
@Builder
public class S3Asset {
    String name;
    String key;
    URL url;
}
