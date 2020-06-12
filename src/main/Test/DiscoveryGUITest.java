import org.junit.Assert;
import org.junit.Test;

public class DiscoveryGUITest {

    private DiscoveryGUI2 createDiscoveryGUI(){
        DiscoveryGUI2 gui =  new DiscoveryGUI2();
        gui.openGUI();
        return gui;
    }

    @Test
    public final void addTextTest(){
        DiscoveryGUI2 gui = createDiscoveryGUI();
        String message = "Hello World";
        gui.addText(message);
        String messageTest = gui.getChat();
        Assert.assertEquals(message + "\n", messageTest);
    }

    @Test
    public final void cleanTextTest(){
        DiscoveryGUI2 gui = createDiscoveryGUI();
        String message = "Hello World";
        gui.addText(message);
        gui.cleanText();
        String chat = gui.getChat();
        Assert.assertTrue(chat.equals(""));
    }

//    @Test
//    public final void addTextWithTranslationTest(){
//        DiscoveryGUI2 gui = createDiscoveryGUI();
//        String answerExpected = "最高速度为 30 mph。\n";
//        String answer = "The top speed is 30 mph.";
//        gui.setLanguage("Chinese");
//        gui.addTextWithTranslation(answer);
//        Assert.assertEquals(answerExpected, gui.getChat());
//    }
}
