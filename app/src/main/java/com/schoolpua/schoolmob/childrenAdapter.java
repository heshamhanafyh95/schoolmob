package com.schoolpua.schoolmob;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by hesha on 10/6/2017.
 */

public class childrenAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    private ArrayList<String> names,classes,images,studentIds;
    //private ArrayList<Uri>images;
    Context mContext;
    static String studentId;

    private static class ViewHolder {
        TextView name;
        TextView classs;
        ImageView pp;
    }

    public childrenAdapter(Context context, ArrayList<String> names, ArrayList<String> classes, ArrayList<String> images,ArrayList<String> studentIds) {
        super(context, R.layout.childview, names);
        this.names = names;
        this.classes = classes;
        this.mContext=context;
        this.images=images;
        this.studentIds=studentIds;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();

        switch (v.getId())
        {
            case R.id.childPic:
                /*Intent homeIntent=new Intent(mContext,profile.class);
                homeIntent.putExtra("studentId",studentIds.get(position));*/
                studentId=studentIds.get(position);
                mContext.startActivity(new Intent(mContext,profile.class));
                break;
        }
    }

    private int lastPosition = 0;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.childview, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.childName);
            viewHolder.classs = (TextView) convertView.findViewById(R.id.childClass);
            viewHolder.pp = (ImageView) convertView.findViewById(R.id.childPic);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.name.setText(names.get(position));
        viewHolder.classs.setText(classes.get(position));
        viewHolder.pp.setOnClickListener(this);
        viewHolder.pp.setTag(position);
        //Picasso.with(mContext).load(images.get(position)).into(viewHolder.pp);
        return convertView;
    }
}

