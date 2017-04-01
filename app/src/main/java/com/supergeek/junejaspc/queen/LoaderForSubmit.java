package com.supergeek.junejaspc.queen;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by junejaspc on 3/14/2017.
 */

public class LoaderForSubmit extends AsyncTaskLoader<LeaderBoard_row> {
    LeaderBoard_row data;
    private static String arrayname="success";
    String response;
    private String url1;
    URL url;
    public LoaderForSubmit(Context context,LeaderBoard_row data) throws MalformedURLException {
        super(context);
        this.data=data;
        url1=context.getResources().getString(R.string.savescore);
       url=new URL(url1);

    }

    @Override
    public LeaderBoard_row loadInBackground() {
        try {
            //response=MyLoader.makerequest(new URL(url));


            InputStream inputstream;
            HttpURLConnection connection;
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username",data.getUsername())
                    .appendQueryParameter("level", String.valueOf(data.getLevel()))
                    .appendQueryParameter("time", data.getTime());
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

            checkresponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String readfromstream(InputStream inputstream) throws IOException {

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
    public void checkresponse(){
        Log.e("bhimsh11122",response);
        /*try { Log.e("bhimsh11122",response);
          //  JSONObject object=new JSONObject(response);
           // JSONArray array=object.getJSONArray(arrayname);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("bhimsh",e.getMessage());
        }*/

    }
}
