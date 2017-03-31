package com.supergeek.junejaspc.queen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
boolean exit=false;
    private AdView mAdView;
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        exit=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }
    public void newgame(View view){
        Intent intent=new Intent(this,LevelActivity.class);
        startActivity(intent);
    }
   public void openboard(View view){
       Intent intent=new Intent(this,LeaderBoardActivity.class);
       startActivity(intent);
   }
   public void exit(View view){
       super.onBackPressed();
   }
   public void openhelp(View view){
       Intent intent=new Intent(this,HelpClass.class);
       startActivity(intent);
   }
   public void contactus(View view){
        Intent intent=new Intent(this,ContactUs.class);
       startActivity(intent);
   }

    @Override
    public void onBackPressed() {
        if(!exit) {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
        else {
            super.onBackPressed();
        }

    }
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
