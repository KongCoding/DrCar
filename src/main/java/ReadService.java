import java.io.File;

public interface ReadService {
    static void Delete(){
        File f = new File("read.wav");
        System.out.println(f.delete());
    }
    void Read(String sentence);
}
