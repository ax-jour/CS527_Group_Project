package com.instacart.project.service;

import com.instacart.project.helper.ResultSetToJsonMapper;
import com.instacart.project.model.ResponseDetail;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Redshiftconn {

    private String username = "";
    private String password = "";

    private String db = null;
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public String getDb() {
        return this.db;
    }

    public void setDb(String db_name) {
        this.db = db_name;
    }

    public ResponseDetail exec(String query) throws Exception {
        try{
            //Open a connection and define properties.
            connect = DriverManager
                    .getConnection("jdbc:redshift://cs527-cluster.cjrs6ndwim0l.us-east-2.redshift.amazonaws.com:5439/"+getDb()+"?useSSL=false",
                            username,
                            password);

            //Try a simple query.
            statement = connect.createStatement();

            //Execute the query and calculate the execution time.
            Instant start = Instant.now();
            Boolean isExecuted = statement.execute(query);
            System.out.println("Executed!");
            resultSet = statement.getResultSet();
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();  //in millis

            ResponseDetail rd = new ResponseDetail();
            rd.setElapsed_time(timeElapsed);

            ResultSetMetaData rsmd = resultSet.getMetaData();

            int count = rsmd.getColumnCount();
            ArrayList<String> colls = new ArrayList<>();
            for(int i = 1; i<=count; i++) {
                colls.add(rsmd.getColumnName(i));
            }

            rd.setColName(colls);
            rd.setRsmd(rsmd);

            if (isExecuted && resultSet != null) {
                rd.setRawData(ResultSetToJsonMapper.convertToJSON(resultSet));
                return rd;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        return null;
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
