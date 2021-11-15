package com.instacart.project.controller;

import com.instacart.project.model.FileDetail;
import com.instacart.project.service.CSVscanner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping(value = "/scanlocalcsv")
    @ResponseBody
    public ResponseEntity<Object> scanCSVfromLocal(@RequestBody FileDetail file) throws Exception {
        try {
            CSVscanner cs = new CSVscanner();
            cs.scanCSVfromLocal(file.getFile_path());
            return new ResponseEntity<>("Printed", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/scans3csv")
    @ResponseBody
    public ResponseEntity<Object> scanCSVfromS3(@RequestBody FileDetail file, String database) throws Exception {
        try {
            CSVscanner cs = new CSVscanner();
            cs.scanCSVfromS3(file, "database");
            return new ResponseEntity<>("Printed", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
