package edu.cmu.pocketsphinx.demo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

import static android.widget.Toast.makeText;
import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

public class attendance extends Activity implements RecognitionListener {

    //This Is Used for getting the result  of the data spoken;
    String name_teacher="";
    String get_course="";
    String get_semester="";

    int count = 0;
    private TextToSpeech tspeech;
    private HashMap<String, Integer> rex;
    private SpeechRecognizer recognizer;
    private static final String KEY = "oh mighty computer";
    private static final String KWS_SE = "wakeup";
    private static final String Menu_Se = "menu";
    private static final String name_search = "name";
    private static final String Course_Search = "course";
    private static final String Semester_Seacrh = "semester";
    String[] arr = {name_search, Course_Search, Semester_Seacrh};
    String[] val;
    TextView smaltext;
    int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rex = new HashMap<>();
        rex.put(KWS_SE, R.string.kws_caption);
        rex.put(name_search, R.string.NameOfTeacher);
        rex.put(Course_Search, R.string.course);
        rex.put(Menu_Se, R.string.attendance_menu);
        rex.put(Semester_Seacrh, R.string.semester);
        setContentView(R.layout.attendance);
        smaltext = (TextView) findViewById(R.id.small);

        val = new String[4];
        ((TextView) findViewById(R.id.title_attendance)).setText("Preparing the recognizer");

        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(attendance.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }


            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    ((TextView) findViewById(R.id.title_attendance))
                            .setText("Failed to init recognizer " + result);
                } else {

                    switchSearch(KWS_SE);


                }
            }
        }.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        recognizer.cancel();
        recognizer.shutdown();
    }

    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;
        String text = hypothesis.getHypstr();
        if (text.equals(KEY)) {
            switchSearch(arr[count]);

            count++;

        }
        ((TextView) findViewById(R.id.resul)).setText(text);


    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        ((TextView) findViewById(R.id.resul)).setText("");
        if (hypothesis != null) {
            String texty = hypothesis.getHypstr();
            Toast.makeText(getApplicationContext(), "index" + index, Toast.LENGTH_SHORT).show();
            try {
                if (index != 4) {
                    val[index] = texty;
                    data(val, index);
                    makeText(getApplicationContext(), "Array from onResult" + val[index], Toast.LENGTH_SHORT).show();
                    index++;
                }
                if (index==4)
                {
                    name_teacher=val[1];
                    get_course=val[2];
                    get_semester=val[3];
                   // makeText(getApplicationContext(),"Name of Teacher"+name_teacher+"\n Course:"+get_course+"\n Semester"+get_semester,Toast.LENGTH_LONG).show();
                   smaltext.setText("Name of Teacher"+name_teacher+"\n Course:"+get_course+"\n Semester"+get_semester);
                    switchSearch(KWS_SE);
                    index=0;
                }





            } catch (ArrayIndexOutOfBoundsException E) {

                makeText(getApplication(), "This is never executed", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    /**
     * We stop recognizer here to get a final result
     */


    @Override
    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().equals(KWS_SE)) {
            try {
                switchSearch(arr[count]);
                count++;

            } catch (ArrayIndexOutOfBoundsException E) {
                Toast.makeText(getApplicationContext(), "Already in Last position dude", Toast.LENGTH_LONG).show();
                count = 0;
                //data(val,index);
                onTimeout();


            }
        }
    }

    private void switchSearch(String searchName) {
        recognizer.stop();

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        if (searchName.equals(KWS_SE))
            recognizer.startListening(searchName);

        else
            recognizer.startListening(searchName, 10000);
        String captionu = getResources().getString(rex.get(searchName));
        ((TextView) findViewById(R.id.title_attendance)).setText(captionu);
    }
    public void data(String a[] ,int ind)
    {

        if(ind==3)
        {
            val[ind] =  a[ind];
            Toast.makeText(getApplicationContext(),"From data"+val[ind],Toast.LENGTH_SHORT).show();
        }
        else
        {
            val[ind]=a[ind];
            Toast.makeText(getApplicationContext(),"its the else part of data"+val[ind],Toast.LENGTH_SHORT).show();

        }

    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                .setRawLogDir(assetsDir)

                // Threshold to tune for keyphrase to balance between false alarms and misses
                .setKeywordThreshold(1e-45f)

                // Use context-independent phonetic search, context-dependent is too slow for mobile
                .setBoolean("-allphone_ci", true)

                .getRecognizer();
        recognizer.addListener(this);

        /** In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        recognizer.addKeyphraseSearch(KWS_SE, KEY);
        //Name grammar
        File namegrammar = new File(assetsDir, "name.gram");
        recognizer.addGrammarSearch(name_search, namegrammar);
        //course grammar
        File coursegrammar = new File(assetsDir, "course.gram");
        recognizer.addGrammarSearch(Course_Search, coursegrammar);
        // Menu grammar
        File menuGrammar = new File(assetsDir, "category.gram");
        recognizer.addGrammarSearch(Menu_Se, menuGrammar);
        //Grammar for semester
        File SemesterGrammar = new File(assetsDir, "semester.gram");
        recognizer.addGrammarSearch(Semester_Seacrh, SemesterGrammar);


    }

    @Override
    public void onError(Exception error) {
        ((TextView) findViewById(R.id.title_attendance)).setText(error.getMessage());
    }

    @Override
    public void onTimeout() {



        switchSearch(KWS_SE);
    }
}
