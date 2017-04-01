package com.supergeek.junejaspc.nqueens;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.supergeek.junejaspc.nqueens.LevelActivity.completed;
import static com.supergeek.junejaspc.nqueens.LevelActivity.decide;

/**
 * Created by junejaspc on 3/7/2017.
 */

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {
    LevelActivity levelClass;
    List<LevelClass> levels;
    SharedPreferences sharedPreferences;

    boolean a;
    public LevelAdapter(LevelActivity levelClass,List<LevelClass> levels){
        this.levels=levels;
        this.levelClass=levelClass;
        decide=new Boolean[14];
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(levelClass);
        for(int i=1;i<=13;i++){
            a=sharedPreferences.getBoolean(String.valueOf(i),false);

            if(!a)
                decide[i]=true;
            else decide[i]=false;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.levelrow, parent, false);
        itemView.setOnClickListener(levelClass);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    LevelClass levelClass=levels.get(position);
    holder.level.setText(levelClass.getCount());
        holder.name.setText(levelClass.getLevelno());
        try {
            String numberOnly= levelClass.getLevelno().replaceAll("[^0-9]", "");
            int level = Integer.valueOf(numberOnly);
            if(decide[level]){
                holder.save.setAlpha(0.1f);

            }
            else {
                holder.save.setAlpha(1.0f);

            }
            if(completed[level])
                holder.done.setAlpha(0.1f);
            else holder.done.setAlpha(1.0f);
        }
        catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,level;
        ImageView save,done;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.levelname);
            level=(TextView)itemView.findViewById(R.id.levelcount);
            save=(ImageView)itemView.findViewById(R.id.saveicon);
            done=(ImageView)itemView.findViewById(R.id.done);
        }
    }
}
