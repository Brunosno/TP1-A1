package main.service.minio;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@ApplicationScoped
public class MinioService {

    @Inject
    S3Client s3Client;

    @Inject
    S3Presigner presigner;

    @ConfigProperty(name = "app.s3.bucket")
    String bucketName;

    @PostConstruct
    void ensureBucket() {
        try {
            boolean exists = s3Client.listBuckets().buckets().stream()
                    .anyMatch(b -> b.name().equals(bucketName));
            if (!exists) {
                s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao garantir a existência do bucket no MinIO", e);
        }
    }

    public void upload(String fileName, InputStream inputStream, String contentType, long size) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(contentType)
                    .build();
            byte[] bytes = inputStream.readAllBytes();

            s3Client.putObject(putObjectRequest,
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(bytes));

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar arquivo para o MinIO", e);
        }
    }

    public URL generatePresignedUrl(String fileName, Duration duration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(duration)
                .build();

        PresignedGetObjectRequest presigned = presigner.presignGetObject(presignRequest);
        return presigned.url();
    }

    public URL generatePresignedUrl(String fileName) {
        return generatePresignedUrl(fileName, Duration.ofMinutes(15));
    }

    public void delete(String fileName) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build());
    }
}
