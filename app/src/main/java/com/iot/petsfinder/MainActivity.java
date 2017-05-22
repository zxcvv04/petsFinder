package com.iot.petsfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView textView01;
    TextView textView02;

    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView01 = (TextView) findViewById(R.id.userMode);
        textView02 = (TextView) findViewById(R.id.userId);

        Intent intent=getIntent();
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            Parcelables parcel = (Parcelables) bundle.getParcelable("parcel");

            str=parcel.getLoginId();

            if(str.equals("admin")){
                textView01.setText("관리자 모드");
                textView02.setText(parcel.getLoginId()+"님 반갑습니다.");
            }

        }
    }
}