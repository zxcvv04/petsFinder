package com.iot.petsfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//
//<<<<<<<HEAD
//        =======
//        >>>>>>>origin/master

public class PetsInfoActivity extends AppCompatActivity implements RecyclerView.OnScrollChangeListener
{
    private static final String TAG_DOG_TYPE = "type";
    private static final String TAG_DOG_GENDER = "gender";
    private static final String TAG_DOG_AGE = "age";
    private static final String TAG_DOG_ISLOST = "islost";
    private CardView _cardView;
    //    private RecyclerView _recyclerView;
    private String myJSON;
    //<<<<<<< HEAD
    private final static int ACTIVITY_ADDITEM = 1006;
    private static final String TAG_RESULTS = "result";
    private final static String DB_URL_LOGIN = "http://122.44.13.91:11057/ddddddddd";
//=======
//>>>>>>> origin/master

    ArrayList<Doggy> doggiesList;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_info);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        doggiesList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        adapter = new CardAdapter(doggiesList, this);

        getDoggieData();

        recyclerView.setOnScrollChangeListener(this);

        adapter = new CardAdapter(doggiesList, this);

        recyclerView.setAdapter(adapter);
    }

    private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar
//        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        //Displaying Progressbar
//        progressBar.setVisibility(View.VISIBLE);
//        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley

        //Returning the request
        return new JsonArrayRequest(Config.DATA_URL + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
//                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(PetsInfoActivity.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //This method will get data from the web api
    private void getDoggieData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            Doggy doggy = new Doggy();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                doggy.setAge(json.getString(Config.TAG_AGE));
                doggy.setGender(json.getString(Config.TAG_GENDER));
                doggy.setType(json.getString(Config.TAG_TYPE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            doggiesList.add(doggy);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }

    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition =
                    ((LinearLayoutManager) recyclerView.getLayoutManager())
                            .findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION
                    && lastVisibleItemPosition == recyclerView.getAdapter()
                    .getItemCount() - 1)
                return true;
        }
        return false;
    }

//    Overriden method to detect scrolling
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //Ifscrolled at last then
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getdata again
            getDoggieData();
        }
    }

    /////////
    /////////
    /////////
    /////////


//
////<<<<<<< HEAD
//        ArrayList<Doggy> doggies = loadData();
////        ArrayList<Doggy> lostDoggyList = new ArrayList<>();
////        ArrayList<Doggy> foundDoggyList = new ArrayList<>();
//
//
//        CardAdapter adapter = new CardAdapter(doggies);
//        _recyclerView.setAdapter(adapter);
//
//        RecyclerView.LayoutManager layoutManager =
//                new LinearLayoutManager(getApplicationContext());
//        _recyclerView.setLayoutManager(layoutManager);
//        _recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//    }
//
//
//    //// list binding
//    private ArrayList<Doggy> loadData() {
//
//        getData(DB_URL_LOGIN);
//
//        try {
//            JSONObject jsonObj = new JSONObject(myJSON);
//            JSONArray doggyList = jsonObj.getJSONArray(TAG_RESULTS);
//
//            for (int i = 0; i < doggyList.length(); i++) {
//                JSONObject _tmpJson = doggyList.getJSONObject(i);
//
//                if (_tmpJson.getString(TAG_DOG_ISLOST).equals("1")) {
//                    Doggy doggy = new Doggy();
//
////                doggy.setImageId(R.drawable.dogsub2);
//                    doggy.setType("종류" + _tmpJson.getString(TAG_DOG_TYPE));
//                    doggy.setAge("나이" + _tmpJson.getString(TAG_DOG_AGE));
//                    doggy.setGender("성별" + _tmpJson.getString(TAG_DOG_GENDER));
//
//                    doggies.add(doggy);
//                }
//                //when the doggy is lost attach to lost doggy list
////                if (_tmpJson.getString(TAG_DOG_ISLOST).equals("1"))
////                    lostDoggyList.add(doggy);
////                else foundDoggyList.add(doggy);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("ee", e.getMessage());
//        }
//
//        return doggies;
//    }
////=======
////        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
////        _recyclerView.setLayoutManager(layoutManager);
////        _recyclerView.setItemAnimator(new DefaultItemAnimator());
////
////        _cardView=(CardView) findViewById(R.id.card_view);
////
////        _recyclerView.setOnClickListener(
////                new View.OnClickListener()
////                {
////                    @Override
////                    public void onClick(View v)
////                    {
////                        _recyclerView.setClickable(true);
////                        Toast.makeText(
////                                getApplicationContext(),
////                                "항목이 선택되었습니다.",
////                                Toast.LENGTH_SHORT
////                        ).show();
////                    }
////                }
////        );
//////>>>>>>> origin/master
////    }
//
//    //// get json from db
//    public void getData(String url) {
//        class GetDataJSON extends AsyncTask<String, Void, String> {
//
//            private String myJSON;
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                String uri = params[0];
//
//                BufferedReader bufferedReader;
//                try {
//                    URL url = new URL(uri);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    StringBuilder stringBuilder = new StringBuilder();
//
//                    bufferedReader = new BufferedReader(
//                            new InputStreamReader(con.getInputStream()));
//
//                    String json;
//                    while ((json = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(json + "\n");
//                    }
//
//                    return stringBuilder.toString().trim();
//
//                } catch (Exception e) {
//                    return null;
//                }
//
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                myJSON = result;
//            }
//        }
//        GetDataJSON g = new GetDataJSON();
//        g.execute(url);
//    }
//
//    @Override
//    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//    }


//    private void insertToDatabase(String _mail, String _pw) {
//
//        class InsertData extends AsyncTask<String, Void, String> {
//            private ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//                loading = ProgressDialog.show(PetsInfoActivity.this,
//                        "Please Wait", null, true, true);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//
//                loading.dismiss();
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
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