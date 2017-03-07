package com.example.junejaspc.queen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LevelActivity extends AppCompatActivity {
LevelAdapter adapter;
    RecyclerView recyclerView;
    List<LevelClass>levels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        levels=new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new LevelAdapter(levels);
        recyclerView.setAdapter(adapter);
        preparedata();
    }
    public void preparedata(){
        levels.add(new LevelClass("Level 1","4X4"));
        levels.add(new LevelClass("Level 2","5X5"));
        levels.add(new LevelClass("Level 3","6X6"));
        levels.add(new LevelClass("Level 4","7X7"));
        levels.add(new LevelClass("Level 5","8X8"));
        adapter.notifyDataSetChanged();
    }
}
