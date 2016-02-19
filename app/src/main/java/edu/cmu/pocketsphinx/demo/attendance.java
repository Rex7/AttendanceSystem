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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

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
    String status_st="";
    String numbers="";
    String [] array;

    HashMap<String,String> S = new HashMap<>();
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
    private static final String PresentOrAbsent="Status.gram";
    private static final String NumbersOfStudents="number";
    String[] arr = {name_search, Course_Search, Semester_Seacrh,PresentOrAbsent,NumbersOfStudents};
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
        rex.put(PresentOrAbsent,R.string.PresentOrAbsent);
        rex.put(NumbersOfStudents,R.string.NumberofStudent);
        //mapping of words to numbers
        S.put("ONE", "1");S.put("TWO", "2");S.put("THREE", "3");S.put("FOUR", "4");S.put("FIVE", "5");S.put("SIX", "6");S.put("SEVEN", "7");S.put("EIGHT", "8");S.put("NINE", "9");S.put("TEN", "10");
        S.put("ELEVEN", "11");S.put("TWELVE", "12");S.put("THIRTEEN", "13");S.put("FOURTEEN", "14");S.put("FIFTEEN", "15");S.put("SIXTEEN", "16");S.put("SEVENTEEN", "17");S.put("EIGHTEEN", "18");S.put("NINETEEN", "19");S.put("TWENTY", "20");
        S.put("TWENTY-ONE", "21");S.put("TWENTY-TWO", "22");S.put("TWENTY-THREE", "23");S.put("TWENTY-FOUR", "24");S.put("TWENTY-FIVE", "25");S.put("TWENTY-SIX", "26");S.put("TWENTY-SEVEN", "27");S.put("TWENTY-EIGHT", "28");S.put("TWENTY-NINE", "29");S.put("THIRTY", "30");
        S.put("THIRTY-ONE", "31");S.put("THIRTY-TWO", "32");S.put("THIRTY-THREE", "33");S.put("THIRTY-FOUR", "34");S.put("THIRTY-FIVE", "35");S.put("THIRTY-SIX", "36");S.put("THIRTY-SEVEN", "37");S.put("THIRTY-EIGHT", "38");S.put("THIRTY-NINE", "39");S.put("FORTY", "40");
        S.put("FORTY-ONE", "41");S.put("FORTY-TWO", "42");S.put("FORTY-THREE", "43");S.put("FORTY-FOUR", "44");S.put("FORTY-FIVE", "45");S.put("FORTY-SIX", "46");S.put("FORTY-SEVEN", "47");S.put("FORTY-EIGHT", "48");S.put("FORTY-NINE", "49");S.put("FIFTY", "50");
        S.put("FIFTY-ONE", "51");S.put("FIFTY-TWO", "52");S.put("FIFTY-THREE", "53");S.put("FIFTY-FOUR", "54");S.put("FIFTY-FIVE", "55");S.put("FIFTY-SIX", "56");S.put("FIFTY-SEVEN", "57");S.put("FIFTY-EIGHT", "58");S.put("FIFTY-NINE", "59");S.put("SIXTY", "60");
        S.put("SIXTY-ONE", "61");S.put("SIXTY-TWO", "62");S.put("SIXTY-THREE", "63");S.put("SIXTY-FOUR", "64");S.put("SIXTY-FIVE", "65");S.put("SIXTY-SIX", "66");S.put("SIXTY-SEVEN", "67");S.put("SIXTY-EIGHT", "68");S.put("SIXTY-NINE", "69");S.put("SEVENTY", "70");
        S.put("SEVENTY-ONE", "71");S.put("SEVENTY-TWO", "72");S.put("SEVENTY-THREE", "73");S.put("SEVENTY-FOUR", "74");S.put("SEVENTY-FIVE", "75");S.put("SEVENTY-SIX", "76");S.put("SEVENTY-SEVEN", "77");S.put("SEVENTY-EIGHT", "78");S.put("SEVENTY-NINE", "79");S.put("EIGHTY", "80");
        S.put("EIGHTY-ONE", "81");S.put("EIGHTY-TWO", "82");S.put("EIGHTY-THREE", "83");S.put("EIGHTY-FOUR", "84");S.put("EIGHTY-FIVE", "85");S.put("EIGHTY-SIX", "86");S.put("EIGHTY-SEVEN", "87");S.put("EIGHTY-EIGHT", "88");S.put("EIGHTY-NINE", "89");S.put("NINETY", "90");
        S.put("NINETY-ONE", "91");S.put("NINETY-TWO", "92");S.put("NINETY-THREE", "93");S.put("NINETY-FOUR", "94");S.put("NINETY-FIVE", "95");S.put("NINETY-SIX", "96");S.put("NINETY-SEVEN", "97");S.put("NINETY-EIGHT", "98");S.put("NINETY-NINE", "99");S.put("HUNDRED", "100");


        setContentView(R.layout.attendance);
        // helps to link design/content to java file , r is a file of all variables n constants , layout.atendance is xml contains all designor variableswidget also
        smaltext = (TextView) findViewById(R.id.small);

        val = new String[6];
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
                if (index != 6) {
                    val[index] = texty;
                    //data(val, index);
                    makeText(getApplicationContext(), "Array from onResult" + val[index], Toast.LENGTH_SHORT).show();
                    index++;
                }
                if (index==6)
                {
                    name_teacher=val[1];
                    get_course=val[2];
                    get_semester=val[3];
                    status_st=val[4];
                    numbers=val[5];
                    array=numbers.split(" ");
                    String [] n = new String[array.length];
                    for(int i=0;i<array.length;i++)
                    {
                        String temp=S.get(array[i]);
                        n[i]=temp;


                    }
                    List<String> num= Arrays.asList(n);
                    TreeSet<String> s = new TreeSet<>(num);
                    for(String fin:s)
                            Toast.makeText(getApplicationContext(),"number array "+fin,Toast.LENGTH_LONG).show();


                   // makeText(getApplicationContext(),"Name of Teacher"+name_teacher+"\n Course:"+get_course+"\n Semester"+get_semester,Toast.LENGTH_LONG).show();
                   smaltext.setText("Name of Teacher"+name_teacher+"\n Course:"+get_course+"\n Semester"+get_semester+"Status of attendance is:"+status_st+"Number of student"+array[0]);
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
    /*
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
    */

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
        //grammar for present or absent;
        File PresentAbsent = new File(assetsDir,"Status.gram");
        recognizer.addGrammarSearch(PresentOrAbsent,PresentAbsent);
        //lanugae model for numbers
        File Numbers=new File(assetsDir,"number.dmp");
        recognizer.addNgramSearch(NumbersOfStudents,Numbers);


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
