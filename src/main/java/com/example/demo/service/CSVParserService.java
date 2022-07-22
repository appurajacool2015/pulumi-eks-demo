package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.example.demo.model.CSVEntity;


public interface CSVParserService {

    void readAndSaveCSV(MultipartFile file);
    List<CSVEntity> getCSV();
}
