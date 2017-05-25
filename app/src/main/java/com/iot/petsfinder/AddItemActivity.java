package com.iot.petsfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {


    TextView txtVSelectedDoggyFamily;
    EditText edtxtDogAge, edtxtDogSex, edtxtContactInfo, edtxtDetails;
    Button btnAttachLostList, btnAttachFoundList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        edtxtDogAge = (EditText) findViewById(R.id.edtxtDogAge);
        edtxtDogSex = (EditText) findViewById(R.id.edtxtDogSex);
        edtxtContactInfo = (EditText) findViewById(R.id.edtxtContactInfo);
        edtxtDetails = (EditText) findViewById(R.id.edtxtDetails);

        /////////TODO : trig from this buttons to send data to db
        btnAttachFoundList = (Button) findViewById(R.id.btnAttachFoundList);
        btnAttachLostList = (Button) findViewById(R.id.btnAttachLostList);
        txtVSelectedDoggyFamily =
                (TextView) findViewById(R.id.txtVChosedDoggyFamily);

    }

    //region doggie spinner lol

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


    ////입력된 값 String으로 받음 ( 사진제외 )
    public List<String> getInputedStrings () {

        List<String> _tmp = Arrays.asList(
                ////품종, 나이, 성별, 연락처, 상세정보
                txtVSelectedDoggyFamily.getText().toString(),
                edtxtDogAge.getText().toString(),
                edtxtDogSex.getText().toString(),
                edtxtContactInfo.getText().toString(),
                edtxtDetails.getText().toString()
        );
        return _tmp.isEmpty() ? _tmp : null;
    }

}
