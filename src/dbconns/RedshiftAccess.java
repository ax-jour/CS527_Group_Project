package dbconns;

import java.sql.*;

public class RedshiftAccess {

    private String username = "admin";
    private String password = "JU&ki8LO(";

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public void queryRedshift(String db, String query) throws Exception {
        try{
            Class.forName("com.amazon.redshift.jdbc.Driver");

            //Open a connection and define properties.
            System.out.println("Connecting to Redshift...");
            connect = DriverManager
                    .getConnection("jdbc:redshift://cs527-cluster.cjrs6ndwim0l.us-east-2.redshift.amazonaws.com:5439/"+db,
                            username,
                            password);

            //Try a simple query.
            System.out.println("Listing system tables...");
            statement = connect.createStatement();
            resultSet = statement.executeQuery(query);
            writeMetaData(resultSet);
            System.out.println("Result: " + resultSet);

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
        // Result set get the result of the SQL query
        System.out.println("The columns in the table are: ");
        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }
}
