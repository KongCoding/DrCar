import java.util.ArrayList;

public interface AISentences {
    void initialize();
    void nextSentence();
    void enter();
    String read();
    ArrayList<String> getAnswers();
}
