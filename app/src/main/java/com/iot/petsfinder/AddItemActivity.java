package com.iot.petsfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class AddItemActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        LinearLayout layoutDoggyIcons = (LinearLayout) findViewById(R.id.layoutdoggieIconList);
//
//        Drawable.class.
//        String getIconNames (LinearLayout layout) {
//            for (int i = 0; i < layoutDoggyIcons.getChildCount(); i++) {
//                {
//                    return layoutDoggyIcons.getChildAt(i).get.getTag();
//                }
//            }
//        }

    }

    //region doggie spinner lol
    private TextView txtVSelectedDoggyFamily =
            (TextView) findViewById(R.id.txtVChosedDoggyFamily);

    protected void btnDoggyJindoClicked (View v) {
        txtVSelectedDoggyFamily.setText("진돗개");
    }
    protected void btnDoggyborder_collieClicked (View v) {
        txtVSelectedDoggyFamily.setText("보더 콜리");
    }
    protected void btnDoggyboxerClicked (View v) {
        txtVSelectedDoggyFamily.setText("복서");
    }
    protected void btnDoggybull_terrierClicked (View v) {
        txtVSelectedDoggyFamily.setText("불 테리어");
    }
    protected void btnDoggybulldogClicked (View v) {
        txtVSelectedDoggyFamily.setText("불독");
    }
    protected void btnDoggychihuahuaClicked (View v) {
        txtVSelectedDoggyFamily.setText("치와와");
    }
    protected void btnDoggychow_chowClicked (View v) {
        txtVSelectedDoggyFamily.setText("차우차우");
    }
    protected void btnDoggydachshundClicked (View v) {
        txtVSelectedDoggyFamily.setText("닥스훈트");
    }
    protected void btnDoggydobermanClicked (View v) {
        txtVSelectedDoggyFamily.setText("도베르만");
    }
    //region

}
