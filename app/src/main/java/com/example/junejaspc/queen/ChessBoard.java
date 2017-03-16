package com.example.junejaspc.queen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.junejaspc.queen.LoaderForSubmit.readfromstream;

public class ChessBoard extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<LeaderBoard_row> {
    GridLayout gridLayout;
    ImageButton button_set[][];
    AlertDialog.Builder builder;
    int i, height, width, totalbuttons, rowlimit, j, total_queens;
    boolean decide,game_started;
    TextView time;
    DisplayMetrics displayMetrics;
    boolean buttons_state[][];
    Runnable startTimer;
    AlertDialog dialog,dialog1;
    boolean saved;
    View view2;
    GradientDrawable shapeDrawable, shape2, shape3;
    public static String colors[] = new String[]{"#7333BF", "#CB2A62", "#A8AD1F", "#D34B20", "#649035", "#359053",
            "#31B0AF", "#2C65A9", "#13EBE8", "#969734", "#ED04FC", "#FC0488", "#0480FC"};
    private String user_name, mytime,savedgame;
    int mylevel,avatar=0;
    private final int REFRESH_RATE = 100;
    private String hours, minutes, seconds, milliseconds;
    private long secs, mins, hrs;
    private long elapsedTime, startTime;
    private Handler mHandler = new Handler();
    SharedPreferences sharedPreferences;
    public static String savecompleted="complete";
    public static String savemilli="milli",auth_user_name,response;
    private long savedtime;
    SharedPreferences.Editor editor;
    private String url="http://geekyboy.16mb.com/saveusername.php";
    private URL myurl;
    @Override
    protected void onPause() {
        super.onPause();
        stop_tick_tock();
        editor.putLong("temptime",elapsedTime);
        editor.apply();
        Log.e("zoya",elapsedTime+"");
        //game_started=false;
    }

    @Override
    protected void onResume() {
        try {Log.e("popi","richard");
            super.onResume();
            if(game_started) {
                builder = new AlertDialog.Builder(this);
                Log.e("popi", "resume");
                View view = getLayoutInflater().inflate(R.layout.newdialog, null);
                builder.setView(view);
                dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
                savedtime=sharedPreferences.getLong("temptime",0);
                Log.e("zoya",savedtime+"");
            }


        }
        catch (Exception e){Log.e("popi","chandler");}
    }
    public void startgame(View view){
        dialog.dismiss();
        tick_tock();
        game_started=true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_board);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor =sharedPreferences.edit();
        game_started=true;

        try {
            myurl=new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        rowlimit = getIntent().getIntExtra("count", 4);
        //savedtime=0;
            saved = getIntent().getBooleanExtra("saved", false);
        if(saved)
            savedtime = sharedPreferences.getLong(colors[rowlimit - 4] + savemilli, 0);
        time = (TextView) findViewById(R.id.mytime);
        shapeDrawable = new GradientDrawable();
        shapeDrawable.setStroke(1, getResources().getColor(R.color.black));
        shapeDrawable.setColor(Color.parseColor(colors[rowlimit - 4]));
        shape2 = new GradientDrawable();
        shape3 = new GradientDrawable();
        shape2.setStroke(1, getResources().getColor(R.color.black));
        shape2.setColor(Color.parseColor("#FFFFFF"));
        shape3.setStroke(1, getResources().getColor(R.color.black));
        shape3.setColor(Color.parseColor("#FF0000"));
        gridLayout = (GridLayout) findViewById(R.id.grid);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        decide = true;
        total_queens = 0;
        buttons_state = new boolean[rowlimit][rowlimit];
        decideFactor();
        boardsetup();
        Log.e("popi","createdone");
        /*MobileAds.initialize(getApplicationContext(),"ca-app-pub-5750055305709604~2904023779");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest request =
                new AdRequest.Builder()
                .addTestDevice(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))
                        .build();

        mAdView.loadAd(request);*/
    }

    public void decideFactor() {
        totalbuttons = rowlimit * rowlimit;
        height = displayMetrics.heightPixels / rowlimit;
        width = displayMetrics.widthPixels / rowlimit;
        gridLayout.setRowCount(rowlimit);
        gridLayout.setColumnCount(rowlimit);
    }

