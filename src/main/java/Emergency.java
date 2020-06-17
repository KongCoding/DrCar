import javax.swing.*;

public class Emergency {

    public static void emergencyPlan(String errorMessage){
        JOptionPane.showConfirmDialog(null, errorMessage, "Error", JOptionPane.DEFAULT_OPTION);
        System.exit(0);
    }
}
