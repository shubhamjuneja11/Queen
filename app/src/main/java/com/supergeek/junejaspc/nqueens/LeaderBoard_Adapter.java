package com.supergeek.junejaspc.nqueens;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by junejaspc on 3/13/2017.
 */

public class LeaderBoard_Adapter extends RecyclerView.Adapter<LeaderBoard_Adapter.MyViewHolder> {
    ArrayList<LeaderBoard_row> al;
    long mins,hrs,secs,time;
    String seconds,minutes,hours,milliseconds;
    SharedPreferences sharedPreferences;
    String username;
    Context context;
    int my_rank=-1;
    public Integer[] icons = {
            R.drawable.avatar1, R.drawable.avatar2,
            R.drawable.avatar3, R.drawable.avatar4,
            R.drawable.avatar5, R.drawable.avatar6,
            R.drawable.avatar7,R.drawable.avatar8,
            R.drawable.avatar9,R.drawable.avatar10,
            R.drawable.avatar11,R.drawable.avatar12,
            R.drawable.avatar13,R.drawable.avatar14,
            R.drawable.avatar15
    };
    public LeaderBoard_Adapter(ArrayList<LeaderBoard_row> al, Context context){
        this.al=al;
        this.context=context;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        username=sharedPreferences.getString("username","");
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        //view.setBackgroundResource(R.drawable.board_row_shape);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            LeaderBoard_row row = al.get(position);
            holder.username.setText(row.getUsername());
            holder.time.setText(settime(row.getTime()));
            holder.icon.setImageResource(icons[row.getIcon()]);
            holder.rank.setText(String.valueOf(position+1));
            if(username.equals(row.getUsername())) {
                holder.linearLayout.setBackgroundResource(R.drawable.board_row_shape_green);
                my_rank=position;
            }
            else{
                holder.linearLayout.setBackgroundResource(R.drawable.board_row_shape);
            }
            //holder.icon.setImageResource(icons[]);
        }
        catch (Exception e){
            e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username,time,rank;
        ImageView icon;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            try {
                linearLayout = (LinearLayout) itemView;
            }
            catch (Exception e){}
            username=(TextView)itemView.findViewById(R.id.username);
            time=(TextView)itemView.findViewById(R.id.mytime);
            icon=(ImageView)itemView.findViewById(R.id.usericon);
            rank=(TextView)itemView.findViewById(R.id.rank);
        }
    }
    public String settime(String t){ try {
        time = Long.valueOf(t);
    }
    catch (Exception e){}
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

        String b=(hours + ":" + minutes + ":" + seconds + "." + milliseconds);
        return b;
    }

}
