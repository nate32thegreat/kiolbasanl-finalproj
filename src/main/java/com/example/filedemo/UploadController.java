package com.example.filedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
public class UploadController {

  @Autowired
  FileValidator fileValidator;

  Logger logger = LoggerFactory.getLogger(UploadController.class);

  @PostMapping("/upload")
  Map<String, Object> uploadFile(@ValidFile @RequestBody MultipartFile file) throws Exception {

    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    Map<String, Object> fileInfo = new HashMap<>();
    fileInfo.put("name", file.getName());
    fileInfo.put("originalFilename", file.getOriginalFilename());
    fileInfo.put("contentType", file.getContentType());
    fileInfo.put("size", file.getSize());

    // Make a folder to save files to.
    File filesDirectory = new File("/tmp/file-demo");
    if (!filesDirectory.exists()) {
      if (!filesDirectory.mkdirs()) {
        throw new IOException("Could not create directory " + filesDirectory);
      }
    }

    String newLocation = filesDirectory.getAbsolutePath() + File.separator + file.getOriginalFilename();
    logger.info("File will be saved to {}", newLocation);

    file.transferTo(new File(newLocation));
    return fileInfo;

  }

  @GetMapping("/")
  public String index() {
    return "upload a file";
  }

}

