package com.example.junejaspc.queen;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ChessBoard extends AppCompatActivity implements View.OnClickListener {
GridLayout gridLayout;
    Button button,button_set[][];
    int i,height,width,totalbuttons,rowlimit;
    boolean j;
    boolean decide;
    DisplayMetrics displayMetrics;
    int buttons_state[][];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_board);
        gridLayout=(GridLayout)findViewById(R.id.grid);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        j=true;
        decide=true;
        rowlimit=8;
        buttons_state=new int[rowlimit][rowlimit];
        decideFactor();
    }
    public void decideFactor(){
        totalbuttons=rowlimit*rowlimit;
        height = displayMetrics.heightPixels/rowlimit;
        width = displayMetrics.widthPixels/rowlimit;
        gridLayout.setRowCount(rowlimit);
        gridLayout.setColumnCount(rowlimit);
        boardsetup();
    }
    public void boardsetup(){
        int i,j;
        button_set=new Button[rowlimit][rowlimit];
        for(i=0;i<rowlimit;i++)
        {
            for(j=0;j<rowlimit;j++){
               button_set[i][j]=new Button(this);
                button_set[i][j].setLayoutParams(new LinearLayout.LayoutParams(width,width));
                if(decide)
                    button_set[i][j].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                else  button_set[i][j].setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                decide=!decide;
                gridLayout.addView(button_set[i][j]);
                button_set[i][j].setOnClickListener(this);
            }
            decide=!decide;
        }
    }
    /*public void boardsetup(){
        for(i=0;i<totalbuttons;i++){
            if(i%rowlimit==0&&i>0)j=!j;
            button=new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(width,width));
            if(j)
                button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            else  button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            j=!j;
            gridLayout.addView(button);
            button.setOnClickListener(this);

        }
    }*/

    @Override
    public void onClick(View v) {

    }
}
