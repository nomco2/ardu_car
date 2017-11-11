package com.example.kimfamily.esp8266_arduino_test1;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import com.example.kimfamily.esp8266_arduino_test1.Movable_Layout_Class;
/**
 * Created by KimFamily on 2017-10-25.
 */




public class Algorithm_dev_activity extends Activity implements View.OnClickListener {

    private ImageButton motor1_speed_btn, motor2_speed_btn, motor12_stop_btn, servo_motor_angle_btn, distance_value, delay_btn;
    Shared_preference_for_saving_array_class shared_preference_for_saving_array_class;

    private RelativeLayout dev_layout_main;
    private int creating_button_id_number = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.algorithm_dev_layout);

        dev_layout_main = (RelativeLayout) findViewById(R.id.dev_layout_main);

        for(int i = 1; i < 7; i++){
            button_creating_method(1, 10, 10);
        }





    }

    @Override
    public void onClick(View view) {

    }

    View.OnClickListener user_listing_buttons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {




        }
    };


    View.OnClickListener new_creation_buttons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    private void button_creating_method(int id_numbers, int location_x, int location_y){

        int this_layout_id_number = creating_button_id_number + id_numbers;


        LinearLayout new_linear = new LinearLayout(getApplicationContext());
        //        new_linear.setId(creating_button_number + 10);
//            new_frames.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_basic_boundary));

        ImageView new_buttons = new ImageView(getApplicationContext());
        new_buttons.setId(this_layout_id_number);
        select_background_img(new_buttons, this_layout_id_number); //백그라운드 레이아웃 id 번호에 따라서 알아서 배치
        new_buttons.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            new_buttons.setOnClickListener(new_creation_buttons);
        new_buttons.setScaleX(0.5f);
        new_buttons.setScaleY(0.5f);

//            TextView new_texts = new TextView(getApplicationContext());
//            new_texts.setId(creating_button_number + 2);
//            new_texts.setText("이동 손잡이");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        new_linear.setOrientation(LinearLayout.VERTICAL);
        new_linear.setLayoutParams(params);


//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(new_buttons.getWidth(), new_buttons.getHeight());
//            new_frames.setLayoutParams(params);


        String[] new_buttons_location = new String[2];
        new_buttons_location[0] = "new_button_x" + this_layout_id_number;
        new_buttons_location[1] = "new_button_y" + this_layout_id_number;
//            String new_button_scale = "new_button_scale" + creating_button_number;
        Movable_Layout_Class new_movable_button =  new Movable_Layout_Class(getApplicationContext(), dev_layout_main, new_linear, new_buttons_location);


        new_linear.addView(new_buttons);
//            new_linear.addView(new_texts);
        dev_layout_main.addView(new_linear);
        new_linear.setX(location_x);
        new_linear.setY(location_y);
    }






    /**버튼 자동으로 생성시 백그라운드를 쉽게 설정하기 위한 함수
     *
     * @param id_number
     * @return
     */
    private void select_background_img(ImageView imageView, int id_number){


        switch (id_number%100){
            case 1 :
                imageView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.motor1_speed_btn));
                break;
            case 2 :
                imageView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.motor2_speed_btn));
                break;
            case 3 :
                imageView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.motor12_stop_btn));
                break;
            case 4 :
                imageView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.servo_motor_angle_btn));
                break;
            case 5 :
                imageView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.distance_value));
                break;
            case 6 :
                imageView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.delay_btn));
                break;
            case 7 :
//                imageView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.motor1_speed_btn));
                break;
        }

    }




}
