package com.iot.petsfinder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeContentFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.activity_pets_info, container, false);
//        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView type;
        TextView age;
        TextView gender;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.activity_home, parent, false));
            image = (ImageView) itemView.findViewById(R.id.img);
            type = (TextView) itemView.findViewById(R.id.type);
            age = (TextView) itemView.findViewById(R.id.age);
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

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder>
    {

        private final Drawable[] image;
        String[] type;
        String[] age;
        String[] gender;
        private static final int LENGTH = 2;
        private static final String TAG_DOG_TYPE = "type";
        private static final String TAG_DOG_GENDER = "gender";
        private static final String TAG_DOG_AGE = "age";
        private static final String TAG_DOG_ISLOST = "islost";
        private CardView _cardView;
        private RecyclerView _recyclerView;
        private String myJSON;
        //<<<<<<< HEAD
        private final static int ACTIVITY_ADDITEM = 1006;
        private static final String TAG_RESULTS = "result";
        private final static String DB_URL_LOGIN = "http://122.44.13.91:11057/ddddddddd";

//        private final String[] gender;

        //// get json from db
        void getData(String url) {
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

        void getJson() {
            getData(DB_URL_LOGIN);
            try {
                JSONObject jsonObj = new JSONObject(myJSON);
//                JSONArray doggyList = jsonObj.getJSONArray(TAG_RESULTS);

                type = jsonObj.get(TAG_DOG_TYPE).toString().split(",");
                age = jsonObj.get(TAG_DOG_AGE).toString().split(",");
                gender = jsonObj.get(TAG_DOG_GENDER).toString().split(",");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ContentAdapter(Context context) {

            getJson();
            Resources resources = context.getResources();

//                TypedArray a = resources.obtainTypedArray(R.array.place_avator);
//                type = resources.getStringArray(R.array.places);
//                age = resources.getStringArray(R.array.place_details);
//                gender = resources.getStringArray(R.array.place_desc);
            TypedArray a = resources.obtainTypedArray(R.array.place_avator);
            image = new Drawable[a.length()];
            for (int i = 0; i < image.length; i++) {
                image[i] = a.getDrawable(i);
            }
            a.recycle();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            holder.image.setImageDrawable(image[position % image.length]);
            holder.type.setText(type[position % type.length]);
            holder.age.setText(age[position % age.length]);
            holder.gender.setText(gender[position % gender.length]);
        }

        @Override
        public int getItemCount()
        {
            return LENGTH;
        }
    }
}