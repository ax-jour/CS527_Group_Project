package com.instacart.project.service;

import com.instacart.project.helper.ResultSetToJsonMapper;
import com.instacart.project.model.ResponseDetail;

import java.sql.*;
import java.util.ArrayList;

public class RetrieveDBlist {
    private String username = "";
    private String mysql_password = "";
    private String redshift_password = "";
    private String sqlserver_password = "";

    private Connection connect = null;
    private DatabaseMetaData rsmd = null;
    private ResultSet rs = null;

    public ResponseDetail retrieveList(String platform) throws Exception {
        try{
            System.out.println("Connecting to server " + platform);
            switch (platform){
                case "mysql":
                    connect = DriverManager
                            .getConnection("jdbc:mysql://cs527-database.c0rg6mabksy4.us-east-2.rds.amazonaws.com/instacart?useSSL=false",
                                    username,
                                    mysql_password);
                    break;
                case "redshift":
                    connect = DriverManager
                            .getConnection("jdbc:mysql://cs527-database.c0rg6mabksy4.us-east-2.rds.amazonaws.com/instacart?useSSL=false",
                                    username,
                                    redshift_password);
                    break;
                case "sqlserver":
                    connect = DriverManager
                            .getConnection("jdbc:sqlserver://cs527-sqlserver2.c0rg6mabksy4.us-east-2.rds.amazonaws.com:1433;databaseName=instacart;user="+username+
                                    ";password="+sqlserver_password+";");
                    break;
                default:
                    connect = DriverManager
                            .getConnection("jdbc:sqlserver://cs527-sqlserver2.c0rg6mabksy4.us-east-2.rds.amazonaws.com:1433;databaseName=instacart;user="+username+
                                    ";password="+sqlserver_password+";");
                    break;
            }

            rsmd = connect.getMetaData();
            rs = rsmd.getCatalogs();

            ResponseDetail rd = new ResponseDetail();
            rd.setColName(ResultSetToJsonMapper.convertToJSON(rs));
            return rd;

        } catch (Exception e) {
            throw e;
        } finally {
            System.out.println("Closing MySQL connection...");
            close();
        }

    }

    private void close() {
        try {
            if (rs != null) {
                rs.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
