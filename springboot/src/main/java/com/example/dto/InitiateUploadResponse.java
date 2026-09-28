package com.example.dto;


import java.util.List;

public record InitiateUploadResponse(
        String uploadId,
        String key,
        int totalParts,
        List<PartUploadUrl> parts
) {
}