import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class Tool {
    public static void main(String[] args) {
        String capacity_output_torque_tp_a = "Max Output 190 hp Engine capacity 1.5 L Max torque 179 lb-ft Top speed 130 mph Acceleration, " +
                "0-69 mph 7.6 sec";
        String price = "Prices starting at $25,050";
        String wheelbase_dimension = "Wheelbase is 104.7”. Dimensions are 182\" L x 73” W x 66-67\" H";
        String ab1 = "CMBS can help bring your vehicle to a stop by automatically applying brake pressure when the " +
                "system determines that a frontal collision is unavoidable. With your hands on the steering wheel, " +
                "long highway drives are easier with LKAS, which subtly adjusts steering to help keep your vehicle " +
                "centered in a detected lane.";
        String acc = "D - Adaptive Cruise Control (ACC) 17 ACC helps you maintain a set following interval behind a " +
                "detected vehicle for highway driving. 1 280 hp @6000 rpm ( SAE net ) . " +
                "2 Maximum towing capacity for AWD models is 5,000 lbs. Maximum towing capacity for 2WD models is 3,500 lbs.";
        String rawString = "The 360˚ SurroundView Camera " +
                "available in the Volvo V60 helps make parking simple by giving " +
                "you a bird's-eye view of your car and its immediate surroundings. " +
                "It's especially useful when fitting into tight spaces, and can help you to avoid parking lot scrapes.";
        System.out.println(cutStringTest(acc, 4, 131));
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
