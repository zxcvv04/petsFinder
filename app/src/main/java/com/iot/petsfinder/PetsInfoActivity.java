package com.iot.petsfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class PetsInfoActivity extends AppCompatActivity
{
    private RecyclerView _recyclerView;
    private final static int ACTIVITY_ADDITEM = 1006;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_info_floatbtn);

        _recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        ArrayList<Album> albums = loadData();

        AlbumAdapter adapter = new AlbumAdapter(albums);
        _recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        _recyclerView.setLayoutManager(layoutManager);
        _recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fabInFoundList =
                (FloatingActionButton) findViewById(R.id.fabInFoundList);
        fabInFoundList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        AddItemActivity.class
                );
                startActivityForResult(intent, ACTIVITY_ADDITEM);
            }
        });

    }

    private ArrayList<Album> loadData()
    {
        ArrayList<Album> albums = new ArrayList<>();

        for (int i = 0; i < 100; i++)
        {
            Album album = new Album();
            album.setImageId(R.drawable.dogsub2);
            album.setType("종류"+(i+1));
            album.setAge((i+1));
            album.setGender("성별"+"남");

            albums.add(album);
        }
        return albums;
    }
}