    public void boardsetup() {

        button_set = new ImageButton[rowlimit][rowlimit];
        for (i = 0; i < rowlimit; i++) {
            for (j = 0; j < rowlimit; j++) {
                button_set[i][j] = new ImageButton(this);
                button_set[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                button_set[i][j].setLayoutParams(new LinearLayout.LayoutParams(width, width));
                if (decide) {
                    button_set[i][j].setBackgroundDrawable(shapeDrawable);
                } else {
                    button_set[i][j].setBackgroundDrawable(shape2);

                }
                decide = !decide;
                gridLayout.addView(button_set[i][j]);
                button_set[i][j].setOnClickListener(this);
            }
            if (rowlimit % 2 == 0)
                decide = !decide;
        }
        if(saved)
        checksavedgame();
    }
public void checksavedgame(){
        savedgame=sharedPreferences.getString(colors[rowlimit-4],"1");
    Log.e("rollz",savedgame+"");
       if(!savedgame.equals("1"))
           resumegame();
}
public void resumegame(){
    Log.e("rollz","s");
    try {
        int k = 0;
        char c[] = savedgame.toCharArray();
        for (int a = 0; a < rowlimit; a++) {
            for (int b = 0; b < rowlimit; b++) {
                if (c[k++] == '1') {
                    buttons_state[a][b] = true;
                    resumeonClick(button_set[a][b]);
                }
                else buttons_state[a][b] = false;
            }
        }
        total_queens=sharedPreferences.getInt(colors[rowlimit-4]+"queen",0);
    }
    catch (Exception e){
        Log.e("rolz","rockandroll");
    }
}
    @Override
    public void onClick(View v) {
        for (i = 0; i < rowlimit; i++)
            for (j = 0; j < rowlimit; j++)
                if (button_set[i][j] == v) {

                    decideButtonStatus(i,j,v);
                }
    }
    public void resumeonClick(View v){
        for (i = 0; i < rowlimit; i++)
            for (j = 0; j < rowlimit; j++)
                if (button_set[i][j] == v) {

                    resumedecidebuttonstatus(i,j,v);
                }
    }
    public void resumedecidebuttonstatus(int i,int j,View v){
        if (!buttons_state[i][j]) {

            if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0)
            { v.setBackgroundDrawable(shapeDrawable);
            }
            else {v.setBackgroundDrawable(shape2);
            }
            ((ImageButton) v).setImageResource(0);
            total_queens--;
            //buttons_state[i][j] = !buttons_state[i][j];
            check_status(i, j);
        } else {
            Log.e("rollz","aao");


            if (total_queens < rowlimit) {Log.e("rollz","mataao");
                total_queens++;
                ((ImageButton) v).setImageResource(R.drawable.queen);
               // buttons_state[i][j] = !buttons_state[i][j];
                check_status(i, j);
                if (total_queens == rowlimit)
                    check_completed();
            }
        }
    }
    public void decideButtonStatus(int i,int j,View v){
        if (buttons_state[i][j]) {        Log.e("rollz","pppo");

            if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0)
            { v.setBackgroundDrawable(shapeDrawable);        Log.e("rollz","ola");
            }
            else {v.setBackgroundDrawable(shape2);        Log.e("rollz","aaao");
            }
            ((ImageButton) v).setImageResource(0);
            total_queens--;
            buttons_state[i][j] = !buttons_state[i][j];
            check_status(i, j);
        } else {
            Log.e("rollz","aao");


            if (total_queens < rowlimit) {Log.e("rollz","mataao");
                total_queens++;
                ((ImageButton) v).setImageResource(R.drawable.queen);
                buttons_state[i][j] = !buttons_state[i][j];
                check_status(i, j);
                if (total_queens == rowlimit)
                    check_completed();
            }
        }
    }

    public void check_status(int m, int n) {
        int i, j;
        for (i = 0; i < rowlimit; i++) {
            for (j = 0; j < rowlimit; j++) {
                if (buttons_state[i][j]) {
                    if (!mark_status(i, j)) {
                        button_set[i][j].setBackgroundDrawable(shape3);
                    } else {
                        if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0)
                            button_set[i][j].setBackgroundDrawable(shapeDrawable);
                        else button_set[i][j].setBackgroundDrawable(shape2);
                    }
                }
            }
        }
    }

    public boolean mark_status(int m, int n) {
        int i, j;
        //vertically
        for (i = 0; i < rowlimit; i++) {
            if (buttons_state[i][n] && i != m) {
                return false;
            }
        }
        //horizontally
        for (i = 0; i < rowlimit; i++) {
            if (buttons_state[m][i] && i != n) {
                return false;
            }
        }
        for (i = m, j = n; i < rowlimit && j < rowlimit; i++, j++) {
            if (buttons_state[i][j] && (i != m && j != n)) {
                return false;
            }
        }
        for (i = m, j = n; i >= 0 && j >= 0; i--, j--) {
            if (buttons_state[i][j] && (i != m && j != n)) {
                return false;
            }
        }
        for (i = m, j = n; i < rowlimit && j >= 0; i++, j--) {
            if (buttons_state[i][j] && (i != m && j != n)) {
                return false;
            }
        }
        for (i = m, j = n; i >= 0 && j < rowlimit; i--, j++) {
            if (buttons_state[i][j] && (i != m && j != n)) {
                return false;
            }
        }
        return true;
    }

    public boolean check_completed() {
        String s="";
        for(i=0;i<rowlimit;i++)
            for(j=0;j<rowlimit;j++)
                if(buttons_state[i][j])s+="1";
                else s+="0";
        Log.e("bushi",s);
        boolean flag = true;
        int i, j;
        Log.e("bam",rowlimit+"");
        outerloop:
        for (i = 0; i < rowlimit; i++) {
            for (j = 0; j < rowlimit; j++) {
                if (buttons_state[i][j])
                    if (!mark_status(i, j)) {
                        Log.e("bam", "raaz");
                        return false;
                        /*flag = false;
                        break outerloop;*/
                    } else {
                        Log.e("bam", "bhole");
                    }
            }
        }
        if (flag) {
            stop_tick_tock();
            editor.putBoolean(colors[rowlimit-4]+savecompleted,true);
            editor.apply();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.congrats_dialog, null);
            TextView tv1, tv2;
            tv1 = (TextView) view.findViewById(R.id.complete_time);
            tv2 = (TextView) findViewById(R.id.mytime);
            tv1.setText(tv2.getText().toString());
            builder.setView(view);
            dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
            game_started=false;
            return true;
        }
        return false;
    }

    public void tick_tock() {
        try {
            elapsedTime = 0;
            startTime = System.currentTimeMillis();
            startTimer = new Runnable() {
                public void run() {
                    elapsedTime = System.currentTimeMillis() - startTime+savedtime;
                    updateTimer(elapsedTime);
                    mHandler.postDelayed(this, REFRESH_RATE);
                }
            };
            startTimer.run();
        } catch (Exception e) {
            Log.e("rolz","rockandroll");
            e.printStackTrace();
        }
    }

    public void stop_tick_tock() {
        mHandler.removeCallbacks(startTimer);
    }

    private void updateTimer(float time) {
        secs = (long) (time / 1000);
        mins = (long) ((time / 1000) / 60);
        hrs = (long) (((time / 1000) / 60) / 60);

		/* Convert the seconds to String
         * and format to ensure it has
		 * a leading zero when required
		 */
        secs = secs % 60;
        seconds = String.valueOf(secs);
        if (secs == 0) {
            seconds = "00";
        }
        if (secs < 10 && secs > 0) {
            seconds = "0" + seconds;
        }

		/* Convert the minutes to String and format the String */

        mins = mins % 60;
        minutes = String.valueOf(mins);
        if (mins == 0) {
            minutes = "00";
        }
        if (mins < 10 && mins > 0) {
            minutes = "0" + minutes;
        }

    	/* Convert the hours to String and format the String */

        hours = String.valueOf(hrs);
        if (hrs == 0) {
            hours = "00";
        }
        if (hrs < 10 && hrs > 0) {
            hours = "0" + hours;
        }
        milliseconds = String.valueOf((long) time);
        if (milliseconds.length() == 2) {
            milliseconds = "0" + milliseconds;
        }
        if (milliseconds.length() <= 1) {
            milliseconds = "00";
        }
        if (milliseconds.length() >= 3)
            milliseconds = milliseconds.substring(milliseconds.length() - 3, milliseconds.length() - 1);

        this.time.setText(hours + ":" + minutes + ":" + seconds + "." + milliseconds);
    }

    public void mynewgame(View view) {
        try {
            Intent intent = getIntent();
            intent.putExtra("count", rowlimit);
            intent.putExtra("saved", false);
            startActivity(intent);
            finish();
        }
        catch (Exception e){}

    }

    public void anotherlevel(View view) {
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
        finish();
    }

    public void onleaderboard(View view) {
        //putonBoard();
       /* Intent intent = new Intent(this, LeaderBoardActivity.class);
        intent.putExtra("count", rowlimit);
        intent.putExtra("submit",true);
        startActivity(intent);
        finish();*/
       dialog.dismiss();
       check_user();
    }
