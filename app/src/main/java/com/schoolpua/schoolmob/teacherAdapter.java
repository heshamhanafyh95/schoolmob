package com.schoolpua.schoolmob;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class teacherAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    private ArrayList<String> names,emails,pics;

    Context mContext;

    private static class ViewHolder {
        TextView name;
        TextView email;
        ImageView pics;
        RelativeLayout teacherBg;
    }

    public teacherAdapter(Context context, ArrayList<String> names, ArrayList<String> emails,ArrayList<String> pics) {
        super(context, R.layout.teacherview,emails);
        this.names = names;
        this.emails = emails;
        this.mContext=context;
        this.pics=pics;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();

        switch (v.getId())
        {
            case R.id.teacherBg:
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"+emails.get(position)));
                mContext.startActivity(Intent.createChooser(i,"Send Email to teacher"));
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
            convertView = inflater.inflate(R.layout.teacherview, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.teacherName);
            viewHolder.email = (TextView) convertView.findViewById(R.id.teacherClass);
            viewHolder.pics=(ImageView)convertView.findViewById(R.id.teacherProPic);
            viewHolder.teacherBg=(RelativeLayout)convertView.findViewById(R.id.teacherBg);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        byte[] decodedString = Base64.decode(pics.get(position), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        viewHolder.teacherBg.setOnClickListener(this);
        viewHolder.teacherBg.setTag(position);
        viewHolder.name.setText(names.get(position));
        viewHolder.email.setText(emails.get(position));
        viewHolder.pics.setImageBitmap(decodedByte);
        return convertView;
    }
}


