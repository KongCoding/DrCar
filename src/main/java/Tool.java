public class Tool {
    public static void main(String[] args) {
        String rawString = "Capacity 2.0L Max Output 187-248hp Max Torque 221-258lb-ft Top Speed 130mph";
        System.out.println(cutString(rawString, 14, 34));
    }

    public static String cutString(String answer, int start, int end){
        if(start >= end || end > answer.length())
            return "Error, please check your start and end, total length = " + answer.length();
        else{
            String returnS = "Correct, clean answer: " + answer.substring(start, end) + "\nTotal length = " + answer.length();
            return returnS;
        }
    }
}
