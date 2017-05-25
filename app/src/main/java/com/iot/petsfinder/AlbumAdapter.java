package com.iot.petsfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>
{
    private ArrayList<Doggy> _doggies;

    private Context context;
    private ArrayList<Doggy> items;
    private int lastPosition = -1;

    public AlbumAdapter(ArrayList<Doggy> doggies){
        _doggies = doggies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Doggy doggy = _doggies.get(position);
        holder.albumImage.setBackgroundResource(doggy.getImageId());
        holder.albumType.setText(doggy.getType());
        holder.albumAge.setText(doggy.getAge());
        holder.albumGender.setText(doggy.getGender());

        setAnimation(holder.albumImage, position);
    }

    @Override
    public int getItemCount()
    {
        return _doggies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView){
            super(itemView);

            albumImage = (ImageView) itemView.findViewById(R.id.img);
            albumType = (TextView) itemView.findViewById(R.id.type);
            albumAge = (TextView) itemView.findViewById(R.id.age);
            albumGender = (TextView) itemView.findViewById(R.id.gender);
        }
        ImageView albumImage;
        TextView albumType;
        TextView albumAge;
        TextView albumGender;
    }
    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.setAnimation(animation);
            lastPosition = position;
        }
    }
}
