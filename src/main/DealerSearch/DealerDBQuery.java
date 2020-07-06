import java.sql.SQLException;
import java.util.ArrayList;

public interface DealerDBQuery {
    boolean checkConnect();
    ArrayList<String> Search(String car, String state) throws SQLException;
    ArrayList<String> setStates();
}
