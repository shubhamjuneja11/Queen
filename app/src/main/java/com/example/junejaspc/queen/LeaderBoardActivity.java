package com.example.junejaspc.queen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<LeaderBoard_row>>{
    private String url="http://geekyboy.16mb.com/leaderboard.php";
    RecyclerView recyclerView;
    LeaderBoard_Adapter adapter;
    ArrayList<LeaderBoard_row> al;
    ProgressBar progressBar;

    @Override
    protected void onResume() {
        super.onResume();
        load_data();
    }
public void load_data(){
    ConnectivityManager connectivity=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo network=connectivity.getActiveNetworkInfo();
    if(network!=null&&network.isConnected())
    {
        Log.e("netz",1+"");
        LoaderManager loaderManager=getSupportLoaderManager();
        loaderManager.initLoader(1,null,this).forceLoad();

    }
    else{
        Toast.makeText(this, "Internet is not connected", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        al=new ArrayList<>();
        progressBar=(ProgressBar)findViewById(R.id.progressBar1);
        adapter=new LeaderBoard_Adapter(al);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public Loader<ArrayList<LeaderBoard_row>> onCreateLoader(int id, Bundle args) {
        Log.e("netz",2+"");
        return new MyLoader(this,url,al);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<LeaderBoard_row>> loader, ArrayList<LeaderBoard_row> data) {
        /*al=data;
        if(al==null)
            Log.e("netz","nazi");
        else Log.e("netz","nazinot");*/
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        Log.e("netz",3+"");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<LeaderBoard_row>> loader) {

    }


}
