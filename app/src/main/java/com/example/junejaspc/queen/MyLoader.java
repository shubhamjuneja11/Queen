package com.example.junejaspc.queen;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by junejaspc on 3/13/2017.
 */

public class MyLoader extends AsyncTaskLoader<ArrayList<LeaderBoard_row>> {
String url;
ArrayList<LeaderBoard_row>al;
    private static String arrayname="scores";
    private static String username="username";
    private static String level="level";
    private static String time="time";
    Context context;
    static String response;
    public MyLoader(Context context,String url,ArrayList<LeaderBoard_row> al) {
        super(context);
        this.url=url;
        this.context=context;
        this.al=al;
        Log.e("netz","4");

    }

    @Override
    public ArrayList<LeaderBoard_row> loadInBackground() {
        try {Log.e("netz","5");
            response=makerequest(new URL(url));
            getscores();
        } catch (IOException e) {
            Log.e("errorz","2");
            Log.e("errorz",e.getMessage());
            e.printStackTrace();
        }
        return al;
    }
    public static String makerequest(URL url1) throws IOException {
        Log.e("netz","6");
        String jsonResponse="";
        InputStream inputstream=null;
        if(url1==null)
            return jsonResponse;
        HttpURLConnection connection=null;
        connection=(HttpURLConnection)url1.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.setReadTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("level",String.valueOf(LeaderBoardActivity.level));
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
        jsonResponse=readfromstream(inputstream);
        return jsonResponse;
    }
    private  void getscores(){
        Log.e("netz","8");
      //  ArrayList<LeaderBoard_row> al=new ArrayList<>();
        try {Log.e("netz","10");
            JSONObject jsonObject = new JSONObject(response);
            Log.e("netz","11");
            Log.e("netz",response);
            JSONArray array=jsonObject.getJSONArray(arrayname);
            //JSONObject jsonObject1=jsonObject.getJSONObject(arrayname);
            Log.e("netz","12");
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject1=array.getJSONObject(i);
                String user=jsonObject1.getString(username);
                int level=jsonObject1.getInt(MyLoader.level);
                String time=jsonObject1.getString(MyLoader.time);
                Log.e("buzz",user); Log.e("buzz",level+""); Log.e("buzz",time);
                int icon=jsonObject1.getInt("avatar");
                LeaderBoard_row score=new LeaderBoard_row(user,level,time,icon);
                al.add(score);

            }
        }
        catch(Exception e){
            Log.e("netz",response);
            Log.e("netz",e.getMessage());
        }
    }
    private static String readfromstream(InputStream inputstream) throws IOException {
        Log.e("netz","7");
        StringBuilder string=new StringBuilder();
        if(inputstream!=null) {
            InputStreamReader inputreader = new InputStreamReader(inputstream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputreader);
            String line = reader.readLine();
            while (line != null) {
                string.append(line);
                line = reader.readLine();
            }
        }
        return string.toString();
    }
}

