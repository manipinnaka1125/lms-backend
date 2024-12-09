package com.example.demo.controller;

import com.example.demo.model.Material;
import com.example.demo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/materials")
@CrossOrigin
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping
    public ResponseEntity<String> uploadMaterial(@RequestParam String title,
                                                 @RequestParam String description,
                                                 @RequestParam MultipartFile file) {
        materialService.saveMaterial(title, description, file);
        return ResponseEntity.ok("Material uploaded successfully");
    }

    @GetMapping
    public List<Material> getMaterials() {
        return materialService.getAllMaterials();
    }

    @GetMapping("/files/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) {
        Resource file = materialService.getFile(fileName);
 
        // Determine the file type (for PDF, set "application/pdf")
        String contentType = "application/pdf"; // You can make this dynamic if needed.

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"") // Inline view
                .body(file);
    }

}
