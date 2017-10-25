package com.example.kimfamily.esp8266_arduino_test1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by KimFamily on 2017-10-25.
 */


public class Algorithm_dev_activity extends Activity implements View.OnClickListener {

    private Button motor_stop;
    Shared_preference_for_saving_array_class shared_preference_for_saving_array_class;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.algorithm_dev_layout);

        motor_stop = (Button) findViewById(R.id.motor_stop);
        motor_stop.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==motor_stop.getId())
        {
            ArrayList a = new ArrayList();
            a.add(1);
            a.add(2);
            shared_preference_for_saving_array_class.setStringArrayPref(this, "test1", a);

            ArrayList b = new ArrayList();
            b = shared_preference_for_saving_array_class.getStringArrayPref(this, "test1");
            Toast.makeText(this, b + "", Toast.LENGTH_LONG).show();
        }

    }
}
