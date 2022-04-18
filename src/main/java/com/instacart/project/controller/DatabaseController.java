package com.instacart.project.controller;

import com.instacart.project.model.MongoQueryDetail;
import com.instacart.project.model.QueryDetail;
import com.instacart.project.model.ResponseDetail;
import com.instacart.project.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/db")
public class DatabaseController {

    @GetMapping(value = "/getDbList")
    @ResponseBody
    public ResponseEntity<Object> getDbList(@RequestParam String platform, HttpServletRequest request) throws Exception {

        System.out.println("Request getDbList from: "+request.getRemoteAddr());
        RetrieveDBlist conn = new RetrieveDBlist();
        try {
            ResponseDetail rd = conn.retrieveList(platform);
            return new ResponseEntity<>(rd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/mysql")
    @ResponseBody
    public ResponseEntity<Object> mysqlQuery(@RequestBody QueryDetail query, HttpServletRequest request) throws Exception {

        System.out.println("Request mysql from: "+request.getRemoteAddr());
        System.out.println("Script: "+query.getScript());
        MySQLconn conn = new MySQLconn();
        conn.setDb(query.getDb());
        try {
            ResponseDetail rd = conn.exec(query.getScript());
            return new ResponseEntity<>(rd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/sqlserver")
    @ResponseBody
    public ResponseEntity<Object> sqlServerQuery(@RequestBody QueryDetail query, HttpServletRequest request) throws Exception {

        System.out.println("Request sqlServer from: "+request.getRemoteAddr());
        System.out.println("Script: "+query.getScript());
        SQLServconn conn = new SQLServconn();
        conn.setDb(query.getDb());
        try {
            ResponseDetail rd = conn.exec(query.getScript());
            return new ResponseEntity<>(rd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/redshift")
    @ResponseBody
    public ResponseEntity<Object> redshiftQuery(@RequestBody QueryDetail query, HttpServletRequest request) throws Exception {

        System.out.println("Request redshift from: "+request.getRemoteAddr());
        System.out.println("Script: "+query.getScript());
        Redshiftconn conn = new Redshiftconn();
        conn.setDb(query.getDb());
        try{
            ResponseDetail rs = conn.exec(query.getScript());
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/mongodb")
    @ResponseBody
    public ResponseEntity<Object> mongodbQuery(@RequestBody QueryDetail query, HttpServletRequest request) throws Exception {
        System.out.println("Request mongodb from: "+request.getRemoteAddr());
        System.out.println("Script: "+query.getScript());
        MongoDBconn conn = new MongoDBconn();
        conn.setDb(query.getDb());
        try{
            ResponseDetail rs = conn.exec(query.getScript());
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
