package com.example.junejaspc.queen;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by junejaspc on 3/17/2017.
 */
public class ImageAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

     public static int selected_avatar=0,avatar_id[]=new int[6];
    private boolean avatar_status[]=new boolean[6];
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
        avatar_status[0]=true;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageButton for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageButton imageView;

        if (convertView == null) {
            imageView = new ImageButton(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300,300));
            imageView.setScaleType(ImageButton.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageButton) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        imageView.setOnClickListener(this);
        try {
            int a = generateViewId();
            avatar_id[position] = a;
            imageView.setId(a);
            if(avatar_status[position])
                imageView.setBackgroundColor(Color.parseColor("#00bfff"));
            else  imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        catch (Exception e){
            Log.e("fuck","bushie");}
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.avatar1, R.drawable.avatar2,
            R.drawable.avatar3, R.drawable.avatar4,
            R.drawable.avatar5, R.drawable.avatar6,
    };

    @Override
    public void onClick(View v) {
        int a=v.getId();
        if(a==avatar_id[0])
            selected_avatar=0;
        else if(a==avatar_id[1])
            selected_avatar=1;
        else if(a==avatar_id[2])
            selected_avatar=2;
        else if(a==avatar_id[3])
            selected_avatar=3;
        else if(a==avatar_id[4])
            selected_avatar=4;
        else if(a==avatar_id[5])
            selected_avatar=5;
        for(int i=0;i<6;i++)avatar_status[i]=false;
        avatar_status[selected_avatar]=true;
        notifyDataSetChanged();
    }
}