public void back(View view){
   goback();
}
public void goback(){
    try {Log.e("going","back");
        Intent upIntent=NavUtils.getParentActivityIntent(this);;
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {

            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {

            NavUtils.navigateUpTo(this, upIntent);
        }

    } catch (Exception e) {Log.e("going","no");
    }
}
    @Override
    public void onBackPressed() {
        Log.e("zebra","a");
        stop_tick_tock();
   /* if(game_started){ Log.e("zebra","b");
        dialog.dismiss();
        super.onBackPressed();
        return;
    }Log.e("zebra","c");*/
        if(total_queens==rowlimit&&check_completed()) {
            Log.e("golden","crow");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog, null);
            builder.setView(view);
            dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }
        else {
            dialog.dismiss();
            goback();
           // super.onBackPressed();
        }
    }

    public void yessave(View view) {
        dialog.dismiss();

        int y,z;
        String s="";
        for(y=0;y<rowlimit;y++){
            for(z=0;z<rowlimit;z++){
                if(buttons_state[y][z])s+='1';
                else s+='0';
            }
        }
        LevelActivity.decide[rowlimit-3]=true;
        editor.putString(colors[rowlimit-4],s);
        editor.putInt(colors[rowlimit-4]+"queen",total_queens);
        editor.putBoolean(String.valueOf(rowlimit-3),true);
        editor.putLong(colors[rowlimit-4]+savemilli,elapsedTime);
        editor.apply();
        super.onBackPressed();
    }

    public void nosave(View view) {
        dialog.dismiss();
        super.onBackPressed();
    }

    public void putonBoard() {
        mytime = time.getText().toString();
        user_name = "shubh";
        mylevel = 1;
        senddata();
    }
    public void senddata(){
        ConnectivityManager connectivity=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network=connectivity.getActiveNetworkInfo();
        if(network!=null&&network.isConnected())
        {
            if(check_user()){
            LoaderManager loaderManager=getSupportLoaderManager();
            loaderManager.initLoader(1,null,this).forceLoad();
            }

        }
    }
    public void senddata(View view){
        ConnectivityManager connectivity=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network=connectivity.getActiveNetworkInfo();
        if(network!=null&&network.isConnected())
        {
            LoaderManager loaderManager=getSupportLoaderManager();
            loaderManager.initLoader(1,null,this).forceLoad();

        }
    }
    public boolean check_user(){
        String username=sharedPreferences.getString("username","user");
        if(!username.equals("user"))
            return true;
        else{
            if(createusername())
                if(createavatar())
                    return true;

        }
        return false;
    }
    public boolean createusername(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        view2=getLayoutInflater().inflate(R.layout.usernamedialog,null);
        builder.setView(view2);
         dialog1=builder.create();
        dialog1.show();
        return true;
    }
    public void submit(View view){
        try {
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
            EditText user = (EditText) view2.findViewById(R.id.username);
            auth_user_name = user.getText().toString();
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo network = connectivity.getActiveNetworkInfo();
            if (network != null && network.isConnected()) {
                new MyAsyncClass().execute();
                dialog1.dismiss();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("bvp",e.getMessage());
            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();}
    }
    public boolean createavatar(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.avatardialog,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        return true;
    }
    @Override
    public Loader<LeaderBoard_row> onCreateLoader(int id, Bundle args) {
        try { Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
            user_name=sharedPreferences.getString("username","user");
            mylevel=rowlimit-3;
            mytime=String.valueOf(elapsedTime);
            return new LoaderForSubmit(this,new LeaderBoard_row(user_name,mylevel,mytime));
        } catch (MalformedURLException e) {
            Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
            Log.e("rolz","rockandroll");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<LeaderBoard_row> loader, LeaderBoard_row data) {

    }

    @Override
    public void onLoaderReset(Loader<LeaderBoard_row> loader) {

    }


    private class MyAsyncClass extends AsyncTask<Void,Void,Boolean>{


        @Override
        protected Boolean doInBackground(Void... params) {
            connect(myurl);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
    public void connect(URL url){
    try{
        InputStream inputstream=null;
        HttpURLConnection connection=null;
        connection=(HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(10000);
        connection.setReadTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("username",auth_user_name)
                .appendQueryParameter("avatar",String.valueOf(avatar));

        String query = builder.build().getEncodedQuery();

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(query);
        writer.flush();
        writer.close();
        os.close();

        connection.connect();

        inputstream=connection.getInputStream();
        response=readfromstream(inputstream);
        Log.e("netz",response);
        checkresponse();
    } catch (IOException e) {
        Log.e("bvp",e.getMessage());
        e.printStackTrace();
    }
    }
    public void checkresponse(){
        try {Log.e("response",response);
            JSONObject object=new JSONObject(response);
            int m=object.getInt("success");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("bvp",e.getMessage());
        }
    }
}
