package com.supergeek.junejaspc.nqueens;

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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderBoardActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<LeaderBoard_row>>,SwipeRefreshLayout.OnRefreshListener {
    private String url;
    RecyclerView recyclerView;
     static LeaderBoard_Adapter adapter;
    ArrayList<LeaderBoard_row> al;
    long a, b;
    public static int level = 1;
    private int selected_level=1;
    int visibleItemCount,totalItemCount,pastVisiblesItems;
    LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout swipe;
    static boolean loading=true;
    static int count=0;
    @Override
    protected void onResume() {
        super.onResume();
        count=0;
        load_data();
    }
    public void load_data() {
        swipe.setRefreshing(true);
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivity.getActiveNetworkInfo();
        if (network != null && network.isConnected()) {

            LoaderManager loaderManager = getSupportLoaderManager();
          //  al.clear();

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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
        }
        url = "http://geekyboy.16mb.com/leader.php";
        getSupportActionBar().setTitle("LeaderBoard");
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        swipe=(SwipeRefreshLayout)findViewById(R.id.swipe);
        al = new ArrayList<>();
        adapter = new LeaderBoard_Adapter(al, this);
        recyclerView.setAdapter(adapter);
         mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        selected_level=level = getIntent().getIntExtra("level", 1);
        swipe.setOnRefreshListener(this);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount =mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if(loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            Log.e("abcd", "page");
                            loading = false;
                            reloadData();
                        }
                    }

                }
            }
        });

    }

    @Override
    public Loader<ArrayList<LeaderBoard_row>> onCreateLoader(int id, Bundle args) {

        return new MyLoader(this, url, al,adapter,count);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<LeaderBoard_row>> loader, ArrayList<LeaderBoard_row> data) {
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(false);
        loading=true;
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
            case R.id.level14:
                selected_level = 14;
                break;
            case R.id.level15:
                selected_level = 15;
                break;
            case R.id.level16:
                selected_level = 16;
                break;
            case R.id.level17:
                selected_level = 17;
                break;
            case R.id.level18:
                selected_level = 18;
                break;
            case R.id.level19:
                selected_level = 19;
                break;
            case R.id.level20:
                selected_level = 20;
                break;
            case R.id.level21:
                selected_level = 21;
                break;
            case R.id.level22:
                selected_level = 22;
                break;
        }
        invalidateOptionsMenu();
        reloadData();
        return true;
    }
    public void reloadData(){
        if(selected_level!=level){
            level=selected_level;
            al.clear();
            count=0;
        }
        else count++;
        Log.e("abcd",count+"");
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
        level=selected_level;
        al.clear();
        count=0;
        load_data();
    }
}
