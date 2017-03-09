package com.example.junejaspc.queen;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChessBoard extends AppCompatActivity implements View.OnClickListener {
GridLayout gridLayout;
    ImageButton button_set[][];
    int i,height,width,totalbuttons,rowlimit,j,total_queens;
    boolean decide;
    DisplayMetrics displayMetrics;
    boolean buttons_state[][];
    GradientDrawable shapeDrawable,shape2,shape3;
    String colors[]=new String[]{"#7333BF","#CB2A62","#A8AD1F","#D34B20","#649035","#359053",
                                 "#31B0AF","#2C65A9","#13EBE8","#969734","#ED04FC","#FC0488","#0480FC"   };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_board);
        rowlimit=getIntent().getIntExtra("count",4);
        shapeDrawable=new GradientDrawable();
        shapeDrawable.setStroke(1,getResources().getColor(R.color.black));
        shapeDrawable.setColor(Color.parseColor(colors[rowlimit-4]));
        shape2=new GradientDrawable();
        shape3=new GradientDrawable();
        shape2.setStroke(1,getResources().getColor(R.color.black));
        shape2.setColor(Color.parseColor("#FFFFFF"));
        shape3.setStroke(1,getResources().getColor(R.color.black));
        shape3.setColor(Color.parseColor("#FF0000"));
        gridLayout=(GridLayout)findViewById(R.id.grid);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        decide=true;
        total_queens=0;
       // rowlimit=8;

        buttons_state=new boolean[rowlimit][rowlimit];
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

        button_set=new ImageButton[rowlimit][rowlimit];
        for(i=0;i<rowlimit;i++)
        {
            for(j=0;j<rowlimit;j++){
               button_set[i][j]=new ImageButton(this);
                button_set[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                //button_set[i][j].setAdjustViewBounds(true);
                button_set[i][j].setLayoutParams(new LinearLayout.LayoutParams(width,width));
                if(decide) {//button_set[i][j].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    button_set[i][j].setBackgroundDrawable(shapeDrawable);
                }else {
                    //button_set[i][j].setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    button_set[i][j].setBackgroundDrawable(shape2);

                }decide=!decide;
                gridLayout.addView(button_set[i][j]);
                button_set[i][j].setOnClickListener(this);
            }
            if(rowlimit%2==0)
            decide=!decide;
        }
    }
    @Override
    public void onClick(View v) {

            for (i = 0; i < rowlimit; i++)
                for (j = 0; j < rowlimit; j++)
                    if (button_set[i][j] == v) {
                        if (buttons_state[i][j]) {
                            if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0)
                                v.setBackgroundDrawable(shapeDrawable);
                            else v.setBackgroundDrawable(shape2);
                            ((ImageButton)v).setImageResource(0);
                            total_queens--;
                            buttons_state[i][j] = !buttons_state[i][j];
                            check_status(i,j);
                        } else {

                            if(total_queens<rowlimit) {
                                total_queens++; Log.e("aaaaaaaa",total_queens+" "+rowlimit);
                                ((ImageButton)v).setImageResource(R.drawable.queen);
                                //v.setBackgroundResource(R.drawable.queen);
                                buttons_state[i][j] = !buttons_state[i][j];
                                //mark_danger(i,j);
                                check_status(i,j);
                                if(total_queens==rowlimit)
                                    check_completed();
                            }
                        }
                    }
    }
    public void check_status(int m,int n){
       int i,j;
        for(i=0;i<rowlimit;i++){
            for(j=0;j<rowlimit;j++){
                if(buttons_state[i][j]){
                    if(!mark_status(i,j)){
                        button_set[i][j].setBackgroundDrawable(shape3);
                    }
                    else{
                        if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0)
                            button_set[i][j].setBackgroundDrawable(shapeDrawable);
                        else button_set[i][j].setBackgroundDrawable(shape2);
                    }
                }
            }
        }
    }
    public boolean mark_status(int m,int n){
        int i,j;
        //vertically
        for(i=0;i<rowlimit;i++){
            if(buttons_state[i][n]&&i!=m)
            {
                return false;
            }
        }
        //horizontally
        for(i=0;i<rowlimit;i++){
            if(buttons_state[m][i]&&i!=n){
                return false;
            }
        }
        for(i=m,j=n;i<rowlimit&&j<rowlimit;i++,j++) {
            if(buttons_state[i][j]&&(i!=m&&j!=n))
            {
               return false;
            }
        }
        for(i=m,j=n;i>=0&&j>=0;i--,j--)
        {if(buttons_state[i][j]&&(i!=m&&j!=n))
        {
            return false;
        }
        }
        for(i=m,j=n;i<rowlimit&&j>=0;i++,j--) {
            if(buttons_state[i][j]&&(i!=m&&j!=n))
            {
                return false;
            }
        }
        for(i=m,j=n;i>=0&&j<rowlimit;i--,j++)
        {
            if(buttons_state[i][j]&&(i!=m&&j!=n))
            {
               return false;
            }
        }
        return true;
    }
    public void check_completed(){
        boolean flag=true;
        int i,j;
        outerloop:for(i=0;i<rowlimit;i++)
            for(j=0;j<rowlimit;j++)
                if(buttons_state[i][j])
                    if(!mark_status(i,j))
                    {
                        flag=false;
                        break outerloop;
                    }
            if(flag) Toast.makeText(this, "Congrats", Toast.LENGTH_SHORT).show();
    }
}
