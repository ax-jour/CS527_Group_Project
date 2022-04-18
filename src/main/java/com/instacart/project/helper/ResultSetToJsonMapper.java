package com.instacart.project.helper;// convenient JDBC result set to JSON array mapper

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility for converting ResultSets into some Output formats
 * @author marlonlom
 */
public class ResultSetToJsonMapper {
    /**
     * Convert a result set into a JSON Array
     * @param resultSet
     * @return a JSONArray
     * @throws Exception
     */
    public static List<?> convertToJSON(ResultSet resultSet) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<>();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();

        ArrayList<ArrayList<Object>> rawArray = new ArrayList<>();
        while (resultSet.next()) {

            ArrayList<Object> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++){
                Object colVal = resultSet.getObject(i);
                row.add(colVal);
            }
            rawArray.add(row);

            // Represent a row in DB. Key: Column name, Value: Column value
//            Map<String, Object> row = new HashMap<>();
//            for (int i = 1; i <= columnCount; i++) {
//                // Note that the index is 1-based
//                String colName = rsmd.getColumnName(i);
//                Object colVal = resultSet.getObject(i);
//                row.put(colName, colVal);
//            }
//            rows.add(row);
        }
        return rawArray;
    }

    /**
     * Convert a result set into a XML List
     * @param resultSet
     * @return a XML String with list elements
     * @throws Exception if something happens
     */
    public static String convertToXML(ResultSet resultSet) throws Exception {
        StringBuffer xmlArray = new StringBuffer("<results>");
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            xmlArray.append("<result ");
            for (int i = 0; i < total_rows; i++) {
                xmlArray.append(" " + resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase() + "='" + resultSet.getObject(i + 1) + "'"); }
            xmlArray.append(" />");
        }
        xmlArray.append("</results>");
        return xmlArray.toString();
    }
}