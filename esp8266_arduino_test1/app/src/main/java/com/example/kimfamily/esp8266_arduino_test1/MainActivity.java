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
    private Button button_creat;
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
        Toast.makeText(this, mac_adress,Toast.LENGTH_LONG).show();



        /***************동적 버튼 생성**************/
        button_creat = (Button) findViewById(R.id.button_creat);
        button_hold = (Button) findViewById(R.id.button_hold);
        test_btn = (Button) findViewById(R.id.test_btn);
        button_creat.setOnClickListener(this);
        button_hold.setOnClickListener(this);
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

            parameterValue = writing_data.getText() + "";
//            Toast.makeText(this,  + "", Toast.LENGTH_LONG).show();
        }


        // execute HTTP request
        if(ipAddress.length()>0 && portNumber.length()>0) {
            new HttpRequestAsyncTask(
                    view.getContext(), parameterValue, ipAddress, portNumber, "pin"
            ).execute();
        }


        /*************동적 버튼 생성 ****************/
        if(view.getId()==button_creat.getId())
        {

            LinearLayout new_linear = new LinearLayout(this);
            new_linear.setId(creating_button_number + 0);
//            new_frames.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_basic_boundary));

            Button new_buttons = new Button(this);
            new_buttons.setId(creating_button_number + 1);
            new_buttons.setText("button" + creating_button_number/10);
            new_buttons.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            new_buttons.setOnClickListener(this);

            TextView new_texts = new TextView(this);
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
            Movable_Layout_Class new_movable_button =  new Movable_Layout_Class(this, mainLayout, new_linear, new_buttons_location);

            creating_button_number += 10; //10단위로 id 셋 구분함

            new_linear.addView(new_buttons);
            new_linear.addView(new_texts);
            mainLayout.addView(new_linear);

        }

        /*******************동적 생성된 버튼 홀드*****************/
        if(view.getId()==button_hold.getId()) {
            for(int i = 10; i<creating_button_number; i += 10){
                try {
                    TextView new_texts = (TextView) findViewById(i + 2);
                    new_texts.setVisibility(View.INVISIBLE);
                }catch (Exception e){

                }
            }

        }


        /*********** 동적 생성된 버튼 intent로 화면 넘기기 **************/
        try {
            for(int i = 10; i<creating_button_number; i += 10) {
                if (view.getId() == i + 1) {
                    Toast.makeText(this, "버튼" + creating_button_number/10, Toast.LENGTH_SHORT).show();
                    //알고리즘 세팅 엑티비티로 넘어감
                    Intent intent = new Intent(MainActivity.this, Algorithm_dev_activity.class);
                    startActivity(intent);
                }
            }
        }catch (Exception e){
            Toast.makeText(this, e + "", Toast.LENGTH_LONG).show();
        }




    } //on click listener end

    /**
     * Description: Send an HTTP Get request to a specified ip address and port.
     * Also send a parameter "parameterName" with the value of "parameterValue".
     * @param parameterValue the pin number to toggle
     * @param ipAddress the ip address to send the request to
     * @param portNumber the port number of the ip address
     * @param parameterName
     * @return The ip address' reply text, or an ERROR message is it fails to receive one
     */
    public String sendRequest(String parameterValue, String ipAddress, String portNumber, String parameterName) {
        String serverResponse = "ERROR";

        try {

            HttpClient httpclient = new DefaultHttpClient(); // create an HTTP client
            // define the URL e.g. http://myIpaddress:myport/?pin=13 (to toggle pin 13 for example)
            URI website = new URI("http://"+ipAddress+":"+portNumber+"/?"+parameterName+"="+parameterValue);
            HttpGet getRequest = new HttpGet(); // create an HTTP GET object
            getRequest.setURI(website); // set the URL of the GET request
            HttpResponse response = httpclient.execute(getRequest); // execute the request
            // get the ip address server's reply
            InputStream content = null;
            content = response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    content
            ));
            serverResponse = in.readLine();
            // Close the connection
            content.close();
        } catch (ClientProtocolException e) {
            // HTTP error
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            // IO error
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // URL syntax error
            serverResponse = e.getMessage();
            e.printStackTrace();
        }
        // return the server's reply/response text
        return serverResponse;
    }


    /**
     * An AsyncTask is needed to execute HTTP requests in the background so that they do not
     * block the user interface.
     */
    private class HttpRequestAsyncTask extends AsyncTask<Void, Void, Void> {

        // declare variables needed
        private String requestReply,ipAddress, portNumber;
        private Context context;
        private AlertDialog alertDialog;
        private String parameter;
        private String parameterValue;

        /**
         * Description: The asyncTask class constructor. Assigns the values used in its other methods.
         * @param context the application context, needed to create the dialog
         * @param parameterValue the pin number to toggle
         * @param ipAddress the ip address to send the request to
         * @param portNumber the port number of the ip address
         */
        public HttpRequestAsyncTask(Context context, String parameterValue, String ipAddress, String portNumber, String parameter)
        {
            this.context = context;

            alertDialog = new AlertDialog.Builder(this.context)
                    .setTitle("HTTP Response From IP Address:")
                    .setCancelable(true)
                    .create();

            this.ipAddress = ipAddress;
            this.parameterValue = parameterValue;
            this.portNumber = portNumber;
            this.parameter = parameter;
        }

        /**
         * Name: doInBackground
         * Description: Sends the request to the ip address
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {
            alertDialog.setMessage("Data sent, waiting for reply from server...");
            if(!alertDialog.isShowing())
            {
                alertDialog.show();
            }
            requestReply = sendRequest(parameterValue,ipAddress,portNumber, parameter);
            return null;
        }

        /**
         * Name: onPostExecute
         * Description: This function is executed after the HTTP request returns from the ip address.
         * The function sets the dialog's message with the reply text from the server and display the dialog
         * if it's not displayed already (in case it was closed by accident);
         * @param aVoid void parameter
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            alertDialog.setMessage(requestReply);
            if(!alertDialog.isShowing())
            {
                alertDialog.show(); // show dialog
            }
        }

        /**
         * Name: onPreExecute
         * Description: This function is executed before the HTTP request is sent to ip address.
         * The function will set the dialog's message and display the dialog.
         */
        @Override
        protected void onPreExecute() {
            alertDialog.setMessage("Sending data to server, please wait...");
            if(!alertDialog.isShowing())
            {
                alertDialog.show();
            }
        }

    }
}

