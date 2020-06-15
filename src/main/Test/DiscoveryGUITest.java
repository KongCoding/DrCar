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

    @Test
    public final void cleanInputTest(){
        DiscoveryGUI2 gui = createDiscoveryGUI();
        gui.userInput("Hello, world");
        gui.cleanInput();
        String input = gui.getInput();
        Assert.assertEquals(input, "");
    }

    @Test
    public final void lockButtonsTest(){
        DiscoveryGUI2 gui = createDiscoveryGUI();
        gui.lockButtons(new int[]{0,1,2,3}, false);
        gui.lockButtons(new int[]{1,2}, true);
        Assert.assertTrue(gui.getRightButtonsState(0));
        Assert.assertTrue(!gui.getRightButtonsState(1));
        Assert.assertTrue(!gui.getRightButtonsState(2));
        Assert.assertTrue(gui.getRightButtonsState(3));

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
