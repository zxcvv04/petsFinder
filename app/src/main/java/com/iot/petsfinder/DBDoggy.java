package com.iot.petsfinder;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.util.ArrayList;

/**
 * Created by dd on 2017-05-25.
 */

public class DBDoggy {

    private final static int ACTIVITY_ADDITEM = 1006;
    private final static String DB_URL_LOGIN = "http://122.44.13.91:11057/ddddddddd";
    private static final String TAG_DOG_TYPE = "type";
    private static final String TAG_DOG_GENDER = "gender";
    private static final String TAG_DOG_AGE = "age";
    private static final String TAG_DOG_ISLOST = "islost";
    private static final String TAG_RESULTS = "result";
    private String date, type, age, gender, phonenum, detailinfo, islost;
    private Blob image;

    private String myJSON;

    ArrayList<Doggy> DoggyList = new ArrayList<>();


    private void loadData() {

        getData(DB_URL_LOGIN);

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            JSONArray doggyList = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < doggyList.length(); i++) {
                JSONObject _tmpJson = doggyList.getJSONObject(i);
                Doggy doggy = new Doggy();

                //TODO :::image inflat
                doggy.setImageId(R.drawable.dogsub2);
                doggy.setType("종류" + _tmpJson.getString(TAG_DOG_TYPE));
                doggy.setAge("나이" + _tmpJson.getString(TAG_DOG_AGE));
                doggy.setGender("성별" + _tmpJson.getString(TAG_DOG_GENDER));

                //when the doggy is lost attach to lost doggy list
                if (_tmpJson.getString(TAG_DOG_ISLOST).equals("1"))
                    DoggyList.add(doggy);
                else DoggyList.add(doggy);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ee", e.getMessage());
        }
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


//    private void insertToDatabase(String _mail, String _pw) {
//
//        class InsertData extends AsyncTask<String, Void, String> {
//            private ProgressDialog loading;
//
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                try {
//                    String mail = params[0];
//                    String pw = params[1];
//
//                    String data = URLEncoder.encode("mail", "UTF-8") + "="
//                            + URLEncoder.encode(mail, "UTF-8");
//                    data += "&" + URLEncoder.encode("pw", "UTF-8") + "="
//                            + URLEncoder.encode(pw, "UTF-8");
//
//                    URL url = new URL(DB_URL_LOGIN);
//                    URLConnection conn = url.openConnection();
//
//                    conn.setDoOutput(true);
//                    OutputStreamWriter wr =
//                            new OutputStreamWriter(conn.getOutputStream());
//
//                    wr.write(data);
//                    wr.flush();
//
//                    BufferedReader reader = new BufferedReader(
//                            new InputStreamReader(conn.getInputStream()));
//
//                    StringBuilder sb = new StringBuilder();
//                    String line;
//
//                    // Read Server Response
//                    while ((line = reader.readLine()) != null) {
//                        sb.append(line);
//                        break;
//                    }
//                    return sb.toString();
//                } catch (Exception e) {
//                    return "Exception: " + e.getMessage();
//                }
//            }
//        }
//        InsertData task = new InsertData();
//        task.execute(_mail, _pw);
//    }
}
