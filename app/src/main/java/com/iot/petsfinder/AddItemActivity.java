package com.iot.petsfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class AddItemActivity extends AppCompatActivity {

    public static final String UPLOAD_URL = "http://122.44.13.91:11057/insert_image.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";

    private Button buttonChoose;

    private Bitmap bitmap;
    private Uri filePath;

    private int PICK_IMAGE_REQUEST = 1;

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_MAIL = "mail";
    private static final String TAG_PW = "pw";

    JSONArray getJsondataArry = null;

    private static final int ACTIVITY_FOUNDLIST = 1007;
    private static final int ACTIVITY_LOSTLIST = 1008;
    TextView txtVSelectedDoggyFamily;
    EditText edtxtDogAge, edtxtDogSex, edtxtContactInfo, edtxtDetails;
    Button btnAttachLostList, btnAttachFoundList;

    private String inputType, inputAge, inputGender, inputPhonenum, inputDetailinfo, inputIslost, inputage;
    private ImageView imageView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        buttonChoose = (Button) findViewById(R.id.btnTakeSnap);
        imageView = (ImageView) findViewById(R.id.imageView);

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
    protected void btnbuttonChoose (View v) {
        showFileChooser();
    }
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

    protected void btnAttachFoundListClicked (View v) {

        //////TODO insert db data

        Intent intent = new Intent(getApplicationContext(),
                PetsInfoActivity.class);
        startActivityForResult(intent, ACTIVITY_FOUNDLIST);

        inputInserValues();
        inputIslost = "0";

        insertToDatabase(inputType, inputAge, inputGender, inputPhonenum, inputDetailinfo, inputIslost);

        uploadImage();

    }

    protected void btnAttachLostListClicked (View v) {

        //////TODO insert db data

        Intent intent = new Intent(getApplicationContext(),
                LostListActivity.class);
        startActivityForResult(intent, ACTIVITY_LOSTLIST);

        inputInserValues();
        inputIslost = "1";
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
    private void insertToDatabase(String _type, String _age, String _gender, String _phonenum, String _detailinfo, String _Islost)
    {

        class InsertData extends AsyncTask<String, Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();

                loading = ProgressDialog.show(AddItemActivity.this,
                        "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params)
            {

                try
                {
                    String type = (String) params[0];
                    String age = (String) params[1];
                    String gender = (String) params[2];
                    String phonenum = (String) params[3];
                    String detailinfo = (String) params[4];
                    String islost = (String) params[5];

                    String link = "http://122.44.13.91:11057/insert_petsinfo.php";

                    String data = URLEncoder.encode("type", "UTF-8") + "="
                            + URLEncoder.encode(type, "UTF-8");
                    data += "&" + URLEncoder.encode("age", "UTF-8") + "="
                            + URLEncoder.encode(age, "UTF-8");
                    data += "&" + URLEncoder.encode("gender", "UTF-8") + "="
                            + URLEncoder.encode(gender, "UTF-8");
                    data += "&" + URLEncoder.encode("phonenum", "UTF-8") + "="
                            + URLEncoder.encode(phonenum, "UTF-8");
                    data += "&" + URLEncoder.encode("detailinfo", "UTF-8") + "="
                            + URLEncoder.encode(detailinfo, "UTF-8");
                    data += "&" + URLEncoder.encode("islost", "UTF-8") + "="
                            + URLEncoder.encode(islost, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e)
                {

                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(_type, _age, _gender, _phonenum, _detailinfo, _Islost);
    }

    private void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage()
    {
        class UploadImage extends AsyncTask<Bitmap, Void, String>
        {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(AddItemActivity.this, "Uploading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params)
            {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.sendPostRequest(UPLOAD_URL, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    private void viewImage() {
        startActivity(new Intent(this, ViewImage.class));
    }

    private void inputInserValues(){
        inputType=txtVSelectedDoggyFamily.getText().toString();
        inputAge=edtxtDogAge.getText().toString();
        inputGender=edtxtDogSex.getText().toString();
        inputPhonenum=edtxtContactInfo.getText().toString();
        inputDetailinfo=edtxtDetails.getText().toString();
    }

}
