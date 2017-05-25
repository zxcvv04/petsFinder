package com.iot.petsfinder;

import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity
{
    private static final int ACTIVITY_MAIN = 1004;
    private static final int ACTIVITY_JOIN = 1005;

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_MAIL = "mail";
    private static final String TAG_PW = "pw";

    JSONArray peoples = null;

    /*LoginDataBaseAdapter loginDataBaseAdapter;*/

    //    public static final String LoginId = "admin";
//    public static final String LoginPw = "admin";
    private static final String TAG = "===";

//    private DatabaseHelper dbHelper;
//    private SQLiteDatabase db;

    ImageView imageView01;
    ImageView imageView02;
    ImageView imageView03;

    EditText userNameInput;
    EditText passwordInput;

    String SelectId = "";
    int count = 0;
    int join_counter = 0;

    String dbaccountMail, dbacccountPw;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        onRestart();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView01 = (ImageView) findViewById(R.id.imageView1);
        imageView02 = (ImageView) findViewById(R.id.imageView2);
        imageView03 = (ImageView) findViewById(R.id.imageView3);

        userNameInput = (EditText) findViewById(R.id.loginIdInput);
        passwordInput = (EditText) findViewById(R.id.loginPasswordInput);

        imageRotation();

        getData("http://122.44.13.91:11057/getdata.php");
        /*loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();*/
    }

    protected void imageRotation()
    {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                handler.postDelayed(this, 2000);

                count++;
                if ((count % 3) == 1)
                {
                    imageView03.setVisibility(View.GONE);
                    imageView02.setVisibility(View.VISIBLE);
                    imageView01.setVisibility(View.GONE);
                } else if ((count % 3) == 2)
                {
                    imageView03.setVisibility(View.VISIBLE);
                    imageView02.setVisibility(View.GONE);
                    imageView01.setVisibility(View.GONE);
                } else if ((count % 3) == 0)
                {
                    imageView03.setVisibility(View.GONE);
                    imageView02.setVisibility(View.GONE);
                    imageView01.setVisibility(View.VISIBLE);
                }
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        userNameInput.setText("");
        passwordInput.setText("");
    }

    protected void btnLogin(View v)
    {

        String userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();

        // fetch the Password form database for respective user name
        /*String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);*/
        try
        {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            boolean isAuth = false;


            for (int i = 0; i < peoples.length(); i++)
            {
                JSONObject c = peoples.getJSONObject(i);
                dbaccountMail = c.getString(TAG_MAIL);
                dbacccountPw = c.getString(TAG_PW);

                if (userName.equals(dbaccountMail)
                        && password.equals(dbacccountPw)) isAuth = true;
            }

            if ( isAuth ) {
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

                Toast.makeText(getApplicationContext(), "로그인.", Toast.LENGTH_LONG).show();

            } else if (userName.equals("") || password.equals(""))
                Toast.makeText(getApplicationContext(), "값을 입력해 주세요..", Toast.LENGTH_LONG).show();
            else Toast.makeText(getApplicationContext(), "계정 또는 비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();

        } catch (JSONException e)
        {
            e.printStackTrace();
        }


    }

    protected void btnJoin(View v)
    {
        Intent intent = new Intent(
                getApplicationContext(),
                SignUpActivity.class
        );

        startActivityForResult(
                intent,
                ACTIVITY_JOIN
        );
    }

    public void getData(String url)
    {
        class GetDataJSON extends AsyncTask<String, Void, String>
        {

            @Override
            protected String doInBackground(String... params)
            {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try
                {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null)
                    {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e)
                {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result)
            {
                myJSON = result;
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TestAppActivity", "onRestart");
    }

}