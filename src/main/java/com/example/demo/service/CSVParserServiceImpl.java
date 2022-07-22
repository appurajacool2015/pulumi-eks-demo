package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.CSVEntity;
import com.example.demo.repository.CSVRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class CSVParserServiceImpl implements CSVParserService {

    @Autowired
    CSVRepository csvRepository;

    @Override
    public void readAndSaveCSV(MultipartFile file) {
        //readCSV
        List<String[]> readData = new ArrayList<>();
        try {
            InputStream istream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(istream, "UTF-8"));
            CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).build();                  
            readData = csvReader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<CSVEntity> csvList = new ArrayList<>();
        for(String[] rows: readData) {
            CSVEntity csvEntity = new CSVEntity();
            csvEntity.setCompany(rows[0] + "- EKS");
            csvEntity.setCompanyAddress(rows[1] + "- EKS");
            csvList.add(csvEntity);
         }
        csvRepository.saveAll(csvList);
    }

    @Override
    public List<CSVEntity> getCSV() {
        List<CSVEntity> readAllEntities = csvRepository.findAll();
        return readAllEntities;
    }
    
}
