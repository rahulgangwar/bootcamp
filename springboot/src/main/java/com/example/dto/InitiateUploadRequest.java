package com.example.dto;

public record InitiateUploadRequest(String fileName, String contentType, long fileSize) {}
