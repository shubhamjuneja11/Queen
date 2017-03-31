package com.supergeek.junejaspc.queen;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderBoardActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<LeaderBoard_row>>,SwipeRefreshLayout.OnRefreshListener {
    private String url = getResources().getString(R.string.leaderboard);
    RecyclerView recyclerView;
     static LeaderBoard_Adapter adapter;
    ArrayList<LeaderBoard_row> al;
    long a, b;
    public static int level = 1;
    private int selected_level=1;
    SwipeRefreshLayout swipe;
    private AdView mAdView;
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        load_data();
    }
    public void load_data() {
        swipe.setRefreshing(true);
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivity.getActiveNetworkInfo();
        if (network != null && network.isConnected()) {

            LoaderManager loaderManager = getSupportLoaderManager();
            al.clear();
            adapter.notifyDataSetChanged();
            loaderManager.restartLoader(0,null,this).forceLoad();
        } else {
            Toast.makeText(this, "Internet is not connected", Toast.LENGTH_SHORT).show();
            swipe.setRefreshing(false);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        getSupportActionBar().setTitle("LeaderBoard");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("57580B9311901ECCFBBED8BC41E8E74F")
                .build();
        mAdView.loadAd(adRequest);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        swipe=(SwipeRefreshLayout)findViewById(R.id.swipe);
        al = new ArrayList<>();
        adapter = new LeaderBoard_Adapter(al, this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        selected_level=level = getIntent().getIntExtra("level", 1);
        swipe.setOnRefreshListener(this);
        mAdView = (AdView) findViewById(R.id.adView);
        /*AdRequest adRequest = new AdRequest.Builder()
                .build();*/




    }

    @Override
    public Loader<ArrayList<LeaderBoard_row>> onCreateLoader(int id, Bundle args) {

        return new MyLoader(this, url, al,adapter);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<LeaderBoard_row>> loader, ArrayList<LeaderBoard_row> data) {
        Collections.sort(al, new Comparator<LeaderBoard_row>() {
            @Override
            public int compare(LeaderBoard_row o1, LeaderBoard_row o2) {
                a = Long.valueOf(o1.getTime());
                b = Long.valueOf(o2.getTime());
                if (a < b) return -1;
                else return 1;
            }
        });
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(false);
       /* try {
            if (adapter.my_rank != -1)
                recyclerView.scrollToPosition(adapter.my_rank);
        }catch (Exception e){}*/
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<LeaderBoard_row>> loader) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.leadermenu, menu);
        MenuItem item = menu.findItem(R.id.level);
        String s = "level ";
        s += selected_level;
        item.setTitle(s);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case R.id.level1:
                selected_level = 1;
                break;
            case R.id.level2:
                selected_level = 2;
                break;
            case R.id.level3:
                selected_level = 3;
                break;
            case R.id.level4:
                selected_level = 4;
                break;
            case R.id.level5:
                selected_level = 5;
                break;
            case R.id.level6:
                selected_level = 6;
                break;
            case R.id.level7:
                selected_level = 7;
                break;
            case R.id.level8:
                selected_level = 8;
                break;
            case R.id.level9:
                selected_level = 9;
                break;
            case R.id.level10:
                selected_level = 10;
                break;
            case R.id.level11:
                selected_level = 11;
                break;
            case R.id.level12:
                selected_level = 12;
                break;
            case R.id.level13:
                selected_level = 13;
                break;
        }
        invalidateOptionsMenu();
        reloadData();
        return true;
    }
    public void reloadData(){
        if(selected_level!=level){
            level=selected_level;
        }
        al.clear();
        adapter.notifyDataSetChanged();
        load_data();
    }

    @Override
    public void onBackPressed() {
        Intent upIntent= NavUtils.getParentActivityIntent(this);;
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {

            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {

            NavUtils.navigateUpTo(this, upIntent);
        }

    }

    @Override
    public void onRefresh() {
        reloadData();
    }
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
