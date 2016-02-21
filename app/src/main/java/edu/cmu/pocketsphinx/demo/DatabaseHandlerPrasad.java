package edu.cmu.pocketsphinx.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Iterator;
import java.util.TreeSet;


public class DatabaseHandlerPrasad extends SQLiteOpenHelper {
    Context l;
    public static final int database_version=1;
    public static final String DataBase_name="Prasad.db";
    public static final String Table_name="MainTable";
    public static  final String roll_no="rollno";
    public static final String Firstname="FirstName";
    public static final String Last_name="LastName";
    public static final String Email_id="EmailId";
    public static final String col_phone="PhoneNo";
    public static final String Course="Course";
    String[] Table_info_attend={"IF1G_JUNE","IF1G_JULY","IF1G_AUGUST","IF1G_SEPT","IF1G_OCT","IF2G_DEC","IF2G_JAN","IF2G_FEB","IF2G_MARCH",
            "IF3G_JUNE","IF3G_JULY","IF3G_AUGUST","IF3G_SEPT","IF3G_OCT","IF4G_DEC","IF4G_JAN","IF4G_FEB","IF4G_MARCH",
            "IF5G_JUNE","IF5G_JULY","IF5G_AUGUST","IF5G_SEPT","IF5G_OCT","IF6G_DEC","IF6G_JAN","IF6G_FEB","IF6G_MARCH"};
    String[] Table_info_attend_Computer={"CO1G_JUNE","CO1G_JULY","CO1G_AUGUST","CO1G_SEPT","CO1G_OCT","CO2G_DEC","CO2G_JAN","CO2G_FEB","CO2G_MARCH",
            "CO3G_JUNE","CO3G_JULY","CO3G_AUGUST","CO3G_SEPT","CO3G_OCT","CO4G_DEC","CO4G_JAN","CO4G_FEB","CO4G_MARCH",
            "CO5G_JUNE","CO5G_JULY","CO5G_AUGUST","CO5G_SEPT","CO5G_OCT","CO6G_DEC","CO6G_JAN","CO6G_FEB","CO6G_MARCH"};
    String[] Table_info_attend_Electronics={"EJ1G_JUNE","EJ1G_JULY","EJ1G_AUGUST","EJ1G_SEPT","EJ1G_OCT","EJ2G_DEC","EJ2G_JAN","EJ2G_FEB","EJ2G_MARCH",
            "EJ3G_JUNE","EJ3G_JULY","EJ3G_AUGUST","EJ3G_SEPT","EJ3G_OCT","EJ4G_DEC","EJ4G_JAN","EJ4G_FEB","EJ4G_MARCH",
            "EJ5G_JUNE","EJ5G_JULY","EJ5G_AUGUST","EJ5G_SEPT","EJ5G_OCT","EJ6G_DEC","EJ6G_JAN","EJ6G_FEB","EJ6G_MARCH"};

    public static final String Trigger_after="after_insert";
    public static final String Table_Information="Information";
    public static final String Table_Computer="Computer";
    public static final String Table_Electronics="Electronics";
    public static final String table_query = " CREATE TABLE " + Table_name + "(" +
            roll_no + " INTEGER NOT NULL, " +
            Firstname + " TEXT  NOT NULL, " +
            Course  + " TEXT NOT NULL  , "+
            Last_name+ " TEXT NOT NULL , " +
            Email_id + " TEXT NOT NULL , " +
            col_phone + " TEXT NOT NULL  "+
            ");";

