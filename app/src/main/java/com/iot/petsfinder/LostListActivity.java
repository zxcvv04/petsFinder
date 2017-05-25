package com.iot.petsfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class LostListActivity extends AppCompatActivity
{
    private CardView _cardView;
    private RecyclerView _recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_info);

        _recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        _recyclerView.setLayoutManager(layoutManager);
        _recyclerView.setItemAnimator(new DefaultItemAnimator());

        _cardView=(CardView) findViewById(R.id.card_view);

        _recyclerView.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        _recyclerView.setClickable(true);
                        Toast.makeText(
                                getApplicationContext(),
                                "항목이 선택되었습니다.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }
}