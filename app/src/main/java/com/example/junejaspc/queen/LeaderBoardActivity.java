package com.example.junejaspc.queen;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderBoardActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<LeaderBoard_row>> {
    private String url = "http://geekyboy.16mb.com/leaderboard.php";
    RecyclerView recyclerView;
     static LeaderBoard_Adapter adapter;
    ArrayList<LeaderBoard_row> al;
    ProgressBar progressBar;
    long a, b;
    public static int level = 1;
    private int selected_level=1;
    int k=0;
    @Override
    protected void onResume() {
        super.onResume();
        load_data();
    }
public static void change(){adapter.notifyDataSetChanged();}
    public void load_data() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivity.getActiveNetworkInfo();
        if (network != null && network.isConnected()) {
            Log.e("netz", 1 + "");
            LoaderManager loaderManager = getSupportLoaderManager();
            al.clear();
            loaderManager.initLoader(k++, null, this).forceLoad();

            //loaderManager.initLoader(0, null, this).forceLoad();
            //loaderManager.restartLoader(0,null,this).forceLoad();

        } else {
            Toast.makeText(this, "Internet is not connected", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        al = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        adapter = new LeaderBoard_Adapter(al, this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        selected_level=level = getIntent().getIntExtra("level", 1);

    }

    @Override
    public Loader<ArrayList<LeaderBoard_row>> onCreateLoader(int id, Bundle args) {
        Log.e("netz", 2 + "");
        return new MyLoader(this, url, al,adapter);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<LeaderBoard_row>> loader, ArrayList<LeaderBoard_row> data) {
    Log.e("jetha","finisj");
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
        progressBar.setVisibility(View.GONE);
        Log.e("nepo", al.size()+"");
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
            al.clear();
            progressBar.setVisibility(View.VISIBLE);
            load_data();

        }
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
}
