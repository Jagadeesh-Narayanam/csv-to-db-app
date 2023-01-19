package com.springframework.csvtodbapp.controllers;

import com.springframework.csvtodbapp.helpers.CSVHelper;
import com.springframework.csvtodbapp.helpers.ResponseMessage;
import com.springframework.csvtodbapp.services.CSVFileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/csv")
public class CSVController {

    CSVFileService fileService;

    public CSVController(CSVFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return new ResponseMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return new ResponseMessage(message);
            }
        }

        message = "Please upload a csv file!";
        return new ResponseMessage(message);
    }


}
