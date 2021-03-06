package com.iot.petsfinder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FoundListItemFragment extends Fragment
{
    private static String myJSON;
    private static JSONArray getJsonfoundinfodataArry = null;

    private static final String TAG_RESULTS = "result";

    private static final String TAG_date = "date";
    private static final String TAG_type = "type";
    private static final String TAG_age = "age";
    private static final String TAG_gender = "gender";
    private static final String TAG_phonenum = "phonenum";
    private static final String TAG_detailinfo = "detailinfo";
    private static final String TAG_islost = "islost";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.activity_pets_info, container, false);
        getData("http://122.44.13.91:11057/getdata_petsinfo.php");

        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;


    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView type;
        TextView age;
        TextView gender;
        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_view, parent, false));
            image = (ImageView) itemView.findViewById(R.id.img);
            type = (TextView) itemView.findViewById(R.id.txtCardVDogType);
            age = (TextView) itemView.findViewById(R.id.txtCardVDogAge);
            gender = (TextView) itemView.findViewById(R.id.gender);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, Detail.class);
                    intent.putExtra(Detail.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);


                }
            });
        }
    }

    private static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;
        private Drawable[] image;
        private String[] date;
        private String[] type;
        private String[] age;
        private String[] gender;
        private String[] phonenum;
        private String[] detailinfo;
        private String[] islost;


        ContentAdapter(Context context) {
            Resources resources = context.getResources();
            try
            {
                JSONObject jsonObj = new JSONObject(myJSON);
                getJsonfoundinfodataArry = jsonObj.getJSONArray(TAG_RESULTS);

                for (int i = 0; i < getJsonfoundinfodataArry.length(); i++)
                {
                    JSONObject c = getJsonfoundinfodataArry.getJSONObject(i);

                    type = resources.getStringArray(Integer.parseInt(c.getString(TAG_type)));
                    age = resources.getStringArray(Integer.parseInt(c.getString(TAG_age)));
                    gender = resources.getStringArray(Integer.parseInt(c.getString(TAG_gender)));

                    /*TypedArray a = resources.obtainTypedArray(R.array.place_avator);
                    image = new Drawable[a.length()];
                    for (int i = 0; i < image.length; i++) {
                        image[i] = a.getDrawable(i);
                    }
                    a.recycle();*/
                }
                

            } catch (JSONException e)
            {
                e.printStackTrace();
            }





        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.image.setImageDrawable(image[position % image.length]);
            holder.type.setText(type[position % type.length]);
            holder.age.setText(age[position % age.length]);
            holder.gender.setText(gender[position % gender.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
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


}