    public static final String Information_Query=" CREATE TABLE " + Table_Information + " (\n" +
            "\t`rollno`\tINTEGER,\n" +
            "\t`FirstName`\tTEXT NOT NULL,\n" +
            "\t`LastName`\tTEXT NOT NULL,\n" +
            "\t`EmailId`\tTEXT NOT NULL,\n" +
            "\t`PhoneNo`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(rollno)\n" +
            ")";
    public  static final String Computer_Query="CREATE TABLE " + Table_Computer +  " (\n" +
            "\t`rollno`\tINTEGER,\n" +
            "\t`FirstName`\tTEXT NOT NULL,\n" +
            "\t`LastName`\tTEXT NOT NULL,\n" +
            "\t`EmailId`\tTEXT NOT NULL,\n" +
            "\t`PhoneNo`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(rollno)\n" +
            ")";
    public static final String Electronics_Query="CREATE TABLE " + Table_Electronics+"(\n" +
            "\t`rollno`\tINTEGER,\n" +
            "\t`FirstName`\tTEXT NOT NULL,\n" +
            "\t`LastName`\tTEXT NOT NULL,\n" +
            "\t`EmailId`\tTEXT NOT NULL,\n" +
            "\t`PhoneNo`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(rollno)\n" +
            ")";
    public static final String trigger_after_insert_Information="CREATE TRIGGER after_insert_Information\n" +
            "\n" +
            "after insert on MainTable for each row when new.Course=\"Information\"\n" +
            "\n" +
            "begin\n" +
            "\n" +
            "insert into  Information (rollno,FirstName,LastName,EmailId,PhoneNo) values(new.rollno,new.FirstName,new.LastName,new.EmailId,new.PhoneNo);\n" +
            "\t\n" +
            "\tend";
    public static final String trigger_after_insert_Computer="CREATE TRIGGER after_insert_Computer\n" +
            "\n" +
            "after insert on MainTable for each row when new.Course=\"Computer\"\n" +
            "\n" +
            "begin\n" +
            "\n" +
            "insert into  Computer (rollno,FirstName,LastName,EmailId,PhoneNo) values(new.rollno,new.FirstName,new.LastName,new.EmailId,new.PhoneNo);\n" +
            "\t\n" +
            "\tend";
    public static final String trigger_after_insert_Electronics="CREATE TRIGGER after_insert_Electronics\n" +
            "\n" +
            "after insert on MainTable for each row when new.Course=\"Electronics\"\n" +
            "\n" +
            "begin\n" +
            "\n" +
            "insert into  Electronics (rollno,FirstName,LastName,EmailId,PhoneNo) values(new.rollno,new.FirstName,new.LastName,new.EmailId,new.PhoneNo);\n" +
            "\t\n" +
            "\tend";


