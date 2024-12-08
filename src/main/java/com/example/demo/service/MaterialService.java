package com.example.demo.service;

import com.example.demo.model.Material;
import com.example.demo.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public void saveMaterial(String title, String description, MultipartFile file) {
        try {
            Material material = new Material();
            material.setTitle(title);
            material.setDescription(description);
            material.setFileName(file.getOriginalFilename());
            material.setFileData(file.getBytes());
            materialRepository.save(material);
        } catch (IOException e) {
            throw new RuntimeException("Error saving material: " + e.getMessage(), e);
        }
    }

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public Resource getFile(String fileName) {
        Material material = materialRepository.findByFileName(fileName);
        return new ByteArrayResource(material.getFileData());
    }
}
