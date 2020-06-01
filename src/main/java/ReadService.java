import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.text_to_speech.v1.util.WaveUtils;

import javax.sound.sampled.*;
import java.io.*;

public class ReadService {
    private static final String Apikey = "tEL_HsyVts87fP3YmKAdzXDS9A2CEqkfB3snpFLEU361";
    private static final String URL = "https://api.us-south.text-to-speech.watson.cloud.ibm.com/instances/d1bd69e2-bbc2-4dcd-9b3c-2b21925f7c83";

    private String dramaSentence = "Please enter the information you would like to access. If you would like to search other cars, please click \"Return\"";
    private TextToSpeech textToSpeech;
    public ReadService(){
        IamAuthenticator authenticator1 = new IamAuthenticator(Apikey);
        textToSpeech = new TextToSpeech(authenticator1);
        textToSpeech.setServiceUrl(URL);
    }

    public void Read(String sentence){
//        SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder().text(sentence)
//                .accept("audio/wav").voice(trans + "-" + voices.get(trans)).build();
        SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder().text(dramaSentence)
                .accept("audio/wav").voice("en-US_EmilyV3Voice").build();
        InputStream inputStream = textToSpeech.synthesize(synthesizeOptions).execute().getResult();
        try{
            InputStream in = WaveUtils.reWriteWaveHeader(inputStream);
            OutputStream out = new FileOutputStream("read.wav");
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.close();
            in.close();
            inputStream.close();
            Play();
        }catch (IOException ioe){
            System.out.println("An IOException happened in ReadService.Read(String sentence)");
        }
    }

    public static void Delete(){
        File f = new File("read.wav");
        System.out.println(f.delete());
    }


    private void Play(){
        try {
            File readFile = new File("read.wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(readFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            stream.close();
        }
        catch (Exception e) {
            System.out.println("Error Playing");
        }
    }
}
