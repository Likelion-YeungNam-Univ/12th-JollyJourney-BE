package com.ll.JollyJourney.global.controller;

import com.ll.JollyJourney.global.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/s3")
@CrossOrigin(origins = "http://localhost:8080") // 필요한 경우 *로 설정
public class S3Controller {
    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = s3Service.uploadFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}