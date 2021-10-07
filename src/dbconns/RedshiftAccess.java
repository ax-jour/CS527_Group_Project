package dbconns;

import java.sql.*;

public class RedshiftAccess {

    private String username = "admin";
    private String password = "JU&ki8LO(";

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public void exec(String query) throws Exception {
        try{
            Class.forName("com.amazon.redshift.jdbc.Driver");

            //Open a connection and define properties.
            System.out.println("Connecting to Redshift...");
            connect = DriverManager
                    .getConnection("jdbc:redshift://cs527-cluster.cjrs6ndwim0l.us-east-2.redshift.amazonaws.com:5439/dev?useSSL=false",
                            username,
                            password);

            //Try a simple query.
            System.out.println("Listing system tables...");
            statement = connect.createStatement();
            Boolean isExecuted = statement.execute(query);
            resultSet = statement.getResultSet();
            if (isExecuted) {
                System.out.println("Script Executed!");
                if (resultSet != null) {
                    writeMetaData(resultSet);
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
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

        }
    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        ResultSetMetaData metadata = resultSet.getMetaData();
        // Result set get the result of the SQL query
        System.out.println("The columns in the table are: ");
        System.out.println("Table: " + metadata.getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }

        while (resultSet.next()) {
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
    }
}
