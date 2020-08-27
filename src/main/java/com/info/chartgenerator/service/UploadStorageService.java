package com.info.chartgenerator.service;

import com.info.chartgenerator.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadStorageService {

    private final Path fileLocation;

    @Autowired
    public UploadStorageService(FileStorageProperties fileStorageProperties) {
        this.fileLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath()
                .normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> storeFile(MultipartFile file) {
        Map<String, String> fileValue = new HashMap<>();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileValue.put("fileName", fileName);
        Path targetLocation = this.fileLocation.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileValue.put("filePath", targetLocation.toString());
        return fileValue;
    }

}
