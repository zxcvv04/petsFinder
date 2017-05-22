package com.iot.petsfinder;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity
{
    private static final int ACTIVITY_MAIN=1004;

    public static final String LoginId = "admin";
    public static final String LoginPw = "admin";

    ImageView imageView01;
    ImageView imageView02;
    ImageView imageView03;

    EditText userNameInput;
    EditText passwordInput;

    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView01=(ImageView) findViewById(R.id.imageView1);
        imageView02=(ImageView) findViewById(R.id.imageView2);
        imageView03=(ImageView) findViewById(R.id.imageView3);

        userNameInput = (EditText) findViewById(R.id.loginIdInput);
        passwordInput = (EditText) findViewById(R.id.loginPasswordInput);

        imageRotation();
    }

    protected void imageRotation(){
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 2000);

                count++;
                if ((count % 3) == 1) {
                    imageView03.setVisibility(View.GONE);
                    imageView02.setVisibility(View.VISIBLE);
                    imageView01.setVisibility(View.GONE);
                } else if ((count % 3) == 2) {
                    imageView03.setVisibility(View.VISIBLE);
                    imageView02.setVisibility(View.GONE);
                    imageView01.setVisibility(View.GONE);
                } else if ((count % 3) == 0) {
                    imageView03.setVisibility(View.GONE);
                    imageView02.setVisibility(View.GONE);
                    imageView01.setVisibility(View.VISIBLE);
                }
            }
        }, 2000);
    }

    public void btnLogin(View v){
        String userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(LoginId.equals(userName)&&LoginPw.equals(password))
        {
            Intent intent = new Intent(
                    getApplicationContext(),
                    MainActivity.class
            );

            ComponentName name = new ComponentName(
                    "com.iot.petsfinder",
                    "com.iot.petsfinder.MainActivity"
            );

            intent.setComponent(name);
            Parcelables parcel = new Parcelables(LoginId);
            intent.putExtra("parcel", parcel);
            startActivityForResult(intent, ACTIVITY_MAIN);
        }
    }
}
