package com.example.demo.controllers;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.CSVEntity;
import com.example.demo.repository.CSVRepository;
import com.example.demo.service.CSVParserService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@RestController
public class parseCsvController {

    @Autowired
    CSVParserService csvParserService;

    @Autowired
    CSVRepository csvRepository;

    @GetMapping("/hello")
    public String getHello() {
        return "Hello there!!!";
    }

    @PostMapping("/uploadCSV")
    public String readCSV(MultipartFile file) {
        csvParserService.readAndSaveCSV(file);
        return "Records saved in Postgres";
    }
    
    @GetMapping("/getCSV")
    public void getCSV(HttpServletResponse response) throws Exception {
        // set file name and content type
        String filename = "CustomerDetails";

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerValue = "attachment; " + filename + "_" + currentDateTime + ".csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, headerValue);

        // create a csv writer
        StatefulBeanToCsv<CSVEntity> writer = 
                new StatefulBeanToCsvBuilder<CSVEntity>
                    (response.getWriter())
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false).build();

        // write all employees to csv file
        writer.write(csvParserService.getCSV());
    }

    @GetMapping("/getRecordFromDB")
    public List<CSVEntity> getRecordsFromDB() {
        List<CSVEntity> listEntities = csvRepository.findAll(); 
        if (listEntities!= null) {
            return csvRepository.findAll();
        }
        return null;
    }

    @GetMapping("/deleteAll")
    public String deleteAllRecords() {
        csvRepository.deleteAll();
        return "Deleted All Records";
    }    
}
