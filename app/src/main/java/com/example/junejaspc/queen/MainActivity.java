package com.example.junejaspc.queen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
boolean exit=false;

    @Override
    protected void onResume() {
        super.onResume();
        exit=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
