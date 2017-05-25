package com.iot.petsfinder;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
//
//<<<<<<<HEAD
//        =======
//        >>>>>>>origin/master

public class PetsInfoActivity extends AppCompatActivity
{
    private static final String TAG_DOG_VAR = "type";
    private static final String TAG_DOG_GENDER = "gender";
    private static final String TAG_DOG_AGE = "age";
    private CardView _cardView;
    private RecyclerView _recyclerView;
    private String myJSON;
//<<<<<<< HEAD
    private final static int ACTIVITY_ADDITEM = 1006;
    private static final String TAG_RESULTS = "result";
    private final static String DB_URL_LOGIN = "http://122.44.13.91:11057/ddddddddd";
//=======
//>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_info);

        _recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

//<<<<<<< HEAD
        ArrayList<Doggy> doggies = loadData();

        AlbumAdapter adapter = new AlbumAdapter(doggies);
        _recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        _recyclerView.setLayoutManager(layoutManager);
        _recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    //// list binding
    private ArrayList<Doggy> loadData()
    {

        getData(DB_URL_LOGIN);

        ArrayList<Doggy> lostDoggyList = new ArrayList<>();
        ArrayList<Doggy> foundDoggyList = new ArrayList<>();

        try
        {

            JSONObject jsonObj = new JSONObject(myJSON);
            JSONArray doggyList = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < doggyList.length(); i++)
            {
                JSONObject _tmpJson = doggyList.getJSONObject(i);
                Doggy doggy = new Doggy();
                doggy.setImageId(R.drawable.dogsub2);
                doggy.setType("종류" + _tmpJson.getString(TAG_DOG_VAR));
                doggy.setAge("나이" + _tmpJson.getString(TAG_DOG_AGE));
                doggy.setGender("성별" + _tmpJson.getString(TAG_DOG_GENDER));

                //albums.add(doggy);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            Log.e("ee", e.getMessage());
        }

        //return albums;
//=======
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        _recyclerView.setLayoutManager(layoutManager);
        _recyclerView.setItemAnimator(new DefaultItemAnimator());

        _cardView = (CardView) findViewById(R.id.card_view);

        _recyclerView.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        _recyclerView.setClickable(true);
                        Toast.makeText(
                                getApplicationContext(),
                                "항목이 선택되었습니다.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
        return lostDoggyList;
    }
//>>>>>>> origin/master


    //// get json from db
    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            private String myJSON;

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

                loading = ProgressDialog.show(PetsInfoActivity.this,
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

                    URL url = new URL(DB_URL_LOGIN);
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

}