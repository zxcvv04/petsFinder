package com.iot.petsfinder;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class PetsInfoActivity extends AppCompatActivity
{
    private RecyclerView _recyclerView;
    private final static int ACTIVITY_ADDITEM = 1006;
    private final static String DB_URL_LOGIN = "http://122.44.13.91:11057/ddddddddd";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_info);

        _recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        ArrayList<Album> albums = loadData();

        AlbumAdapter adapter = new AlbumAdapter(albums);
        _recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        _recyclerView.setLayoutManager(layoutManager);
        _recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    //// list binding
    private ArrayList<Album> loadData() {

        getData(DB_URL_LOGIN);

        ArrayList<Album> albums = new ArrayList<>();

        try {

            for (int i = 0; i < 100; i++) {
                Album album = new Album();
                album.setImageId(R.drawable.dogsub2);
                album.setType("종류" + (i + 1));
                album.setAge((i + 1));
                album.setGender("성별" + "남");

                albums.add(album);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ee", e.getMessage());
        }

        return albums;
    }

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