    public DatabaseHandlerPrasad(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DataBase_name, factory, database_version);
        l=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //using an array to create table of six semester for information
        for(String tb_name:Table_info_attend) {
            String table_attend_query = "CREATE TABLE " + tb_name + " (\n" +
                    "\t`rollno`\tINTEGER,\n" +
                    "\t`1`\tTEXT,\n" +
                    "\t`2`\tTEXT,\n" +
                    "\t`3`\tTEXT,\n" +
                    "\t`4`\tTEXT,\n" +
                    "\t`5`\tTEXT,\n" +
                    "\t`6`\tTEXT,\n" +
                    "\t`7`\tTEXT,\n" +
                    "\t`8`\tTEXT,\n" +
                    "\t`9`\tTEXT,\n" +
                    "\t`10`\tTEXT,\n" +
                    "\t`11`\tTEXT,\n" +
                    "\t`12`\tTEXT,\n" +
                    "\t`13`\tTEXT,\n" +
                    "\t`14`\tTEXT,\n" +
                    "\t`15`\tTEXT,\n" +
                    "\t`16`\tTEXT,\n" +
                    "\t`17`\tTEXT,\n" +
                    "\t`18`\tTEXT,\n" +
                    "\t`19`\tTEXT,\n" +
                    "\t`20`\tTEXT,\n" +
                    "\t`21`\tTEXT,\n" +
                    "\t`22`\tTEXT,\n" +
                    "\t`23`\tTEXT,\n" +
                    "\t`24`\tTEXT,\n" +
                    "\t`25`\tTEXT,\n" +
                    "\t`26`\tTEXT,\n" +
                    "\t`27`\tTEXT,\n" +
                    "\t`28`\tTEXT,\n" +
                    "\t`29`\tTEXT,\n" +
                    "\t`30`\tTEXT,\n" +
                    "\t`31`\tTEXT\n" +

                    ")";
            db.execSQL(table_attend_query);
        }
        //using an array to create table of sex semester for Computer
        for(String tb_name:Table_info_attend_Computer) {
            String table_attend_query = "CREATE TABLE " + tb_name + " (\n" +
                    "\t`rollno`\tINTEGER,\n" +
                    "\t`1`\tTEXT,\n" +
                    "\t`2`\tTEXT,\n" +
                    "\t`3`\tTEXT,\n" +
                    "\t`4`\tTEXT,\n" +
                    "\t`5`\tTEXT,\n" +
                    "\t`6`\tTEXT,\n" +
                    "\t`7`\tTEXT,\n" +
                    "\t`8`\tTEXT,\n" +
                    "\t`9`\tTEXT,\n" +
                    "\t`10`\tTEXT,\n" +
                    "\t`11`\tTEXT,\n" +
                    "\t`12`\tTEXT,\n" +
                    "\t`13`\tTEXT,\n" +
                    "\t`14`\tTEXT,\n" +
                    "\t`15`\tTEXT,\n" +
                    "\t`16`\tTEXT,\n" +
                    "\t`17`\tTEXT,\n" +
                    "\t`18`\tTEXT,\n" +
                    "\t`19`\tTEXT,\n" +
                    "\t`20`\tTEXT,\n" +
                    "\t`21`\tTEXT,\n" +
                    "\t`22`\tTEXT,\n" +
                    "\t`23`\tTEXT,\n" +
                    "\t`24`\tTEXT,\n" +
                    "\t`25`\tTEXT,\n" +
                    "\t`26`\tTEXT,\n" +
                    "\t`27`\tTEXT,\n" +
                    "\t`28`\tTEXT,\n" +
                    "\t`29`\tTEXT,\n" +
                    "\t`30`\tTEXT,\n" +
                    "\t`31`\tTEXT\n" +
                    ")";
            db.execSQL(table_attend_query);
        }
        for(String tb_name:Table_info_attend_Electronics) {
            String table_attend_query = "CREATE TABLE " + tb_name + " (\n" +
                    "\t`rollno`\tINTEGER,\n" +
                    "\t`1`\tTEXT,\n" +
                    "\t`2`\tTEXT,\n" +
                    "\t`3`\tTEXT,\n" +
                    "\t`4`\tTEXT,\n" +
                    "\t`5`\tTEXT,\n" +
                    "\t`6`\tTEXT,\n" +
                    "\t`7`\tTEXT,\n" +
                    "\t`8`\tTEXT,\n" +
                    "\t`9`\tTEXT,\n" +
                    "\t`10`\tTEXT,\n" +
                    "\t`11`\tTEXT,\n" +
                    "\t`12`\tTEXT,\n" +
                    "\t`13`\tTEXT,\n" +
                    "\t`14`\tTEXT,\n" +
                    "\t`15`\tTEXT,\n" +
                    "\t`16`\tTEXT,\n" +
                    "\t`17`\tTEXT,\n" +
                    "\t`18`\tTEXT,\n" +
                    "\t`19`\tTEXT,\n" +
                    "\t`20`\tTEXT,\n" +
                    "\t`21`\tTEXT,\n" +
                    "\t`22`\tTEXT,\n" +
                    "\t`23`\tTEXT,\n" +
                    "\t`24`\tTEXT,\n" +
                    "\t`25`\tTEXT,\n" +
                    "\t`26`\tTEXT,\n" +
                    "\t`27`\tTEXT,\n" +
                    "\t`28`\tTEXT,\n" +
                    "\t`29`\tTEXT,\n" +
                    "\t`30`\tTEXT,\n" +
                    "\t`31`\tTEXT\n" +
                    ")";
            db.execSQL(table_attend_query);
        }

        //Creation of Trigger for information table

