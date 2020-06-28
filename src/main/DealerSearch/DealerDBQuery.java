import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DealerDBQuery {
    private static final String databaseName = "CarDealer.db";
    private Connection connection;

    private static Connection InitializeConnection(){
        File f = new File("CarDealer.db");

        //This url is only used for Zhenhao Lu's device.
        //String url = "jdbc:sqlite:C:/Users/13667/Downloads/Java-JDBC-Sample-Application/5914_Project/CarDealer.db";

        //This url should work for every device.
        String url = "jdbc:sqlite:" + f.getAbsolutePath();
        Connection conn = null; // If you create this variable inside the Try block it will be out of scope
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                // Provides some positive assurance the connection and/or creation was successful.
                DatabaseMetaData meta = conn.getMetaData();
                System.out
                        .println("The driver name is " + meta.getDriverName());
                System.out.println(
                        "The connection to the database was successful.");
            } else {
                // Provides some feedback in case the connection failed but did not throw an exception.
                System.out.println("Null Connection");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out
                    .println("There was a problem connecting to the database.");
        }
        return conn;
    }

    public static void main(String[] args) {
        DealerDBQuery db = new DealerDBQuery();
        ArrayList<String> answer = db.Search("Volvo", "Ohio");
        System.out.println("Test");
        for (String s: answer) {
            System.out.println(s);
        }
    }

    public DealerDBQuery(){
        connection = InitializeConnection();
    }

    @SuppressWarnings("all")
    public ArrayList<String> Search(String car, String state){
        String sqlQuery = "select C.DealerName, C.Address\n" +
                "from " + car + " as C, StateShortName as N\n" +
                "where (C.State = '" + state + "' or N.ShortName = '" + state + "') and C.State = N.State";
        ArrayList<String> answer = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            int columnCount = rs.getMetaData().getColumnCount();
            int n = 1;
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append(n + ". ");
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = rs.getString(i);
                    sb.append(columnValue);
                    if (i < columnCount) {
                        sb.append(": ");
                    }
                }
                answer.add(sb.toString());
                n++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
