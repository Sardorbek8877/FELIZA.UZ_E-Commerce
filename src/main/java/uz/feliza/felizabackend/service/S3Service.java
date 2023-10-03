package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3Client;
    public S3Service(S3Client s3Client){
        this.s3Client = s3Client;
    }

    public PutObjectResponse uploadImage2(MultipartFile file, String fileKey){
        final  String bucketName = "feliza-files";

        try {
            return s3Client.putObject(software.amazon.awssdk.services.s3.model.PutObjectRequest.builder()
                    .bucket(bucketName).key(fileKey).build(),
                    RequestBody.fromInputStream(file.getInputStream(),
                    file.getSize()));
        }
        catch (S3Exception e) {
            // AWS SDK-spezifische Ausnahme (SdkClientException und SdkServiceException)
            System.err.println("Error uploading file to S3: " + e.getMessage());
            throw new IllegalStateException("Failed to upload file to S3", e);
        }
        catch (IOException e){
            // IO-Ausnahme
            System.err.println("IO error while uploading file to S3: " + e.getMessage());
            throw new IllegalStateException("Failed to upload file", e);
        }
    }
}
