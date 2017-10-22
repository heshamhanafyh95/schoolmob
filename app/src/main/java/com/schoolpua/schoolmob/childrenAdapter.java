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
import com.squareup.picasso.Picasso;




/**
 * Created by hesha on 10/6/2017.
 */

public class childrenAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    private ArrayList<String> names,classes,studentIds;
    private ArrayList<String> images;
    Context mContext;
    static String studentId;

    private static class ViewHolder {
        TextView name;
        TextView classs;
        ImageView pp;
    }

    public childrenAdapter(Context context,ArrayList<String> images, ArrayList<String> names, ArrayList<String> classes, ArrayList<String> studentIds) {
        super(context, R.layout.childview,classes);
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
                studentId=studentIds.get(position);
                mContext.startActivity(new Intent(mContext,profile.class));
                break;
        }
    }

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


        viewHolder.name.setText(names.get(position));
        viewHolder.classs.setText(classes.get(position));
        viewHolder.pp.setOnClickListener(this);
        viewHolder.pp.setTag(position);
        //viewHolder.pp.setImageURI(images1);
        Picasso.with(mContext).load(images.get(position)).into(viewHolder.pp);
        return convertView;
    }
}

