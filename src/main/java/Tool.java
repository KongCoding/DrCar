import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class Tool {
    public static void main(String[] args) {
        String rawString = "Available at speeds up to 8 mph, it’s great when you’re in a tight space and can’t see " +
                "clearly what’s around you. Park Assist Pilot makes parking effortless, every time. " +
                "As you drive by potential spaces, the system assesses the size of " +
                "them and alerts you to any that your car will fit into.";
        System.out.println(cutStringTest(rawString, 114, 0));
    }

    public static String cutStringTest(String answer, int start, int end){
        end = end == 0? answer.length(): end;
        if(start >= end || end > answer.length())
            return "Error, please check your start and end, total length = " + answer.length();
        else{
            String returnS = "Correct, clean answer: " + answer.substring(start, end) + "\nTotal length = " + answer.length();
            return returnS;
        }
    }

    public static String cutString(String answer, int start, int end){
        end = end == 0? answer.length(): end;
        if(start >= end || end > answer.length())
            return answer;
        else
            return answer.substring(start, end);
    }

    public static String[] returnAllCars() throws Exception{
        ArrayList<String> cars = new ArrayList<>();
        Document topics = DiscoveryService.load("Topics.xml");
        Element root = topics.getRootElement();
        List<Element> allCars = root.elements();
        for (Element car: allCars) {
            cars.add(car.element("name").getText());
        }
        return cars.toArray(new String[cars.size()]);
    }

    public static Element returnGivenCarTopics(String carName) throws Exception{
        ArrayList<String> topic = new ArrayList<>();
        Document topics = DiscoveryService.load("Topics.xml");
        Element root = topics.getRootElement();
        Element givenCar = root.element(carName.toLowerCase().replaceAll(" ", "-"));
        Element allTopic = givenCar.element("topic");
//        for(Element t: allTopic){
//            topic.add(t.getQName().getName().replaceAll("-", " "));
//        }
//        return topic.toArray(new String[topic.size()]);
        return allTopic;
    }

    public static String returnHeadLetter(Element topic) throws Exception{
        return topic.element("head-letters").getText();
    }

    public static int[] returnStartAndEnd(Element topic) throws Exception{
        return new int[]{Integer.parseInt(topic.element("start").getText()), Integer.parseInt(topic.element("end").getText())};
    }
}
