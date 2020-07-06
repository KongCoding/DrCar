import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DealerDBQuerySQLite implements DealerDBQuery {
    private static final String databaseName = "CarDealer.db";
    private Connection connection;

    private static Connection InitializeConnection(){
        File f = new File(databaseName);
        if(!f.exists()){
            Emergency.emergencyPlanNothing("Sorry, we failed to connect to database");
            return null;
        }

        //This url is only used for Zhenhao Lu's device.
        //String url = "jdbc:sqlite:C:/Users/13667/Downloads/Java-JDBC-Sample-Application/5914_Project/CarDealer.db";

        //This url should work for every device.
        String url = "jdbc:sqlite:" + f.getAbsolutePath();
        Connection conn; // If you create this variable inside the Try block it will be out of scope
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
            conn = null;
            Emergency.emergencyPlanNothing("Sorry, we failed to connect to database");
        }
        return conn;
    }


    public static void main(String[] args) throws SQLException {
        DealerDBQuerySQLite db = new DealerDBQuerySQLite();
        ArrayList<String> answer = db.Search("Volvo", "Ohio");
        System.out.println("Test");
        for (String s: answer) {
            System.out.println(s);
        }
    }

    public DealerDBQuerySQLite(){
        //Frame = frame;
        connection = InitializeConnection();
    }

    @Override
    public boolean checkConnect(){
        return connection != null;
    }

    @Override
    @SuppressWarnings("all")
    public ArrayList<String> setStates(){
        String sqlQuery = "select S.State\n" +
                "from StateShortName as S";
        ArrayList<String> states = new ArrayList<>(){{add("Select A State");}};
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while(rs.next()){
                states.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return states;
    }

    @SuppressWarnings("all")
    public ArrayList<String> Search(String car, String state) throws SQLException{
        String sqlQuery = "Select C.DealerName, C.Address, C.Phone\n";
        sqlQuery += "from '"+ car + "' as C, StateShortName as N\n";
        sqlQuery += "where C.State = '" + state + "' and C.State = N.State";
//        String sqlQuery = "select C.DealerName, C.Address, C.Phone\n" +
//                "from " + car + " as C, StateShortName as N\n" +
//                "where (C.State = '" + state + "' or N.ShortName = '" + state + "') and C.State = N.State";
        ArrayList<String> answer = new ArrayList<>();
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
                if (i < columnCount - 1) {
                    sb.append(": ");
                }else if(i == columnCount - 1)
                    sb.append(", ");
            }
            answer.add(sb.toString());
            n++;
        }
        return answer;
    }
}
