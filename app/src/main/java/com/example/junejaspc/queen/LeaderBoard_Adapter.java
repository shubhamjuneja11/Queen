package com.example.junejaspc.queen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by junejaspc on 3/13/2017.
 */

public class LeaderBoard_Adapter extends RecyclerView.Adapter<LeaderBoard_Adapter.MyViewHolder> {
    ArrayList<LeaderBoard_row> al;
    public LeaderBoard_Adapter(ArrayList<LeaderBoard_row> al){
        this.al=al;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            LeaderBoard_row row = al.get(position);
            holder.username.setText(row.getUsername());
            holder.level.setText(row.getLevel());
            holder.time.setText(row.getTime());
        }
        catch (Exception e){e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username,level,time;

        public MyViewHolder(View itemView) {
            super(itemView);
            username=(TextView)itemView.findViewById(R.id.username);
            level=(TextView)itemView.findViewById(R.id.level);
            time=(TextView)itemView.findViewById(R.id.time);
        }
    }
}
