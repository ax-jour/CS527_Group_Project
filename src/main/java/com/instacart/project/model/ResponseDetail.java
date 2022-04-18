package com.instacart.project.model;

import java.sql.ResultSetMetaData;
import java.util.List;

public class ResponseDetail {
    private List colName = null;
    private List rawData = null;
    private long elapsed_time = 0;
    private ResultSetMetaData rsmd = null;

    public List getColName() {
        return colName;
    }

    public void setColName(List colName) {
        this.colName = colName;
    }

    public List getRawData() {
        return rawData;
    }

    public void setRawData(List rawData) {
        this.rawData = rawData;
    }

    public long getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(long elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public ResultSetMetaData getRsmd() {
        return rsmd;
    }

    public void setRsmd(ResultSetMetaData rsmd) {
        this.rsmd = rsmd;
    }
}