            String trigger_info_table="CREATE TRIGGER after_insert_on_info_table \n" +
                    "after insert on " + Table_Information +  " for each row begin \n" +
                    "insert into  " + Table_info_attend[0] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[1] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[2] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[3] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[4] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[5] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[6] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[7] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[8] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[9] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[10] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[11] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[12] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[13] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[14] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[15] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[16] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[17] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[18] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[19] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[20] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[21] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[22] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[23] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[24] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[25] + " (rollno) values(new.rollno) ;\n" +
                    "insert into  " + Table_info_attend[26] + " (rollno) values(new.rollno) ;\n" +
                    "end";
        //creation of trigger for Computer table
        String trigger_co_table="CREATE TRIGGER after_insert_on_Computer \n" +
                "after insert on " + Table_Computer + " for each row begin\n" +
                "insert into  "  + Table_info_attend_Computer[0] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[1] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[2] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[3] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[4] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[5] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[6] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[7] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[8] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[9] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[10] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[11] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[12] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[13] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[14] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[15] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[16] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[17] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[18] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[19] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[20] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[21] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[22] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[23] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[24] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[25] +  " (rollno) values(new.rollno) ;\n" +
                "insert into  "  + Table_info_attend_Computer[26] +  " (rollno) values(new.rollno) ;\n" +
                "end";
        //creation of table for Electronics
        String trigger_Ej_insert="CREATE TRIGGER after_insert_EJ\n" +
                "after insert on " + Table_Electronics + " for each row begin\n" +
                "insert into  " + Table_info_attend_Electronics[0] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[1] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[2] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[3] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[4] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[5] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[6] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[7] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[8] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[9] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[10] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[11] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[12] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[13] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[14] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[15] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[16] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[17] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[18] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[19] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[20] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[21] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[22] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[23] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[24] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[25] + " (rollno) values(new.rollno) ;\n" +
                "insert into  " + Table_info_attend_Electronics[26] + " (rollno) values(new.rollno) ;\n" +
                "end";

