package com.diet.second_project_diet.food2.service;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
  @Value("${file.image.dailydiet}") String diet_image_path;
  // 파일을 저장
  public String saveImageFile( MultipartFile file) throws Exception {
    // 파일이 저장될 폴더의 경로를 가져와서
    Path targetLocation = Paths.get(diet_image_path);
    String originFileName = file.getOriginalFilename();
    // 입력된 파일명을 추가하여, 저장될 위치를 설정하고
    targetLocation = targetLocation.resolve(originFileName);
    // 입력된 파일을 저장될 위치에 복사한다.
    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    // 저장된 위치를 문자열로 변환해서 내어준다.
    return originFileName;
  }

  // 파일을 가져오기
  public ResponseEntity<Resource> getImageFile(String filename) throws Exception {
    Path imgLocation = Paths.get(diet_image_path+"/"+filename).normalize();
    Resource r = new UrlResource(imgLocation.toUri());
    String contentType = "application/octet-stream";

    return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, 
        "attachment; filename=\""+ URLEncoder.encode(r.getFilename(), "UTF-8")).body(r);
  }
}
