package Helpers;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
public class DataBaseConnect {
    ConfigReader reader=new ConfigReader();
    String relativePath = "CRInquiry.sql";
    String filePath = System.getProperty("user.dir") + "/src/main/resources/" + relativePath;
    String url;
    String CRnumber;
    String user;
    String password;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    public String loadQuery(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
    public void connectWithMojDB(String Env){
        switch (Env){

            case "Testing":
                url=  reader.getProperty("TestingDB-Url");
                user=reader.getProperty("DB_User_test");
                password=reader.getProperty("DB_Password");
                break;

            case "Staging":
                url=  reader.getProperty("");
                user=reader.getProperty("");
                password=reader.getProperty("");
                break;
        }

        try {
        // Load the JDBC driver (optional in newer versions)
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // Establish the connection
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
            try {
                // Create a statement
                statement = connection.createStatement();
            } catch (Exception e){
                throw new Exception ("Failed during create exception");
            }

            try{
            // Execute a query
            String sql = loadQuery(filePath); // Replace with your SQL query
            resultSet = statement.executeQuery(sql);
                System.out.println("Query Executed."+resultSet);
        } catch (Exception e){
                throw new Exception ("Failed during Execute a query");
        }
            // Process the results
            while (resultSet.next()) {
                // Assuming your_table_name has columns named "id" and "name"
                CRnumber = resultSet.getNString("Reference");
                System.out.println("CRnumber comes from DB is" +CRnumber);
                ConfigReader reader = new ConfigReader();
                reader.setProperty("CRnumberFromDB",CRnumber);
                reader.saveProperty("CRnumberFromDB",CRnumber);;
            }
        System.out.println("Connection established successfully!");

    } catch (ClassNotFoundException e) {
        System.out.println("JDBC Driver not found!");
        e.printStackTrace();
    } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        // Close the connection
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
}