        db.execSQL(table_query);
        db.execSQL(Information_Query);
        db.execSQL(Electronics_Query);
        db.execSQL(Computer_Query);
        db.execSQL(trigger_after_insert_Information);
        db.execSQL(trigger_after_insert_Computer);
        db.execSQL(trigger_after_insert_Electronics);
        db.execSQL(trigger_info_table);
        db.execSQL(trigger_co_table);
        db.execSQL(trigger_Ej_insert);

    }
    public void update(String course, String semester, TreeSet<String> Student_numbers,String status) {
        String tablename, day;
        Calendar cal = Calendar.getInstance();
        String month = String.valueOf(1 + (cal.get(Calendar.MONTH)));
        day = String.valueOf(cal.get(Calendar.DATE));
        tablename = Tablenamereturns(course, semester, month);
        Toast.makeText(l, "Table Name" + tablename, Toast.LENGTH_LONG).show();
        Toast.makeText(l, "date" + day, Toast.LENGTH_LONG).show();
        ContentValues vs = new ContentValues();
        vs.put(day, "present");
        SQLiteDatabase db = getWritableDatabase();
        TreeSet<String> S = new TreeSet<>(Student_numbers);
        String[] numbers = S.toArray(new String[S.size()]);

        if (status.equalsIgnoreCase("present")) {

            for (String number : numbers) {
                Toast.makeText(l, "number" + number, Toast.LENGTH_LONG).show();
                db.execSQL("update " + tablename + " \n" +
                        "set \"" + day + "\"=\"present\"\n" +
                        "where rollno = " + number + " \n");


            }

        }
        else
        {
            for(String number:numbers)
            {
                Toast.makeText(l, "calling from else part:absent", Toast.LENGTH_SHORT).show();
                db.execSQL("update " + tablename + " \n" +
                        "set \"" + day + "\"=\"absent\"\n" +
                        "where rollno = " + number + " \n");
            }
        }
    }





    public String Tablenamereturns(String course,String semster,String month)
    {


        switch(course)
        {
            case "information":
            {
                switch(semster)
                {
                    case "first":
                    {
                        if(month.equals("6"))
                        {
                            return("IF1G_JUNE");

                        }

                        if(month.equals("7"))
                        {
                            return ("IF1G_JULY");
                        }
                        if(month.equals("8"))
                            return ("IF1G_AUGUST");
                        if(month.equals("9"))
                            return ("IF1G_SEPT");
                        if(month.equals("10"))
                            return ("IF1G_OCT");

                        break;
                    }
                    case "second":
                    {
                        System.out.println("month"+month);
                        if(month.equals("12"))
                            return("IF2G_DEC");
                        if(month.equals("1"))
                            return("IF2G_JAN");
                        if(month.equals("2"))
                            return("IF2G_FEB");
                        if(month.equals("3"))
                            return("IF2G_MARCH");
                        break;
                    }
                    case "third":
                    {
                        if(month.equals("6"))
                            return("IF3G_JUNE");
                        if(month.equals("7"))
                            return("IF3G_JULY");
                        if(month.equals("8"))
                            return("IF3G_AUGUST");
                        if(month.equals("9"))
                            return("IF3G_SEPT");
                        if(month.equals("10"))
                            return("IF3G_OCT");
                        break;
                    }
                    case "fourth":
                    {   if(month.equals("12"))
                        return("IF4G_DEC");
                        if(month.equals("1"))
                            return("IF4G_JAN");
                        if(month.equals("2"))
                            return("IF4G_FEB");
                        if(month.equals("3"))
                            return("IF4G_MARCH");
                        break;
                    }
                    case "fifth":
                    {
                        if(month.equals("6"))
                            return("IF5G_JUNE");
                        if(month.equals("7"))
                            return("IF5G_JULY");
                        if(month.equals("8"))
                            return("IF5G_AUGUST");
                        if(month.equals("9"))
                            return("IF5G_SEPT");
                        if(month.equals("10"))
                            return("IF5G_OCT");

                        break;
                    }
                    case "sixth":
                    {
                        if(month.equals("12"))
                            return("IF6G_DEC");
                        if(month.equals("1"))
                            return("IF6G_JAN");
                        if(month.equals("2"))
                            return("IF6G_FEB");
                        if(month.equals("3"))
                            return("IF6G_MARCH");
                        break;
                    }
                }
                break;
            }
            case "electronics":
            {
                switch(semster)
                {
                    case "first":
                    {
                        if(month.equals("6"))
                        {
                            return("EJ1G_JUNE");

                        }

                        if(month.equals("7"))
                        {
                            return ("EJ1G_JULY");
                        }
                        if(month.equals("8"))
                            return ("EJ1G_AUGUST");
                        if(month.equals("9"))
                            return ("EJ1G_SEPT");
                        if(month.equals("10"))
                            return ("EJ1G_OCT");

                        break;
                    }
                    case "second":
                    {
                        System.out.println("month"+month);
                        if(month.equals("12"))
                            return("EJ2G_DEC");
                        if(month.equals("1"))
                            return("EJ2G_JAN");
                        if(month.equals("2"))
                            return("EJ2G_FEB");
                        if(month.equals("3"))
                            return("EJ2G_MARCH");
                        break;
                    }
                    case "third":
                    {
                        if(month.equals("6"))
                            return("EJ3G_JUNE");
                        if(month.equals("7"))
                            return("EJ3G_JULY");
                        if(month.equals("8"))
                            return("EJ3G_AUGUST");
                        if(month.equals("9"))
                            return("EJ3G_SEPT");
                        if(month.equals("10"))
                            return("EJ3G_OCT");
                        break;
                    }
                    case "fourth":
                    {   if(month.equals("12"))
                        return("EJ4G_DEC");
                        if(month.equals("1"))
                            return("EJ4G_JAN");
                        if(month.equals("2"))
                            return("EJ4G_FEB");
                        if(month.equals("3"))
                            return("EJ4G_MARCH");
                        break;
                    }
                    case "fifth":
                    {
                        if(month.equals("6"))
                            return("EJ5G_JUNE");
                        if(month.equals("7"))
                            return("EJ5G_JULY");
                        if(month.equals("8"))
                            return("EJ5G_AUGUST");
                        if(month.equals("9"))
                            return("EJ5G_SEPT");
                        if(month.equals("10"))
                            return("EJ5G_OCT");

                        break;
                    }
                    case "sixth":
                    {
                        if(month.equals("12"))
                            return("EJ6G_DEC");
                        if(month.equals("1"))
                            return("EJ6G_JAN");
                        if(month.equals("2"))
                            return("EJ6G_FEB");
                        if(month.equals("3"))
                            return("EJ6G_MARCH");
                        break;
                    }
                }
                break;
            }




            case "computer":
            {
                switch(semster)
                {
                    case "first":
                    {
                        if(month.equals("6"))
                        {
                            return("CO1G_JUNE");

                        }

                        if(month.equals("7"))
                        {
                            return ("CO1G_JULY");
                        }
                        if(month.equals("8"))
                            return ("CO1G_AUGUST");
                        if(month.equals("9"))
                            return ("CO1G_SEPT");
                        if(month.equals("10"))
                            return ("CO1G_OCT");

                        break;
                    }
                    case "second":
                    {
                        System.out.println("month"+month);
                        if(month.equals("12"))
                            return("CO2G_DEC");
                        if(month.equals("1"))
                            return("CO2G_JAN");
                        if(month.equals("2"))
                            return("CO2G_FEB");
                        if(month.equals("3"))
                            return("CO2G_MARCH");
                        break;
                    }
                    case "third":
                    {
                        if(month.equals("6"))
                            return("CO3G_JUNE");
                        if(month.equals("7"))
                            return("CO3G_JULY");
                        if(month.equals("8"))
                            return("CO3G_AUGUST");
                        if(month.equals("9"))
                            return("CO3G_SEPT");
                        if(month.equals("10"))
                            return("CO3G_OCT");
                        break;
                    }
                    case "fourth":
                    {   if(month.equals("12"))
                        return("CO4G_DEC");
                        if(month.equals("1"))
                            return("CO4G_JAN");
                        if(month.equals("2"))
                            return("CO4G_FEB");
                        if(month.equals("3"))
                            return("CO4G_MARCH");
                        break;
                    }
                    case "fifth":
                    {
                        if(month.equals("6"))
                            return("CO5G_JUNE");
                        if(month.equals("7"))
                            return("CO5G_JULY");
                        if(month.equals("8"))
                            return("CO5G_AUGUST");
                        if(month.equals("9"))
                            return("CO5G_SEPT");
                        if(month.equals("10"))
                            return("CO5G_OCT");

                        break;
                    }
                    case "sixth":
                    {
                        if(month.equals("12"))
                            return("CO6G_DEC");
                        if(month.equals("1"))
                            return("CO6G_JAN");
                        if(month.equals("2"))
                            return("CO6G_FEB");
                        if(month.equals("3"))
                            return("CO6G_MARCH");
                        break;
                    }
                }






                break;
            }
            default:
                return("no record found");
        }
        return null;




    }

    public void register(Student s)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vs  =new ContentValues();
        vs.put(roll_no,s.getRollno());
        vs.put(Firstname,s.getFirst());
        vs.put(Last_name,s.getLast());
        vs.put(col_phone,s.getPhone());
        vs.put(Email_id,s.getEmailid());
        vs.put(Course,s.getCourse());
        db.insert(Table_name,null,vs);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        for(String temp_table:Table_info_attend)
        {
            String drop_query="DROP TABLE IF EXISTS " + temp_table;
            db.execSQL(drop_query);
        }
        for(String temp:Table_info_attend_Computer)
        {
            String drop_query="DROP TABLE IF EXIST " + temp;
            db.execSQL(drop_query);
        }
        for(String temp:Table_info_attend_Electronics)
        {
            String drop_query="DROP TABLE IF EXIST " + temp;
            db.execSQL(drop_query);
        }
        //Droping all tables if they are already present in sd card;
        String query=" DROP TABLE IF EXISTS " + Table_name;
        String drop_computer="DROP TABLE IF EXISTS " +Table_Computer;
        String drop_Infrormation="DROP TABLE IF EXISTS " + Table_Information;
        String drop_Electronics="DROP TABLE IF EXISTS " +Table_Electronics;

        String trigger_query="DROP TRIGGER " + Trigger_after;

        db.execSQL(query);
        db.execSQL(drop_computer);
        db.execSQL(drop_Electronics);
        db.execSQL(drop_Infrormation);

        db.execSQL(trigger_query);
        db.execSQL(trigger_after_insert_Electronics);
        db.execSQL(trigger_after_insert_Computer);
        db.execSQL(trigger_after_insert_Information);
        onCreate(db);
    }
}


