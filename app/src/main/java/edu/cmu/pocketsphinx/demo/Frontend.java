package edu.cmu.pocketsphinx.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Regis on 13-02-2016.
 */
public class Frontend extends Activity implements AdapterView.OnItemSelectedListener{
    EditText first_name, last_name,  phone, emailid, rollno;
    String course="";
    Spinner cours;
    String[] arr={"Information","Computer","Electronics"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrollgui);
        first_name = (EditText) findViewById(R.id.FirstName);
        last_name = (EditText) findViewById(R.id.LastName);
        cours = (Spinner) findViewById(R.id.Course);
        phone = (EditText) findViewById(R.id.PhoneNo);
        emailid = (EditText) findViewById(R.id.EmailId);
        rollno=(EditText)findViewById(R.id.Rollno);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,arr);
        cours.setAdapter(ad);
        cours.setOnItemSelectedListener(this);
    }

    public void Submit(View v) {


        String firstname = first_name.getText().toString();
        String last = last_name.getText().toString();

        String phones = phone.getText().toString();
        int roll= Integer.parseInt(rollno.getText().toString());
        String email = emailid.getText().toString();
        Student s = new Student(firstname,last,email,phones,course,roll);
        DatabaseHandlerSudhir h = new DatabaseHandlerSudhir(this,null,null,1);
        DatabaseHandlerPrasad p = new DatabaseHandlerPrasad(this,null,null,1);
        p.register(s);
        h.register(s);
    }

    public void clear(View v) {
        first_name.setText("");
        last_name.setText("");
        phone.setText("");
        emailid.setText("");
        rollno.setText("");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView v = (TextView)view;
        course=v.getText().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


