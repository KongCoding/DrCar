import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import org.dom4j.Element;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AskInfo implements AISentences {
    private static final String AIname = "Dr. Car: ";
    private DiscoveryGUI ui;
    private String carName;
    private DiscoveryService discovery;
    private String sentenceToRead;
    private ArrayList<String> answer;
    private Element topics;

    public AskInfo(DiscoveryGUI gui, String name){
        ui = gui;
        carName = name;
        ArrayList<String> topicList = new ArrayList<>();
        try{
            topics = Tool.returnGivenCarTopics(carName);
        }catch (Exception e){
            Emergency.emergencyPlanShutDown("Error occurs when read Topics.xml, please check your format");
        }
        List<Element> allTopics = topics.elements();
        for(Element t: allTopics){
            topicList.add(t.getQName().getName().replaceAll("-", " "));
        }
        ui.replaceSelectorItems(topicList.toArray(new String[topicList.size()]));
        discovery = new DiscoveryServiceIBM(name, ui.getFrame()); //ready for connect to service.
    }

    private void askService() throws RuntimeException {
        String question = ui.getInput();
        ui.addText("User: " + question + "\n");
        ui.cleanInput();
        Element topic = topics.element(question.replaceAll(" ", "-"));
        String headLetter = "";
        int [] startAndEnd = new int[1];
        try{
            headLetter = Tool.returnHeadLetter(topic);
            startAndEnd = Tool.returnStartAndEnd(topic);
        }catch (Exception e){
            Emergency.emergencyPlanShutDown("Error occurs when read Topics.xml, please check your format");
        }
        answer = discovery.ask(question);
        if(answer.size() == 0){
            sentenceToRead = "Sorry, I can't answer this question. Please be more specific. " +
                    "What would you like to know about " + carName + "?\n";
            ui.addTextWithTranslation(AIname + sentenceToRead);
        }else{
            ui.addTextWithTranslation(AIname + "There " + (answer.size()==1?"is ":"are ")
                    + answer.size() + " answer(s) for your question: ");
            String bestAnswer = answer.get(0);
            for (String a: answer){
                // && (bestAnswer.indexOf(headLetter) < 0 || bestAnswer.indexOf(headLetter) >= a.indexOf(headLetter))
                if(a.contains(headLetter))
                    bestAnswer = Tool.cutString(a, startAndEnd[0], startAndEnd[1]);
            }
            sentenceToRead = "The first answer is " + bestAnswer;
            ui.addTextWithTranslation("1. " + bestAnswer);
            ui.lockButtons(new int[]{3}, answer.size() == 1);
            if(answer.size() > 1){
                ui.addTextWithTranslation("\n" + AIname + "If you want to view other answers, please " +
                        "click \"View Results\" on the right side. If not, what else would you want to know about " +
                        carName + "?\n");
            }else
                ui.addTextWithTranslation("\n" + AIname + "What else would you like to know about " +
                        carName + "? If you want to ask about other cars, please click \"Return\"\n");
        }
        ui.moveBarBottom();
        ui.addText("");
    }

    private void askUser(){
        sentenceToRead = "What would you like to know about " + carName + "? (Enter question or keyword)\n";
        ui.addTextWithTranslation(AIname + sentenceToRead);
    }

    @Override
    public void initialize() {
        ui.cleanInput();
        askUser();
    }

    @Override
    public void nextSentence() {
        ui.lockButtons(new int[]{1}, false);
    }

    @Override
    public void enter() throws RuntimeException{
        String q = ui.getInput();
        if(!q.equals(""))
            askService();
    }

    @Override
    public String read() {
        return sentenceToRead;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return answer;
    }
}
