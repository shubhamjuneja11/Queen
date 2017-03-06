package com.example.junejaspc.queen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ChessBoard extends AppCompatActivity {
GridLayout gridLayout;
    Button button;
    int i,height,width,totalbuttons,rowlimit;
    boolean j;
    DisplayMetrics displayMetrics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_board);
        gridLayout=(GridLayout)findViewById(R.id.grid);
        j=true;
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        decideFactor();
    }
    public void decideFactor(){
        totalbuttons=rowlimit*rowlimit;
        height = displayMetrics.heightPixels/rowlimit;
        width = displayMetrics.widthPixels/rowlimit;
        boardsetup();
    }
    public void boardsetup(){
        for(i=0;i<totalbuttons;i++){
            if(i%rowlimit==0&&i>0)j=!j;
            button=new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(width,width));
            if(j)
                button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            else  button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            j=!j;
            gridLayout.addView(button);
        }
    }
}
