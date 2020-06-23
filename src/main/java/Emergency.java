import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Emergency {

    public static void emergencyPlan(String errorMessage){
        JOptionPane.showConfirmDialog(null, errorMessage, "Error", JOptionPane.DEFAULT_OPTION);
        System.exit(0);
    }

    public static void externalWebsite(){
        int response = JOptionPane.showConfirmDialog(null, "This will open external browser. " +
                "Do you want to continue?", "Open Browser", JOptionPane.YES_NO_OPTION);
        if(response == 0){
            Desktop desktop = Desktop.getDesktop();
            try{
                //The URL will be changed to our own website in the future.
                desktop.browse(new URI("ServiceCrash.html"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
