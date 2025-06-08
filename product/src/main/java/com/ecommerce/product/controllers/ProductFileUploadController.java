package com.ecommerce.product.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/product/files")
public class ProductFileUploadController {

        @PostMapping(value = "/upload", consumes = "multipart/form-data")
        public ResponseEntity<String> handleFileUpload(
                @RequestParam("file") MultipartFile file,
                @RequestParam("description") String description) {

            // Process the file and description
            String filename = file.getOriginalFilename();
            long size = file.getSize();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Received file: " + filename + " (size: " + size + "), description: " + description);
        }
}

