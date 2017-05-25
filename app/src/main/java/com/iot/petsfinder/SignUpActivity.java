package com.iot.petsfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_MAIL = "mail";
    private static final String TAG_PW = "pw";
    private static final String DB_URL_LOGIN = "http://122.44.13.91:11057/getdata.php";
    private static final String DB_URL_SIGNUP = "http://122.44.13.91:11057/signup.php";

    JSONArray peoples = null;

    EditText editTextUserName, editTextPassword, editTextConfirmPassword;
    Button btnCreateAccount;

    String userName, password, confirmPassword;
    String dbaccountMail, dbacccountPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        editTextUserName = (EditText) findViewById(R.id.loginIdInput);
        editTextPassword = (EditText) findViewById(R.id.loginPasswordInput);
        editTextConfirmPassword = (EditText) findViewById(R.id.loginConfirmPasswordInput);

        getData(DB_URL_LOGIN);

        btnCreateAccount = (Button) findViewById(R.id.button);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                userName = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();
                confirmPassword = editTextConfirmPassword.getText().toString();

                try {
                    JSONObject jsonObj = new JSONObject(myJSON);
                    peoples = jsonObj.getJSONArray(TAG_RESULTS);

                    boolean isAuth = false;

                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject c = peoples.getJSONObject(i);
                        dbaccountMail = c.getString(TAG_MAIL);
                        dbacccountPw = c.getString(TAG_PW);

                        if (userName.equals(dbaccountMail)
                                && password.equals(dbacccountPw)
                                && confirmPassword.equals(password))
                            isAuth = true;
                    }

                    if (isAuth) {
                        Toast.makeText(getApplicationContext(),
                                "동일한 계정이 있습니다.",
                                Toast.LENGTH_LONG).show();

                    } else if (userName.equals("")
                            || password.equals("")
                            || confirmPassword.equals(""))
                        Toast.makeText(getApplicationContext(),
                                "값을 입력해 주세요..",
                                Toast.LENGTH_LONG).show();

                    else if ((!Objects.equals(userName, ""))
                            && (!Objects.equals(password, ""))
                            && confirmPassword.equals(password)) {
                        insertToDatabase(userName, password);
                        Toast.makeText(getApplicationContext(),
                                "계정이 만들어졌습니다.",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder stringBuilder = new StringBuilder();

                    bufferedReader = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json + "\n");
                    }

                    return stringBuilder.toString().trim();

                } catch (Exception e) {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }


    private void insertToDatabase(String _mail, String _pw) {

        class InsertData extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(SignUpActivity.this,
                        "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String mail = params[0];
                    String pw = params[1];

                    String data = URLEncoder.encode("mail", "UTF-8") + "="
                            + URLEncoder.encode(mail, "UTF-8");
                    data += "&" + URLEncoder.encode("pw", "UTF-8") + "="
                            + URLEncoder.encode(pw, "UTF-8");

                    URL url = new URL(DB_URL_SIGNUP);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return "Exception: " + e.getMessage();
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(_mail, _pw);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TestAppActivity", "onRestart");
    }
}