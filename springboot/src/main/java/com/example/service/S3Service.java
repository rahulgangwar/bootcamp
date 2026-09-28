package com.example.service;

import com.example.dto.FileInfo;
import com.example.dto.InitiateUploadResponse;
import com.example.dto.PartUploadUrl;

import com.example.dto.UploadedPart;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final S3Presigner s3Presigner;

    // 10 MB per part
    private static final long PART_SIZE = 10 * 1024 * 1024;

    public void upload(String key, byte[] content, String contentType) {

        PutObjectRequest request =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(contentType)
                        .build();

        s3Client.putObject(request, RequestBody.fromBytes(content));
    }

    public InitiateUploadResponse initiateMultipartUpload(
            String fileName, String contentType, long fileSize) {

        String key = UUID.randomUUID() + "-" + fileName;
        CreateMultipartUploadRequest request =
                CreateMultipartUploadRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(contentType)
                        .build();

        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(request);
        String uploadId = response.uploadId();
        int totalParts = (int) Math.ceil((double) fileSize / PART_SIZE);
        List<PartUploadUrl> parts = new ArrayList<>();
        for (int partNumber = 1; partNumber <= totalParts; partNumber++) {
            UploadPartRequest uploadPartRequest =
                    UploadPartRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .uploadId(uploadId)
                            .partNumber(partNumber)
                            .build();

            PresignedUploadPartRequest presignedRequest =
                    s3Presigner.presignUploadPart(
                            UploadPartPresignRequest.builder()
                                    .signatureDuration(Duration.ofMinutes(15))
                                    .uploadPartRequest(uploadPartRequest)
                                    .build());

            parts.add(new PartUploadUrl(partNumber, presignedRequest.url().toString()));
        }
        return new InitiateUploadResponse(uploadId, key, totalParts, parts);
    }

    public void completeMultipartUpload(
            String uploadId, String key, List<UploadedPart> uploadedParts) {

        List<software.amazon.awssdk.services.s3.model.CompletedPart> awsParts =
                uploadedParts.stream()
                        .sorted(Comparator.comparingInt(UploadedPart::partNumber))
                        .map(
                                part ->
                                        software.amazon.awssdk.services.s3.model.CompletedPart
                                                .builder()
                                                .partNumber(part.partNumber())
                                                .eTag(part.etag())
                                                .build())
                        .toList();

        CompletedMultipartUpload completedUpload =
                CompletedMultipartUpload.builder().parts(awsParts).build();

        CompleteMultipartUploadRequest request =
                CompleteMultipartUploadRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .uploadId(uploadId)
                        .multipartUpload(completedUpload)
                        .build();

        s3Client.completeMultipartUpload(request);
    }

    public List<FileInfo> listFiles() {

        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);
        return response.contents().stream()
                .map(
                        object -> {
                            String key = object.key();
                            String fileName = key.substring(key.indexOf("-") + 1);
                            return new FileInfo(key, fileName, object.size());
                        })
                .toList();
    }

    public String generateDownloadUrl(String key) {

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .responseContentDisposition("attachment")
                        .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
                        .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }

    public void deleteFile(String key) {

        DeleteObjectRequest request =
                DeleteObjectRequest.builder().bucket(bucketName).key(key).build();

        s3Client.deleteObject(request);
    }
}
