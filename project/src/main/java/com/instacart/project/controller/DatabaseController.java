package com.instacart.project.controller;

import com.instacart.project.model.QueryDetail;
import com.instacart.project.model.ResponseDetail;
import com.instacart.project.service.MySQLconn;
import com.instacart.project.service.Redshiftconn;
import com.instacart.project.service.RetrieveDBlist;
import com.instacart.project.service.SQLServconn;
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

        System.out.println("Request from IP: "+request.getRemoteAddr());
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

        System.out.println("Request from IP: "+request.getRemoteAddr());
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

        System.out.println("Request from IP: "+request.getRemoteAddr());
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

        System.out.println("Request from IP: "+request.getRemoteAddr());
        Redshiftconn conn = new Redshiftconn();
        conn.setDb(query.getDb());
        try{
            ResponseDetail rs = conn.exec(query.getScript());
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
