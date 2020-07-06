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
        openMainPage("Do you want to view our website to see what our program can do?");
        frame.setVisible(false);
    }

    public static void emergencyPlanNothing(String errorMessage){
        JOptionPane.showConfirmDialog(null, errorMessage, "Error", JOptionPane.DEFAULT_OPTION);
    }

    public static void externalWebsite(String link, String message){
        if(message.equals(""))
            message = "This will open external browser. Do you want to continue?";
        int response = JOptionPane.showConfirmDialog(null, message, "Open Browser", JOptionPane.YES_NO_OPTION);
        if(response == 0){
            Desktop desktop = Desktop.getDesktop();
            try{
                //The URL will be changed to our own website in the future.
                desktop.browse(new URI(link));
            } catch (URISyntaxException | IOException e) {
                System.out.println("URL is not available.");
            }
        }
    }

    public static void openMainPage(String message){externalWebsite("https://drcar311199443.wordpress.com/home/", message);}
    public static void openTutorial(){externalWebsite("https://drcar311199443.wordpress.com/home/", "");}
    public static void openTutorial1(){externalWebsite("https://drcar311199443.wordpress.com/home/#Translation", "");}
    public static void openTutorial2(){externalWebsite("https://drcar311199443.wordpress.com/home/#Discovery", "");}
    public static void openTutorial3(){externalWebsite("https://drcar311199443.wordpress.com/home/#DealerSearch", "");}
}
