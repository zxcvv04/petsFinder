package com.iot.petsfinder;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity
{

    String myJSON;

    ListView list;

    private static final String TAG_RESULTS="result";
    private static final String TAG_MAIL = "mail";
    private static final String TAG_PW ="pw";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    EditText editTextUserName,editTextPassword,editTextConfirmPassword;
    Button btnCreateAccount;

    String userName, password, confirmPassword;

    String dbaccountMail, dbacccountPw;

    /*LoginDataBaseAdapter loginDataBaseAdapter;*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        personList = new ArrayList<HashMap<String,String>>();


        editTextUserName = (EditText)findViewById(R.id.loginIdInput);
        editTextPassword = (EditText)findViewById(R.id.loginPasswordInput);
        editTextConfirmPassword=(EditText)findViewById(R.id.loginConfirmPasswordInput);

        /*// get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextUserName=(EditText)findViewById(R.id.loginIdInput);
        editTextPassword=(EditText)findViewById(R.id.loginPasswordInput);
        editTextConfirmPassword=(EditText)findViewById(R.id.loginConfirmPasswordInput);*/

        btnCreateAccount=(Button)findViewById(R.id.button);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                userName=editTextUserName.getText().toString();
                password=editTextPassword.getText().toString();
                confirmPassword=editTextConfirmPassword.getText().toString();

                getData("http://122.44.13.91:11057/getdata.php");

                try {
                    JSONObject jsonObj = new JSONObject(myJSON);
                    peoples = jsonObj.getJSONArray(TAG_RESULTS);

                    for(int i=0;i<peoples.length();i++){
                        JSONObject c = peoples.getJSONObject(i);
                        dbaccountMail = c.getString(TAG_MAIL);
                        dbacccountPw = c.getString(TAG_PW);

                        if(userName.equals("")||password.equals(""))
                        {
                            Toast.makeText(getApplicationContext(), "입력이 없습니다.",
                                    Toast.LENGTH_LONG).show();
                        }
                        if(userName.equals(dbaccountMail))
                        {
                            Toast.makeText(getApplicationContext(), "동일한 계정이 있습니다.",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            insertToDatabase(userName, password);
                            Toast.makeText(getApplicationContext(), "Account Successfully Created",
                                    Toast.LENGTH_LONG).show();

                            finish();
                        }

                        HashMap<String,String> persons = new HashMap<String,String>();

                        /*persons.put(TAG_MAIL,dbaccountMail);
                        persons.put(TAG_PW,dbacccountPw);

                        personList.add(persons);*/
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



                // check if any of the fields are vaccant
                /*if(userName.equals("")||password.equals("")||confirmPassword.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant",
Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match",
Toast.LENGTH_LONG).show();
                }
                else
                {
                    // Save the Data in Database
                    loginDataBaseAdapter.insertEntry(userName, password);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ",
Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader
                            (con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }



            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    /*@Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }*/


    //    private SQLiteDatabase db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_join);
//
//
//    }
    private void insertToDatabase(String _mail, String _pw){

        class InsertData extends AsyncTask<String, Void, String>
        {
            ProgressDialog loading;


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
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String mail = (String)params[0];
                    String pw = (String)params[1];

                    String link="http://122.44.13.91:11057/signup.php";
                    String data  = URLEncoder.encode("mail", "UTF-8") + "="
                            + URLEncoder.encode(mail, "UTF-8");
                    data += "&" + URLEncoder.encode("pw", "UTF-8") + "="
                            + URLEncoder.encode(pw, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){

                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(_mail,_pw);
    }
}