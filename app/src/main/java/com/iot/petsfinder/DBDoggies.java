//package com.iot.petsfinder;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.sql.Blob;
//import java.util.ArrayList;
//
//
//public class DBDoggies {
//
//    private final static int ACTIVITY_ADDITEM = 1006;
//    private final static String DB_URL_GETDATA = "http://122.44.13.91:11057/getdata_petsinfo.php";
//    private final static String DB_URL_SETDATA = "http://122.44.13.91:11057/getdata_petsinfo.php";
//    private static final String TAG_DOG_TYPE = "type";
//    private static final String TAG_DOG_GENDER = "gender";
//    private static final String TAG_DOG_AGE = "age";
//    private static final String TAG_DOG_ISLOST = "islost";
//    private static final String TAG_RESULTS = "result";
//    private static final String TAG_DOG_DETAILINFO = "detailinfo";
//    private static final String TAG_DOG_PHONENUM = "phonenum";
//    private String date, type, age, gender, phonenum, detailinfo, islost;
//    private Blob image;
//
//    private String myJSON;
//
//    private ArrayList<Doggy> lostDoggie = new ArrayList<>();
//    ArrayList<Doggy> foundDoggie = new ArrayList<>();
//
//    ////// getter for lost doggie
//    public ArrayList<Doggy> getLostDoggie() {
//        loadData();
//        return lostDoggie;
//    }
//    /////found doggie getter
//    public ArrayList<Doggy> getFoundDoggie() {
//        loadData();
//        return foundDoggie;
//    }
//
////    public void setLostDog(ArrayList<Doggy> setlostdog) {
////        insertToDatabase(setlostdog);
////    }
//
//    private void loadData() {
//
//        getData(DB_URL_GETDATA);
//
//        try {
//            JSONObject jsonObj = new JSONObject(myJSON);
//            JSONArray doggyList = jsonObj.getJSONArray(TAG_RESULTS);
//
//            for (int i = 0; i < doggyList.length(); i++) {
//                JSONObject _tmpJson = doggyList.getJSONObject(i);
//                Doggy doggy = new Doggy();
//
////                doggy.setImageId(R.drawable.dogsub2);
//
//                doggy.setDetailinfo(_tmpJson.getString(TAG_DOG_DETAILINFO));
//                doggy.setDate(_tmpJson.getString(TAG_DOG_TYPE));
//                doggy.setType(_tmpJson.getString(TAG_DOG_TYPE));
//                doggy.setAge(_tmpJson.getString(TAG_DOG_AGE));
//                doggy.setGender(_tmpJson.getString(TAG_DOG_GENDER));
//                doggy.setIslost(_tmpJson.getString(TAG_DOG_ISLOST));
//                doggy.setPhonenum(_tmpJson.getString(TAG_DOG_PHONENUM));
//
//                //when the doggy is lost attach to lost doggy list
//                if (_tmpJson.getString(TAG_DOG_ISLOST).equals("1"))
//                    lostDoggie.add(doggy);
//                else foundDoggie.add(doggy);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("ee", e.getMessage());
//        }
//    }
//
//
//    private void getData(String url) {
//
//        class GetDataJSON extends AsyncTask<String, Void, String> {
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
//                        stringBuilder.append(json).append("\n");
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
//
////    private void insertToDatabase(ArrayList<Doggy> setlostdog) {
////
////        class InsertData extends AsyncTask<String, Void, String> {
////            private ProgressDialog loading;
////
////            @Override
////            protected void onPostExecute(String s) {
////                super.onPostExecute(s);
////                loading.dismiss();
////            }
////
////            @Override
////            protected String doInBackground(String... params) {
////
////                try {
////                    String mail = params[0];
////                    String pw = params[1];
////
////                    String data = URLEncoder.encode("mail", "UTF-8") + "="
////                            + URLEncoder.encode(mail, "UTF-8");
////                    data += "&" + URLEncoder.encode("pw", "UTF-8") + "="
////                            + URLEncoder.encode(pw, "UTF-8");
////
////                    URL url = new URL(DB_URL_LOGIN);
////                    URLConnection conn = url.openConnection();
////
////                    conn.setDoOutput(true);
////                    OutputStreamWriter wr =
////                            new OutputStreamWriter(conn.getOutputStream());
////
////                    wr.write(data);
////                    wr.flush();
////
////                    BufferedReader reader = new BufferedReader(
////                            new InputStreamReader(conn.getInputStream()));
////
////                    StringBuilder sb = new StringBuilder();
////                    String line;
////
////                    // Read Server Response
////                    while ((line = reader.readLine()) != null) {
////                        sb.append(line);
////                        break;
////                    }
////                    return sb.toString();
////                } catch (Exception e) {
////                    return "Exception: " + e.getMessage();
////                }
////            }
////        }
////        InsertData task = new InsertData();
////        task.execute(_mail, _pw);
////    }
//}
