package com.iot.petsfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;

import static com.iot.petsfinder.Detail.ACTIVITY_ADDITEM;

public class LoserInfoActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loser_info_floatbtn);

        /*mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        ArrayList<Album> albums = loadData();

        AlbumAdapter adapter = new AlbumAdapter(albums);
        mRecyclerView.setAdapter(adapter);

        // 이 부분에서 정렬 방식을 설정합니다.
        mLayoutManager = new GridLayoutManager(this, 2);
        //mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AlbumAdapter(albums);
       mRecyclerView.setAdapter(mAdapter);*/

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        ArrayList<Album> albums = loadData();
        AlbumAdapter adapter = new AlbumAdapter(albums);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabInLostList =
                (FloatingActionButton) findViewById(R.id.fabInLostList);
        fabInLostList.setOnClickListener(new View.OnClickListener() {
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
