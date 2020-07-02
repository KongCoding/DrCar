import java.util.ArrayList;

public interface DealerDBQuery {
    ArrayList<String> Search(String car, String state);
    ArrayList<String> setStates();
}
