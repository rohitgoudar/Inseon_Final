package com.appdeveloperrohitgmail.inseon_final;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.SmileRating;

public class Question_Option extends AppCompatActivity {

    Boolean internet;
    FirebaseDatabase database1;
    DatabaseReference question_reference;
    TextView[] q_text = new TextView[7];
    CheckBox checkBox1[],checkBox2[],checkBox3[],checkBox4[],checkBox5[],checkBox6[],checkBox7[];
    LinearLayout layout_q2, layout_q3, layout_q4, layout_q5, layout_q6, layout_q7;

    String[] question_text = new String[7], option_high = new String[7], option_low = new String[7];
    String[][] options_check = new String[12][10];
    String[] shared_question_text = new String[7], shared_option_high = new String[7], shared_option_low = new String[7];
    String[] option11,option12,option13,option14,option15,option16,option17,option18,option19,option20,option21,option22;
    String[] shared_option11,shared_option12,shared_option13,shared_option14,shared_option15,shared_option16,shared_option17,shared_option18,shared_option19,shared_option20,shared_option21,shared_option22;
    int option11_len,option12_len,option13_len,option14_len,option15_len,option16_len,option17_len,option18_len,option19_len,option20_len,option21_len,option22_len;
    int shared_option11_len,shared_option12_len,shared_option13_len,shared_option14_len,shared_option15_len,shared_option16_len,shared_option17_len,shared_option18_len,shared_option19_len,shared_option20_len,shared_option21_len,shared_option22_len;
    int option_no = 11, check_no = 11, check_no1 = 0, option_no1 = 0, count_question_no,shared_count_question_no, check_id = 0, flag, checkBox_id_number = 0;
    int[] count_options = new int[10];
    int i=0;

