package com.example.kimfamily.esp8266_arduino_test1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.kimfamily.esp8266_arduino_test1.Shared_preference_for_saving_array_class;
import com.example.kimfamily.esp8266_arduino_test1.MacAdress_get;
import com.example.kimfamily.esp8266_arduino_test1.HttpRequestAsyncTask;


public class MainActivity extends Activity implements View.OnClickListener {

    public final static String PREF_IP = "PREF_IP_ADDRESS";
    public final static String PREF_PORT = "PREF_PORT_NUMBER";
    // declare buttons and text inputs
    private Button buttonPin11,buttonPin12,buttonPin13;
    private EditText editTextIPAddress, editTextPortNumber;
    private EditText writing_data;
    private TextView mac_address;
    // shared preferences objects used to save the IP address and port so that the user doesn't have to
    // type them next time he/she opens the app.
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;


    /************동적 버튼 생성 *************/
    private Button button_create;
    private Button button_hold;
    private Button test_btn;
    private ViewGroup mainLayout;
    private int creating_button_number = 10;

    Shared_preference_for_saving_array_class shared_preference_for_saving_array_class;
    MacAdress_get macAdress_get;

    private String mac_adress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("HTTP_HELPER_PREFS",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // assign buttons
        buttonPin11 = (Button)findViewById(R.id.buttonPin11);
        buttonPin12 = (Button)findViewById(R.id.buttonPin12);
        buttonPin13 = (Button)findViewById(R.id.buttonPin13);

        // assign text inputs
        editTextIPAddress = (EditText)findViewById(R.id.editTextIPAddress);
        editTextPortNumber = (EditText)findViewById(R.id.editTextPortNumber);
        mac_address = (TextView) findViewById(R.id.mac_address);

        // set button listener (this class)
        buttonPin11.setOnClickListener(this);
        buttonPin12.setOnClickListener(this);
        buttonPin13.setOnClickListener(this);

        // get the IP address and port number from the last time the user used the app,
        // put an empty string "" is this is the first time.
        editTextIPAddress.setText(sharedPreferences.getString(PREF_IP,""));
        editTextPortNumber.setText(sharedPreferences.getString(PREF_PORT,""));

        //mac어드레스 가져오기
        macAdress_get = new MacAdress_get();
        mac_adress = "";
        mac_adress = macAdress_get.getMACAddress(this, "wlan0");
//        Toast.makeText(this, mac_adress + " : " + mac_adress.length(),Toast.LENGTH_LONG).show();



        /***************동적 버튼 생성**************/
        button_create = (Button) findViewById(R.id.button_create);
        button_hold = (Button) findViewById(R.id.button_hold);
        test_btn = (Button) findViewById(R.id.test_btn);
        button_create.setOnClickListener(operation_buttons);
        button_hold.setOnClickListener(operation_buttons);
        test_btn.setOnClickListener(this);

        writing_data = (EditText) findViewById(R.id.writing_data);

        mainLayout = (RelativeLayout) findViewById(R.id.main);






    }


    @Override
    public void onClick(View view) {

        // get the pin number
        String parameterValue = "";
        // get the ip address
        String ipAddress = editTextIPAddress.getText().toString().trim();
        // get the port number
        String portNumber = editTextPortNumber.getText().toString().trim();


        // save the IP address and port for the next time the app is used
        editor.putString(PREF_IP,ipAddress); // set the ip address value to save
        editor.putString(PREF_PORT,portNumber); // set the port number to save
        editor.commit(); // save the IP and PORT



        //데이터 셋 만들기
        StringBuilder data_build = new StringBuilder();




        // get the pin number from the button that was clicked
        if(view.getId()==buttonPin11.getId())
        {
            parameterValue = "11";
        }
        else if(view.getId()==buttonPin12.getId())
        {
            parameterValue = "12";
        }
        else if(view.getId()==buttonPin13.getId())
        {
            parameterValue = "13";
        }
        else if(view.getId()==test_btn.getId()) {

            data_build.append("="); //start byte
            data_build.append(mac_adress);
            data_build.append("001");
            data_build.append(writing_data.getText());
            data_build.append("?");//end data

//            Toast.makeText(this,  + "", Toast.LENGTH_LONG).show();
        }


        // execute HTTP request
        if(ipAddress.length()>0 && portNumber.length()>0) {
//            new HttpRequestAsyncTask(view.getContext(), parameterValue, ipAddress, portNumber, "pin").execute();
            new HttpRequestAsyncTask(view.getContext(), data_build.toString(), ipAddress, portNumber, "pin").execute();

        }




    } //on click listener end





    /**동적 버튼 생성용 버튼 리스트 클릭 리스너 **/
    View.OnClickListener operation_buttons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /*************동적 버튼 생성 ****************/
            if(v.getId()==button_create.getId())
            {

                LinearLayout new_linear = new LinearLayout(getApplicationContext());
                new_linear.setId(creating_button_number + 0);
//            new_frames.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_basic_boundary));

                Button new_buttons = new Button(getApplicationContext());
                new_buttons.setId(creating_button_number + 1);
                new_buttons.setText("button" + creating_button_number/10);
                new_buttons.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                new_buttons.setOnClickListener(new_creation_buttons);

                TextView new_texts = new TextView(getApplicationContext());
                new_texts.setId(creating_button_number + 2);
                new_texts.setText("이동 손잡이");

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER;
                new_linear.setOrientation(LinearLayout.VERTICAL);
                new_linear.setLayoutParams(params);


//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(new_buttons.getWidth(), new_buttons.getHeight());
//            new_frames.setLayoutParams(params);


                String[] new_buttons_location = new String[2];
                new_buttons_location[0] = "new_button_x" + creating_button_number;
                new_buttons_location[1] = "new_button_y" + creating_button_number;
//            String new_button_scale = "new_button_scale" + creating_button_number;
                Movable_Layout_Class new_movable_button =  new Movable_Layout_Class(getApplicationContext(), mainLayout, new_linear, new_buttons_location);

                creating_button_number += 10; //10단위로 id 셋 구분함

                new_linear.addView(new_buttons);
                new_linear.addView(new_texts);
                mainLayout.addView(new_linear);

            }

            /*******************동적 생성된 버튼 홀드*****************/
            if(v.getId()==button_hold.getId()) {
                for(int i = 10; i<creating_button_number; i += 10){
                    try {
                        TextView new_texts = (TextView) findViewById(i + 2);
                        new_texts.setVisibility(View.INVISIBLE);
                    }catch (Exception e){

                    }
                }

            }

        }
    };


/** 새로 만들어진 버튼들 리스너 **/
    View.OnClickListener new_creation_buttons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /*********** 동적 생성된 버튼 intent로 화면 넘기기 **************/
            try {
                for(int i = 10; i<creating_button_number; i += 10) {
                    if (v.getId() == i + 1) {
                        Toast.makeText(getApplicationContext(), "버튼" + creating_button_number/10, Toast.LENGTH_SHORT).show();
                        //알고리즘 세팅 엑티비티로 넘어감
                        Intent intent = new Intent(MainActivity.this, Algorithm_dev_activity.class);
                        startActivity(intent);
                    }
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
            }


        }
    };





}

