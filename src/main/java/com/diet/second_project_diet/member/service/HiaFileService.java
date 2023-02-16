package com.diet.second_project_diet.member.service;

import java.net.URLEncoder;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HiaFileService {
    @Value("${file.image.member}") String member_image_path;

    // 파일저장
    public String saveImageFile(MultipartFile file) throws Exception{
        Path targetLocation = Paths.get(member_image_path);
        String originFileName = file.getOriginalFilename();
        targetLocation = targetLocation.resolve(originFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return originFileName;
    }

    // 파일가져오기
    public ResponseEntity<Resource> getImagFile(String fileName) throws Exception{
        Path imgLocation = Paths.get(member_image_path + "/" + fileName).normalize();
        Resource r = new UrlResource(imgLocation.toUri());
        String contentType = "application/octet-stream";
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\""+ URLEncoder.encode(r.getFilename(), "UTF-8"))
            .body(r);
    }
}
