package com.example.junejaspc.queen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by junejaspc on 3/7/2017.
 */

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {
    public TextView name,size;
    LevelActivity levelClass;
    List<LevelClass> levels;
    public LevelAdapter(LevelActivity levelClass,List<LevelClass> levels){
        this.levels=levels;
        this.levelClass=levelClass;
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
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,level;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.levelname);
            level=(TextView)itemView.findViewById(R.id.levelcount);

        }
    }
}
