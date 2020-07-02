import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Emergency {

    public static void emergencyPlanShutDown(String errorMessage){
        JOptionPane.showConfirmDialog(null, errorMessage, "Error", JOptionPane.DEFAULT_OPTION);
        System.exit(0);
    }

    public static void emergencyPlanCLoseWindow(JFrame frame, String errorMessage){
        JOptionPane.showConfirmDialog(null, errorMessage, "Error", JOptionPane.DEFAULT_OPTION);
        frame.setVisible(false);
    }

    public static void externalWebsite(String link){
        int response = JOptionPane.showConfirmDialog(null, "This will open external browser. " +
                "Do you want to continue?", "Open Browser", JOptionPane.YES_NO_OPTION);
        if(response == 0){
            Desktop desktop = Desktop.getDesktop();
            try{
                //The URL will be changed to our own website in the future.
                desktop.browse(new URI(link));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void openMainPage(){externalWebsite("ServiceCrash.html");}
    public static void openTutorial(){externalWebsite("ServiceCrash.html");}
    public static void openTutorial1(){externalWebsite("ServiceCrash.html");}
    public static void openTutorial2(){externalWebsite("ServiceCrash.html");}
    public static void openTutorial3(){externalWebsite("ServiceCrash.html");}
}
