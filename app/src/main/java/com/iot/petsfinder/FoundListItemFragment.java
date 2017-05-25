package com.iot.petsfinder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FoundListItemFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.activity_pets_info, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView type;
        public TextView age;
        public TextView gender;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_view, parent, false));
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

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;
        private final Drawable[] image;
        private final String[] type;
        private final String[] age;
        private final String[] gender;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            type = resources.getStringArray(R.array.places);
            age = resources.getStringArray(R.array.place_details);
            gender = resources.getStringArray(R.array.place_desc);
            TypedArray a = resources.obtainTypedArray(R.array.place_avator);
            image = new Drawable[a.length()];
            for (int i = 0; i < image.length; i++) {
                image[i] = a.getDrawable(i);
            }
            a.recycle();
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
}
