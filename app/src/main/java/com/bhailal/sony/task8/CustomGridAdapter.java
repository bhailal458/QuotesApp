package com.bhailal.sony.task8;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sony on 20-02-2017.
 */

public class CustomGridAdapter extends BaseAdapter {

    Context context;
    private Activity activity;
    ArrayList<Post> posts;
    private LayoutInflater inflater;
    int[] img;
    //Animation animation;





    public CustomGridAdapter(FirstActivity firstActivity, ArrayList<Post> postArrayList, int[] img) {

        this.activity = firstActivity;
        this.posts = postArrayList;
        this.img =img;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


//        Animation animation;
//        animation = AnimationUtils.loadAnimation(activity, R.anim.animation_move);

        if(inflater == null)
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView =inflater.inflate(R.layout.grid_row,null);


        TextView t1 = (TextView)convertView.findViewById(R.id.gridText);
        ImageView im = (ImageView)convertView.findViewById(R.id.imageview);


        Post m = posts.get(position);


        t1.setText(m.getName());
        im.setImageResource(img[position]);
        //im.startAnimation(animation);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity,SecondActivity.class);
                i.putExtra("myKey",posts.get(position).getId());
                activity.startActivity(i);
            }
        });
       return convertView;
    }
}
