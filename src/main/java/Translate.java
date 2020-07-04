public interface Translate {
    void setService();
    String Translate(String sentence, String mode);
    String translateFromEnglish(String message, String targetLanguage);
    void translateFromAnyToAny();
}
