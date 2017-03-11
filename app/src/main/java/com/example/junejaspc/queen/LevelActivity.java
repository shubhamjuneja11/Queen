package com.example.junejaspc.queen;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {
LevelAdapter adapter;
    RecyclerView recyclerView;
    List<LevelClass>levels;
    String lev[];
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this,R.color.colorPrimary));
        lev=new String[13];
        lev[0]="4x4";
        lev[1]="5x5";
        lev[2]="6x6";
        lev[3]="7x7";
        lev[4]="8x8";
        lev[5]="9x9";
        lev[6]="10x10";
        lev[7]="11x11";
        lev[8]="12x12";
        lev[9]="13x13";
        lev[10]="14x14";
        lev[11]="15x15";
        lev[12]="16x16";
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        levels=new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new LevelAdapter(this,levels);
        recyclerView.setAdapter(adapter);
        preparedata();
    }
    public void preparedata(){
        levels.add(new LevelClass(1,"Level 1","4x4"));
        levels.add(new LevelClass(2,"Level 2","5x5"));
        levels.add(new LevelClass(3,"Level 3","6x6"));
        levels.add(new LevelClass(4,"Level 4","7x7"));
        levels.add(new LevelClass(5,"Level 5","8x8"));
        levels.add(new LevelClass(5,"Level 6","9x9"));
        levels.add(new LevelClass(5,"Level 7","10x10"));
        levels.add(new LevelClass(5,"Level 8","11x11"));
        levels.add(new LevelClass(5,"Level 9","12x12"));
        levels.add(new LevelClass(5,"Level 10","13x13"));
        levels.add(new LevelClass(5,"Level 11","14x14"));
        levels.add(new LevelClass(5,"Level 12","15x15"));
        levels.add(new LevelClass(5,"Level 13","16x16"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int a=1;
        String mylevel=((TextView)v.findViewById(R.id.levelcount)).getText().toString();
        for(int i=0;i<lev.length;i++){
            if(mylevel.equals(lev[i]))
            {a=i+4;break;}
        }
        Intent intent=new Intent(LevelActivity.this,ChessBoard.class);
        intent.putExtra("count",a);
        startActivity(intent);
    }
}
