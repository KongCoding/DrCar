import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class Tool {
    public static void main(String[] args) {
        String capacity_output_torque_tp_a = "Max Output 280 hp Engine capacity 3.5 L Max torque 262 lb-ft " +
                "Top speed N/A Acceleration, 0-60 mph 6.9 sec";
        String price = "Prices start at $29,990";
        String wheelbase_dimension = "Wheelbase is 99.6”. Dimensions are 161\" L x 67” W x 60\" H\n";
        String daf = "ACC helps you maintain a set following interval behind a detected vehicle for highway driving. " +
                "Fit for Anything The Fit can take all kinds of people and all kinds of cargo all kinds of places, " +
                "thanks to brilliant engineering and a little magic. The spacious interior has room for five, " +
                "and the 2nd-row Magic Seat® helps you configure your Fit four different ways.";
        String acc = "Each element pushes the limit of what a truck can do to advance its function and expand your " +
                "possibilities. In-Bed Trunk • Only pickup with a lockable trunk • 7.3 cubic feet of secure storage " +
                "5 • Use as a cooler or to stow your dirty gear • Drain plug for quick water removal and easy cleaning";
        String rawString = "The 360˚ SurroundView Camera " +
                "available in the Volvo V60 helps make parking simple by giving " +
                "you a bird's-eye view of your car and its immediate surroundings. " +
                "It's especially useful when fitting into tight spaces, and can help you to avoid parking lot scrapes.";
        System.out.println(cutStringTest(capacity_output_torque_tp_a, 75, 0));
    }

    public static String cutStringTest(String answer, int start, int end){
        end = end == 0? answer.length(): end;
        if(start >= end || end > answer.length())
            return "Error, please check your start and end, total length = " + answer.length();
        else{
            return "Correct, clean answer: " + answer.substring(start, end) + "\nTotal length = " + answer.length();
        }
    }

    public static String cutString(String answer, int start, int end){
        end = end == 0? answer.length(): end;
        if(start >= end || end > answer.length())
            return answer;
        else
            return answer.substring(start, end);
    }

    @SuppressWarnings("all")
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

    @SuppressWarnings("all")
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
    @SuppressWarnings("all")
    public static String returnHeadLetter(Element topic) throws Exception{
        return topic.element("head-letters").getText();
    }
    @SuppressWarnings("all")
    public static int[] returnStartAndEnd(Element topic) throws Exception{
        return new int[]{Integer.parseInt(topic.element("start").getText()), Integer.parseInt(topic.element("end").getText())};
    }
}
