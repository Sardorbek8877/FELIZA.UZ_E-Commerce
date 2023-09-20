package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

@Service
public class ProductImagesService {

    @Autowired
    S3Client s3Client;
    private final  String bucketName = "feliza-files";

    public PutObjectResponse uploadImage(String key, MultipartFile file){
        S3Client s3Client1 = S3Client.builder().build();
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            return s3Client1.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getInputStream().available()));

//            return s3Client.putObject(software.amazon.awssdk.services.s3.model.PutObjectRequest.builder()
//                            .bucket(bucketName)
//                            .key(key)
//                            .build(),
//                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        }
        catch (IOException e){
            throw new IllegalStateException("Failed to upload file", e);
        }
    }
}
