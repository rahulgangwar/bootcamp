package com.example.controller;

import com.example.dto.CompleteUploadRequest;
import com.example.dto.FileInfo;
import com.example.dto.InitiateUploadRequest;
import com.example.dto.InitiateUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.service.S3Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class S3FileController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        String key = UUID.randomUUID() + "-" + file.getOriginalFilename();
        s3Service.upload(key, file.getBytes(), file.getContentType());
        return "redirect:/file-upload";
    }

    @GetMapping("/file-upload")
    public String home() {
        return "file-upload";
    }

    @PostMapping("/api/uploads/initiate")
    @ResponseBody
    public InitiateUploadResponse initiateUpload(@RequestBody InitiateUploadRequest request) {
        return s3Service.initiateMultipartUpload(
                request.fileName(), request.contentType(), request.fileSize());
    }

    @PostMapping("/api/uploads/complete")
    @ResponseBody
    public String completeUpload(@RequestBody CompleteUploadRequest request) {
        s3Service.completeMultipartUpload(request.uploadId(), request.key(), request.parts());
        return "Upload completed successfully";
    }

    @GetMapping("/api/files")
    @ResponseBody
    public List<FileInfo> listFiles() {
        return s3Service.listFiles();
    }

    @GetMapping("/api/files/download")
    @ResponseBody
    public String downloadFile(@RequestParam String key) {
        return s3Service.generateDownloadUrl(key);
    }

    @DeleteMapping("/api/files")
    @ResponseBody
    public String deleteFile(@RequestParam String key) {
        s3Service.deleteFile(key);
        return "File deleted successfully";
    }
}
