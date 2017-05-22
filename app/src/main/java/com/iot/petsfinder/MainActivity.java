package com.iot.petsfinder;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
    ImageView imageView01;
    ImageView imageView02;
    ImageView imageView03;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView01=(ImageView) findViewById(R.id.imageView1);
        imageView02=(ImageView) findViewById(R.id.imageView2);
        imageView03=(ImageView) findViewById(R.id.imageView3);

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
}
