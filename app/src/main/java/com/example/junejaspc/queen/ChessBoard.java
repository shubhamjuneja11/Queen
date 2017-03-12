package com.example.junejaspc.queen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ChessBoard extends AppCompatActivity implements View.OnClickListener {
GridLayout gridLayout;
    ImageButton button_set[][];
    int i,height,width,totalbuttons,rowlimit,j,total_queens;
    boolean decide;
    TextView time;
    DisplayMetrics displayMetrics;
    boolean buttons_state[][];
     Runnable startTimer;
    AlertDialog dialog;
    GradientDrawable shapeDrawable,shape2,shape3;
    String colors[]=new String[]{"#7333BF","#CB2A62","#A8AD1F","#D34B20","#649035","#359053",
                                 "#31B0AF","#2C65A9","#13EBE8","#969734","#ED04FC","#FC0488","#0480FC"   };

    private final int REFRESH_RATE = 1;
    private String hours,minutes,seconds,milliseconds;
    private long secs,mins,hrs,msecs;
    private long elapsedTime,startTime;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_board);
        rowlimit=getIntent().getIntExtra("count",4);
        time=(TextView)findViewById(R.id.mytime);
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
        //tick_tock();
        /*MobileAds.initialize(getApplicationContext(),"ca-app-pub-5750055305709604~2904023779");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest request =
                new AdRequest.Builder()
                .addTestDevice(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))
                        .build();

        mAdView.loadAd(request);*/
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
                                total_queens++;
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
            if(flag){stop_tick_tock(); //Toast.makeText(this, "Congrats", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                View view=getLayoutInflater().inflate(R.layout.congrats_dialog,null);
                builder.setView(view);
               dialog=builder.create();
                dialog.show();
            }
    }
    public void tick_tock(){
        try {
            elapsedTime=0;
            startTime = System.currentTimeMillis();
             startTimer= new Runnable() {
                public void run() {
                    elapsedTime = System.currentTimeMillis() - startTime;
                    updateTimer(elapsedTime);
                    mHandler.postDelayed(this, REFRESH_RATE);
                }
            };
        startTimer.run();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void stop_tick_tock(){
        mHandler.removeCallbacks(startTimer);
    }
    private void updateTimer (float time){
        secs = (long)(time/1000);
        mins = (long)((time/1000)/60);
        hrs = (long)(((time/1000)/60)/60);

		/* Convert the seconds to String
		 * and format to ensure it has
		 * a leading zero when required
		 */
        secs = secs % 60;
        seconds=String.valueOf(secs);
        if(secs == 0){
            seconds = "00";
        }
        if(secs <10 && secs > 0){
            seconds = "0"+seconds;
        }

		/* Convert the minutes to String and format the String */

        mins = mins % 60;
        minutes=String.valueOf(mins);
        if(mins == 0){
            minutes = "00";
        }
        if(mins <10 && mins > 0){
            minutes = "0"+minutes;
        }

    	/* Convert the hours to String and format the String */

        hours=String.valueOf(hrs);
        if(hrs == 0){
            hours = "00";
        }
        if(hrs <10 && hrs > 0){
            hours = "0"+hours;
        }
        milliseconds = String.valueOf((long)time);
        if(milliseconds.length()==2){
            milliseconds = "0"+milliseconds;
        }
        if(milliseconds.length()<=1){
            milliseconds = "00";
        }
        if(milliseconds.length()>=3)
        milliseconds = milliseconds.substring(milliseconds.length()-3, milliseconds.length()-1);

        this.time.setText(hours + ":" + minutes + ":" + seconds+"." + milliseconds);
    }
public void mynewgame(View view){
recreate();

    dialog.dismiss();
}
    public void anotherlevel(View view){
        Intent intent=new Intent(this,LevelActivity.class);
        startActivity(intent);
        finish();
    }
    public void onleaderboard(View view){
    Intent intent=new Intent(this,LeaderBoardActivity.class);
        intent.putExtra("count",rowlimit);
        startActivity(intent);
        finish();
    }
}
