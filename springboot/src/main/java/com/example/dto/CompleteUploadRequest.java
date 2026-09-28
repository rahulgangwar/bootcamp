package com.example.dto;


import java.util.List;

public record CompleteUploadRequest(String uploadId, String key, List<UploadedPart> parts) {}
