package com.example.junejaspc.queen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
}
