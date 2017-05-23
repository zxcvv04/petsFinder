package com.iot.petsfinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    private static final int ACTIVITY_MAIN=1004;
    private static final int ACTIVITY_JOIN=1005;

    //    public static final String LoginId = "admin";
//    public static final String LoginPw = "admin";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "petsfinder";
    private static final String TABLE_NAME = "login";
    private static final String TAG = "===";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    ImageView imageView01;
    ImageView imageView02;
    ImageView imageView03;

    EditText userNameInput;
    EditText passwordInput;

    String SelectId="";
    int count=0;
    int join_counter=0;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        userNameInput.setText("");
        passwordInput.setText("");
    }

    protected void btnLogin(View v){

        String userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(!getDB()) Log.e(TAG, "fail to connect to db");

//        if(LoginId.equals(userName)&&LoginPw.equals(password))
        if (getAuth(userName, password)) //login success
        {
//            SelectId=LoginId;

            Intent intent = new Intent(
                    getApplicationContext(),
                    MainActivity.class
            );

            ComponentName name = new ComponentName(
                    "com.iot.petsfinder",
                    "com.iot.petsfinder.MainActivity"
            );

            intent.setComponent(name);
            Parcelables parcel = new Parcelables(SelectId);
            intent.putExtra("parcel", parcel);
            startActivityForResult(intent, ACTIVITY_MAIN);
        }
        else { ///fail to login
            Toast.makeText(
                    getApplicationContext(),
                    "로그인에 실패했습니다.",
                    Toast.LENGTH_LONG).show();
        }
    }

    protected void btnJoin(View v){
        Intent intent=new Intent(
                getApplicationContext(),
                JoinActivity.class
        );

        startActivityForResult(
                intent,
                ACTIVITY_JOIN
        );
    }

    /////db helper
    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                String DROP_SQL = "drop table if exists " + TABLE_NAME;
                db.execSQL(DROP_SQL);
            } catch (Exception e) { ErrLogger(e); }

            String CREATE_SQL = "create table " + TABLE_NAME + " (" +
                    " mail text PRIMARY KEY, " +
                    " pw text )";

            try { db.execSQL(CREATE_SQL); } catch (Exception e) { ErrLogger(e); }

        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "upgrading database version from " + oldVersion + " to " + newVersion);
        }

        void ErrLogger(Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

    }

    ///// launch db
    private boolean getDB() {
        Log.w(TAG, "open db");
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        return true;
    }

    ///// query id, pw
    private boolean getAuth(String userName, String password){
        if (userName == null || password == null) return false;
        Cursor _cursorQryResult = db.rawQuery("select * from " + TABLE_NAME +
        " where id = " + userName +
        " and pw = " + password, null);

        if (_cursorQryResult.getCount() == 1) {
            _cursorQryResult.close();
            return true;
        }
        else { _cursorQryResult.close(); return false; }
    }

}