    SharedPreferences sharedPreferences_StoreFromFirebase, sharedPreferences_RetriveFromFirebase;
    SmileRating smileRating1, smileRating2, smileRating3, smileRating4, smileRating5, smileRating6, smileRating7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question__option);

        internet = haveNetworkConnection();
        database1 = FirebaseDatabase.getInstance();

        q_text[0] = (TextView) findViewById(R.id.Question1_text);
        q_text[1] = (TextView) findViewById(R.id.Question2_text);
        q_text[2] = (TextView) findViewById(R.id.Question3_text);
        q_text[3] = (TextView) findViewById(R.id.Question4_text);
        q_text[4] = (TextView) findViewById(R.id.Question5_text);
        q_text[5] = (TextView) findViewById(R.id.Question6_text);
        q_text[6] = (TextView) findViewById(R.id.Question7_text);

        smileRating1 = (SmileRating) findViewById(R.id.smiley_rating1);
        smileRating2 = (SmileRating) findViewById(R.id.smiley_rating2);
        smileRating3 = (SmileRating) findViewById(R.id.smiley_rating3);
        smileRating4 = (SmileRating) findViewById(R.id.smiley_rating4);
        smileRating5 = (SmileRating) findViewById(R.id.smiley_rating5);
        smileRating6 = (SmileRating) findViewById(R.id.smiley_rating6);
        smileRating7 = (SmileRating) findViewById(R.id.smiley_rating7);

        layout_q2 = (LinearLayout)findViewById(R.id.layout_q2);
        layout_q3 = (LinearLayout)findViewById(R.id.layout_q3);
        layout_q4 = (LinearLayout)findViewById(R.id.layout_q4);
        layout_q5 = (LinearLayout)findViewById(R.id.layout_q5);
        layout_q6 = (LinearLayout)findViewById(R.id.layout_q6);
        layout_q7 = (LinearLayout)findViewById(R.id.layout_q7);

        if (internet) {
            Store_From_Firebase();
            //SmilyRate();
            Log.d("internet","internet is there");
        } else {
            Retrive_From_Shared();

            Log.d("internet","internet is not there");
        }
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void Store_From_Firebase()
    {
        Log.d("internet","internet store from firebase");
        question_reference =database1.getReference("userid1/surveys/survey1");

        question_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                sharedPreferences_StoreFromFirebase = getSharedPreferences("storeFromFirebase", Context.MODE_PRIVATE);
                SharedPreferences.Editor store_from_editor=sharedPreferences_StoreFromFirebase.edit();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    int q_no=1;
                    if (dataSnapshot1.getKey().equals("questions")) {
                        count_question_no = (int) dataSnapshot1.getChildrenCount();
                        store_from_editor.putInt("count_question_no",count_question_no);
                        Log.d("count_question","  "+count_question_no);
                        for (DataSnapshot dataSnapshot11 : dataSnapshot1.getChildren()) {
                            if (dataSnapshot11.getKey().equals("q"+q_no)) {
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()) {
                                    if (dataSnapshot111.getKey().equals("question")) {
                                        question_text[q_no-1]=dataSnapshot111.getValue().toString();
                                        Log.d("question_text",q_no+"  "+question_text[q_no-1]);
                                        store_from_editor.putString("question_text"+q_no,question_text[q_no-1]);
                                        //q_text[q_no-1].setText(question_text[q_no-1]);

                                    }
                                    if (dataSnapshot111.getKey().equals("high")) {
                                        option_high[q_no-1]=dataSnapshot111.getValue().toString();
                                        Log.d("qoptions_high","  "+ option_high[q_no-1]);
                                        store_from_editor.putString("option_high"+q_no,option_high[q_no-1]);

                                    }
                                    if (dataSnapshot111.getKey().equals("low")) {
                                        option_low[q_no-1]=dataSnapshot111.getValue().toString();
                                        Log.d("qoptions_low","  "+ option_low[q_no-1]);
                                        store_from_editor.putString("option_low"+q_no,question_text[q_no-1]);
                                    }
                                }
                            }
                            q_no=q_no+1;
                        }
                    }
                    store_from_editor.apply();
                    if (dataSnapshot1.getKey().equals("options")){
                        for (DataSnapshot dataSnapshot11 : dataSnapshot1.getChildren()){
                            if (dataSnapshot11.getKey().equals("option"+option_no)){
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    if (dataSnapshot111.getKey().equals("ch"+check_no)){
                                        options_check[option_no1][check_no1] = dataSnapshot111.getValue().toString();
                                        Log.d("check"," option_No "+(option_no1)+"check_No "+(check_no1)+"check  "+(check_no)+" "+options_check[option_no1][check_no1]);
                                        check_no1++;
                                        check_no++;
                                    }
                                }
                                option_no++;
                                option_no1++;
                                check_no1=0;
                            }
                            if (dataSnapshot11.getKey().equals("option11")){
                                int a=0;
                                option11_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option11_len", option11_len );
                                option11 = new String[option11_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option11[a]= dataSnapshot111.getValue().toString();
                                    a++;
                                   // Log.d("option11"," "+option11[a]);
                                }
                                StringBuilder sb11= new StringBuilder();
                                for (int i = 0; i < option11_len; i++) {
                                    sb11.append(option11[i]).append(",");
                                }
                                store_from_editor.putString("option11", sb11.toString());
                            }
                            if (dataSnapshot11.getKey().equals("option12")){
                                int b=0;
                                option12_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option12_len", option12_len );
                                option12 = new String[option12_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option12[b]= dataSnapshot111.getValue().toString();
                                    b++;
                                    //Log.d("option12"," "+option12[b]);
                                }
                                StringBuilder sb12= new StringBuilder();
                                for (int i = 0; i < option12_len; i++) {
                                    sb12.append(option12[i]).append(",");
                                }
                                store_from_editor.putString("option12", sb12.toString());
                            }
                            if (dataSnapshot11.getKey().equals("option13")){
                                int c=0;
                                option13_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option13_len", option13_len );
                                option13 = new String[option13_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option13[c]= dataSnapshot111.getValue().toString();
                                    c++;
                                    //Log.d("option13"," "+option13[c]);
                                }
                                StringBuilder sb13= new StringBuilder();
                                for (int i = 0; i < option13_len; i++) {
                                    sb13.append(option13[i]).append(",");
                                }
                                store_from_editor.putString("option13", sb13.toString());
                            }
                            if (dataSnapshot11.getKey().equals("option14")){
                                int d=0;
                                option14_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option14_len", option14_len );
                                option14 = new String[option14_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option14[d]= dataSnapshot111.getValue().toString();
                                    d++;
                                }
                                StringBuilder sb14= new StringBuilder();
                                for (int i = 0; i < option14_len; i++) {
                                    sb14.append(option14[i]).append(",");
                                }
                                store_from_editor.putString("option14", sb14.toString());
                            }
                            if (dataSnapshot11.getKey().equals("option15")){
                                int e=0;
                                option15_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option15_len", option15_len );
                                option15 = new String[option15_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option15[e]= dataSnapshot111.getValue().toString();
                                    e++;
                                }
                                StringBuilder sb15 = new StringBuilder();
                                for (int i = 0; i < option15_len; i++) {
                                    sb15.append(option15[i]).append(",");
                                }
                                store_from_editor.putString("option15", sb15.toString());
                            }
                            if (dataSnapshot11.getKey().equals("option16")){
                                int f=0;
                                option16_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option16_len", option16_len );
                                option16 = new String[option16_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option16[f]= dataSnapshot111.getValue().toString();
                                    Log.d("option16",""+option16[f]);
                                    f++;
                                }
                                StringBuilder sb16 = new StringBuilder();
                                for (int i = 0; i < option16_len; i++) {
                                    sb16.append(option16[i]).append(",");
                                }
                                store_from_editor.putString("option16", sb16.toString());

                            }
                            if (dataSnapshot11.getKey().equals("option17")){
                                int g=0;
                                option17_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option17_len", option17_len );
                                option17 = new String[option17_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option17[g]= dataSnapshot111.getValue().toString();
                                    g++;
                                }
                                StringBuilder sb17 = new StringBuilder();
                                for (int i = 0; i < option17_len; i++) {
                                    sb17.append(option17[i]).append(",");
                                }
                                store_from_editor.putString("option17", sb17.toString());
                            }
                            if (dataSnapshot11.getKey().equals("option18")){
                                int h=0;
                                option18_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option18_len", option18_len );
                                option18 = new String[option18_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option18[h]= dataSnapshot111.getValue().toString();
                                    h++;
                                }
                                StringBuilder sb18 = new StringBuilder();
                                for (int i = 0; i < option18_len; i++) {
                                    sb18.append(option18[i]).append(",");
                                }
                                store_from_editor.putString("option18", sb18.toString());
                            }
                            if (dataSnapshot11.getKey().equals("option19")){
                                int l=0;
                                option19_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option19_len", option19_len );
                                option19 = new String[option19_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option19[l]= dataSnapshot111.getValue().toString();
                                    l++;
                                }
                                StringBuilder sb19 = new StringBuilder();
                                for (int i = 0; i < option19_len; i++) {
                                    sb19.append(option19[i]).append(",");
                                }
                                store_from_editor.putString("option19", sb19.toString());
                            }
                            if (dataSnapshot11.getKey().equals("option20")){
                                int m=0;
                                option20_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option20_len", option20_len );
                                option20 = new String[option20_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option20[m]= dataSnapshot111.getValue().toString();
                                    m++;
                                }
                                StringBuilder sb20 = new StringBuilder();
                                for (int i = 0; i < option20_len; i++) {
                                    sb20.append(option20[i]).append(",");
                                }
                                store_from_editor.putString("option20", sb20.toString());
                            }
                            if (dataSnapshot11.getKey().equals("option21")){
                                int n=0;
                                option21_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option21_len", option21_len );
                                option21 = new String[option21_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option21[n]= dataSnapshot111.getValue().toString();
                                    Log.d("option21",""+option21[n]);
                                    n++;
                                }
                                StringBuilder sb21 = new StringBuilder();
                                for (int i = 0; i < option21_len; i++) {
                                    sb21.append(option21[i]).append(",");
                                }
                                store_from_editor.putString("option21", sb21.toString());
                            }
                            if (dataSnapshot11.getKey().equals("option22")){
                                int z=0;
                                option22_len = (int) dataSnapshot11.getChildrenCount();
                                store_from_editor.putInt("option22_len", option22_len );
                                option22 = new String[option22_len];
                                for (DataSnapshot dataSnapshot111 : dataSnapshot11.getChildren()){
                                    option22[z]= dataSnapshot111.getValue().toString();
                                    Log.d("option22",""+option22[z]);
                                    z++;
                                }
                                StringBuilder sb22 = new StringBuilder();
                                for (int i = 0; i < option22_len; i++) {
                                    sb22.append(option22[i]).append(",");
                                }
                                store_from_editor.putString("option22", sb22.toString());
                            }
                        }
                    }
                }
                store_from_editor.apply();
                Retrive_From_Shared();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void Retrive_From_Shared()
    {
        Log.d("Retrive_From_Shared()","Retrive_From_Shared");
        sharedPreferences_RetriveFromFirebase = getSharedPreferences("storeFromFirebase", Context.MODE_PRIVATE);
        shared_count_question_no = sharedPreferences_RetriveFromFirebase.getInt("count_question_no",7);
        Log.d("shared_count_questions",""+shared_count_question_no);
        for(int i=1;i<=shared_count_question_no;i++)
        {
            shared_question_text[i-1]=sharedPreferences_RetriveFromFirebase.getString("question_text"+i,"false");
            q_text[i-1].setText(shared_question_text[i-1]);
            Log.d("sharedquestion"+i," "+shared_question_text[i-1]);
        }
        String check_h11=sharedPreferences_RetriveFromFirebase.getString("option11","");
        shared_option11 = new String[option11_len];
        shared_option11 = check_h11.split(",");
        shared_option11_len = sharedPreferences_RetriveFromFirebase.getInt("option11_len",9);
        Log.d("shared_option11",""+shared_option11[1]);
        String check_h12=sharedPreferences_RetriveFromFirebase.getString("option12","");
        shared_option12 = new String[option12_len];
        shared_option12 = check_h12.split(",");
        shared_option12_len = sharedPreferences_RetriveFromFirebase.getInt("option12_len",9);
        String check_h13=sharedPreferences_RetriveFromFirebase.getString("option13","");
        shared_option13 = new String[option13_len];
        shared_option13 = check_h13.split(",");
        shared_option13_len = sharedPreferences_RetriveFromFirebase.getInt("option13_len",9);
        String check_h14=sharedPreferences_RetriveFromFirebase.getString("option14","");
        shared_option14 = new String[option14_len];
        shared_option14 = check_h14.split(",");
        shared_option14_len = sharedPreferences_RetriveFromFirebase.getInt("option14_len",9);
        String check_h15=sharedPreferences_RetriveFromFirebase.getString("option15","");
        shared_option15 = new String[option15_len];
        shared_option15 = check_h15.split(",");
        shared_option15_len = sharedPreferences_RetriveFromFirebase.getInt("option15_len",9);
        String check_h16=sharedPreferences_RetriveFromFirebase.getString("option16","");
        shared_option16 = new String[option16_len];
        shared_option16 = check_h16.split(",");
        shared_option16_len = sharedPreferences_RetriveFromFirebase.getInt("option16_len",9);
        String check_h17=sharedPreferences_RetriveFromFirebase.getString("option17","");
        shared_option17 = new String[option17_len];
        shared_option17 = check_h17.split(",");
        shared_option17_len = sharedPreferences_RetriveFromFirebase.getInt("option17_len",9);
        String check_h18=sharedPreferences_RetriveFromFirebase.getString("option18","");
        shared_option18 = new String[option18_len];
        shared_option18 = check_h18.split(",");
        shared_option18_len = sharedPreferences_RetriveFromFirebase.getInt("option18_len",9);
        String check_h19=sharedPreferences_RetriveFromFirebase.getString("option19","");
        shared_option19 = new String[option19_len];
        shared_option19 = check_h19.split(",");
        shared_option19_len = sharedPreferences_RetriveFromFirebase.getInt("option19_len",9);
        String check_h20=sharedPreferences_RetriveFromFirebase.getString("option20","");
        shared_option20 = new String[option20_len];
        shared_option20 = check_h20.split(",");
        shared_option20_len = sharedPreferences_RetriveFromFirebase.getInt("option20_len",9);
        String check_h21=sharedPreferences_RetriveFromFirebase.getString("option21","");
        shared_option21 = new String[option21_len];
        shared_option21 = check_h21.split(",");
        shared_option21_len = sharedPreferences_RetriveFromFirebase.getInt("option21_len",9);
        String check_h22=sharedPreferences_RetriveFromFirebase.getString("option22","");
        shared_option22 = new String[option22_len];
        shared_option22 = check_h22.split(",");
        shared_option22_len = sharedPreferences_RetriveFromFirebase.getInt("option22_len",9);
        SmilyRate();
    }

    public void SmilyRate()
    {
        smilyRate1();
        smilyRate2();
        smilyRate3();
        smilyRate4();
        smilyRate5();
        smilyRate6();
        smilyRate7();

    }

    public void smilyRate1()
    {

        smileRating1.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                int i=0;
                switch (smiley)
                {
                    case SmileRating.TERRIBLE:
                        flag=1;
                    case SmileRating.BAD:
                        flag=2;
                    case SmileRating.OKAY:
                        flag=3;
                        break;
                    case SmileRating.GOOD:
                        flag=4;
                    case SmileRating.GREAT:
                        flag=5;
                        break;
                }
            }
        });
    }
    public void smilyRate2()
    {
        Log.d("BAD DEBUG","0");
        smileRating2.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                i=0;
                switch (smiley)
                {
                    case SmileRating.TERRIBLE:
                        flag=1;
                    case SmileRating.OKAY:
                        flag=3;
                    case SmileRating.BAD:
                        Log.d("BAD DEBUG","1");
                        layout_q2.removeAllViews();
                        Log.d("BAD DEBUG","2 OPTION LEN"+option12_len);
                        checkBox2 = new CheckBox[shared_option12_len];
                        for(String value: shared_option12){
                            Log.d("BAD DEBUG","2.1");
                            checkBox2[i] = new CheckBox(Question_Option.this);
                            Log.d("BAD DEBUG","2.2");
                            checkBox2[i].setText(value);
                            checkBox2[i].setTextSize(20);
                            checkBox2[i].setId(i);
                            Log.d("BAD DEBUG","3");
                            layout_q2.addView(checkBox2[i]);
                            Log.d("BAD DEBUG","4");
                            i++;
                            flag=2;
                        }
                        break;
                    case SmileRating.GOOD:
                        flag=4;
                    case SmileRating.GREAT:
                        layout_q2.removeAllViews();
                        checkBox2 = new CheckBox[shared_option11_len];
                        Log.d("high check",""+ shared_option11[0]);
                        for(String value : shared_option11){
                            Log.d("high check",""+ i);
                            checkBox2[i] = new CheckBox(Question_Option.this);
                            checkBox2[i].setText(value);
                            checkBox2[i].setTextSize(20);
                            checkBox2[i].setId(i);
                            layout_q2.addView(checkBox2[i]);
                            i++;
                            flag=5;

                        }
                        break;
                    default:
                        layout_q2.removeAllViews();
                        checkBox2 = new CheckBox[shared_option11_len];
                        for(String value: option11){
                            checkBox2[i] = new CheckBox(Question_Option.this);
                            checkBox2[i].setText(value);
                            checkBox2[i].setTextSize(20);
                            checkBox2[i].setId(i);
                            layout_q2.addView(checkBox2[i]);
                            i++;
                            flag=5;
                            Log.d("high check",""+ i);
                        }
                        break;
                }
            }
        });
    }
    public void smilyRate3()
    {

        smileRating3.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                i=0;
                switch (smiley)
                {
                    case SmileRating.TERRIBLE:
                        flag=1;
                    case SmileRating.OKAY:
                        flag=3;
                    case SmileRating.BAD:
                        layout_q3.removeAllViews();
                        checkBox3 = new CheckBox[shared_option14_len];
                        //checkBox3 = new CheckBox[10];
                        for(String value: shared_option14){
                            checkBox3[i] = new CheckBox(Question_Option.this);
                            checkBox3[i].setText(value);
                            checkBox3[i].setTextSize(20);
                            checkBox3[i].setId(i);
                            layout_q3.addView(checkBox3[i]);
                            i++;
                            flag=2;
                        }
                        break;
                    case SmileRating.GOOD:
                        flag=4;
                    case SmileRating.GREAT:
                        layout_q3.removeAllViews();
                        checkBox3 = new CheckBox[shared_option13_len];
                        //checkBox3 = new CheckBox[10];
                        for(String value: shared_option13){
                            checkBox3[i] = new CheckBox(Question_Option.this);
                            checkBox3[i].setText(value);
                            checkBox3[i].setTextSize(20);
                            checkBox3[i].setId(i);
                            layout_q3.addView(checkBox3[i]);
                            i++;
                            flag=5;
                            Log.d("high check",""+ i);
                        }
                        break;
                }
            }
        });
    }
    public void smilyRate4()
    {

        smileRating4.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                i=0;
                switch (smiley)
                {
                    case SmileRating.TERRIBLE:
                        flag=1;
                    case SmileRating.OKAY:
                        flag=3;
                    case SmileRating.BAD:
                        layout_q4.removeAllViews();
                        checkBox4 = new CheckBox[shared_option16_len+1];
                        for(String value: shared_option16){
                            checkBox4[i] = new CheckBox(Question_Option.this);
                            checkBox4[i].setText(value);
                            checkBox4[i].setTextSize(20);
                            checkBox4[i].setId(i);
                            layout_q4.addView(checkBox4[i]);
                            i++;
                            flag=2;
                        }
                        break;
                    case SmileRating.GOOD:
                        flag=4;
                    case SmileRating.GREAT:
                        layout_q4.removeAllViews();
                        checkBox4 = new CheckBox[shared_option15_len+1];
                        for(String value: shared_option15){
                            checkBox4[i] = new CheckBox(Question_Option.this);
                            checkBox4[i].setText(value);
                            checkBox4[i].setTextSize(20);
                            checkBox4[i].setId(i);
                            layout_q4.addView(checkBox4[i]);
                            i++;
                            flag=5;
                            Log.d("high check",""+ i);
                        }
                        break;
                }
            }
        });
    }
    public void smilyRate5()
    {

        smileRating5.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                i=0;
                switch (smiley)
                {
                    case SmileRating.TERRIBLE:
                        flag=1;
                    case SmileRating.OKAY:
                        flag=3;
                    case SmileRating.BAD:
                        layout_q5.removeAllViews();
                        checkBox5 = new CheckBox[shared_option18_len];
                        for(String value: shared_option18){
                            checkBox5[i] = new CheckBox(Question_Option.this);
                            checkBox5[i].setText(value);
                            checkBox5[i].setTextSize(20);
                            checkBox5[i].setId(i);
                            layout_q5.addView(checkBox5[i]);
                            i++;
                            flag=2;
                        }
                        break;
                    case SmileRating.GOOD:
                        flag=4;
                    case SmileRating.GREAT:
                        layout_q5.removeAllViews();
                        checkBox5 = new CheckBox[shared_option17_len];
                        for(String value: shared_option17){
                            checkBox5[i] = new CheckBox(Question_Option.this);
                            checkBox5[i].setText(value);
                            checkBox5[i].setTextSize(20);
                            checkBox5[i].setId(i);
                            layout_q5.addView(checkBox5[i]);
                            i++;
                            flag=5;
                            Log.d("high check",""+ i);
                        }
                        break;
                }
            }
        });
    }
    public void smilyRate6()
    {

        smileRating6.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                int i=0;
                switch (smiley)
                {
                    case SmileRating.TERRIBLE:
                        flag=1;
                    case SmileRating.OKAY:
                        flag=3;
                    case SmileRating.BAD:
                        layout_q6.removeAllViews();
                        checkBox6 = new CheckBox[shared_option20_len];
                        for(String value: shared_option20){
                            checkBox6[i] = new CheckBox(Question_Option.this);
                            checkBox6[i].setText(value);
                            checkBox6[i].setTextSize(20);
                            checkBox6[i].setId(i);
                            layout_q6.addView(checkBox6[i]);
                            i++;
                            flag=2;
                        }
                        break;
                    case SmileRating.GOOD:
                        flag=4;
                    case SmileRating.GREAT:
                        layout_q6.removeAllViews();
                        checkBox6 = new CheckBox[shared_option19_len];
                        for(String value: shared_option19){
                            checkBox6[i] = new CheckBox(Question_Option.this);
                            checkBox6[i].setText(value);
                            checkBox6[i].setTextSize(20);
                            checkBox6[i].setId(i);
                            layout_q6.addView(checkBox6[i]);
                            i++;
                            flag=5;
                            Log.d("high check",""+ i);
                        }
                        break;
                }
            }
        });
    }
    public void smilyRate7()
    {

        smileRating7.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                int q=0;
                switch (smiley)
                {
                    case SmileRating.TERRIBLE:
                        flag=1;
                    case SmileRating.OKAY:
                        flag=3;
                    case SmileRating.BAD:
                        layout_q7.removeAllViews();
                        Log.d("option22_len",""+ option22_len);
                        checkBox7 = new CheckBox[shared_option22_len];
                        //checkBox7 = new CheckBox[10];
                        for(String value: shared_option22){
                            checkBox7[q] = new CheckBox(Question_Option.this);
                            checkBox7[q].setText(value);
                            checkBox7[q].setId(q);
                            checkBox7[q].setTextSize(20);
                            layout_q7.addView(checkBox7[q]);
                            Log.d("option21",""+value);
                            q++;
                            flag=2;
                        }
                        break;
                    case SmileRating.GOOD:
                        flag=4;
                    case SmileRating.GREAT:
                        layout_q7.removeAllViews();
                        Log.d("option21_len",""+ option21_len);
                        checkBox7 = new CheckBox[shared_option21_len];
                        //checkBox7 = new CheckBox[10];
                        for(String value: shared_option21){
                            checkBox7[q] = new CheckBox(Question_Option.this);
                            checkBox7[q].setText(value);
                            checkBox7[q].setId(q);
                            checkBox7[q].setTextSize(20);
                            layout_q7.addView(checkBox7[q]);
                            Log.d("option21",""+value);
                            q++;
                            flag=5;
                            Log.d("high check",""+ i);
                        }
                        break;
                }
            }
        });
    }
}