package com.schoolpua.schoolmob;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;




/**
 * Created by hesha on 10/6/2017.
 */

public class childrenAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    private ArrayList<String> names,classes,studentIds,pics;

    Context mContext;
    String phone;

    String studentId;

    private static class ViewHolder {
        TextView name;
        TextView classs;
        ImageView pics;
        RelativeLayout childBg;
    }

    public childrenAdapter(Context context, ArrayList<String> names, ArrayList<String> classes, ArrayList<String> studentIds,ArrayList<String> pics,String phone) {
        super(context, R.layout.childview,classes);
        this.names = names;
        this.classes = classes;
        this.mContext=context;
        this.studentIds=studentIds;
        this.phone=phone;
        this.pics=pics;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();

        switch (v.getId())
        {
            case R.id.childBg:
                studentId=studentIds.get(position);
                Intent i = new Intent(mContext, profile.class);
                i.putExtra("studentId", studentId);
                i.putExtra("phone", phone);
                mContext.startActivity(i);
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
            viewHolder.pics=(ImageView)convertView.findViewById(R.id.childProPic);
            viewHolder.childBg=(RelativeLayout)convertView.findViewById(R.id.childBg);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        byte[] decodedString = Base64.decode(pics.get(position), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        viewHolder.childBg.setOnClickListener(this);
        viewHolder.childBg.setTag(position);
        viewHolder.name.setText(names.get(position));
        viewHolder.classs.setText(classes.get(position));
        viewHolder.pics.setImageBitmap(decodedByte);
        return convertView;
    }
}


