package com.supergeek.junejaspc.nqueens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {
LevelAdapter adapter;
    RecyclerView recyclerView;
    List<LevelClass>levels;
    String lev[];
    int a=4;
    int n=17;
    Intent intent;
    AlertDialog dialog;
    CollapsingToolbarLayout collapsingToolbarLayout;
    SharedPreferences sharedPreferences;
    public static  Boolean decide[],completed[];
    @Override
    protected void onResume() {
        super.onResume();
        boolean a,b;

        LevelActivity.decide=new Boolean[18];
        completed=new Boolean[18];
        if(sharedPreferences==null)
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        for(int i=1;i<=17;i++){
            a=sharedPreferences.getBoolean(String.valueOf(i),false);

            if(!a)
                LevelActivity.decide[i]=true;
            else LevelActivity.decide[i]=false;

            b=sharedPreferences.getBoolean(ChessBoard.colors[i-1]+ChessBoard.savecompleted,false);
            if(!b)
                completed[i]=true;
            else completed[i]=false;
        }
        if(adapter!=null)adapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this,R.color.colorPrimary));
        lev=new String[17];
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
        lev[13]="17x17";
        lev[14]="18x18";
        lev[15]="19x19";
        lev[16]="20x20";
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
        levels.add(new LevelClass(6,"Level 6","9x9"));
        levels.add(new LevelClass(7,"Level 7","10x10"));
        levels.add(new LevelClass(8,"Level 8","11x11"));
        levels.add(new LevelClass(9,"Level 9","12x12"));
        levels.add(new LevelClass(10,"Level 10","13x13"));
        levels.add(new LevelClass(11,"Level 11","14x14"));
        levels.add(new LevelClass(12,"Level 12","15x15"));
        levels.add(new LevelClass(13,"Level 13","16x16"));
        levels.add(new LevelClass(14,"Level 14","17x17"));
        levels.add(new LevelClass(15,"Level 15","18x18"));
        levels.add(new LevelClass(16,"Level 16","19x19"));
        levels.add(new LevelClass(17,"Level 17","20x20"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        String mylevel=((TextView)v.findViewById(R.id.levelcount)).getText().toString();
        for(int i=0;i<lev.length;i++){
            if(mylevel.equals(lev[i]))
            {a=i+4;break;}
        }

        if(!decide[a-3])
        {

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.yesnodialog, null);
            builder.setView(view);
             dialog = builder.create();
            dialog.show();
        }
        else{
            intent=new Intent(LevelActivity.this,ChessBoard.class);
            intent.putExtra("count",a);
            startActivity(intent);
        }

    }
    public void yesresume(View view){
        dialog.dismiss();
         intent=new Intent(LevelActivity.this,ChessBoard.class);
        intent.putExtra("count",a);
        intent.putExtra("saved",true);
        startActivity(intent);
    }
    public void noresume(View view){
        dialog.dismiss();
        intent=new Intent(LevelActivity.this,ChessBoard.class);
        intent.putExtra("count",a);
        intent.putExtra("saved",false);
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {

        try {
            Intent upIntent=NavUtils.getParentActivityIntent(this);;
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {

                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities();
            } else {

                NavUtils.navigateUpTo(this, upIntent);
            }

        } catch (Exception e) {
        }
    }
}
