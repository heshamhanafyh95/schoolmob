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

public class attendAdapter extends ArrayAdapter<String>{

    private ArrayList<ArrayList<String>> attendance;
    private ArrayList<String>date;

    Context mContext;


    private static class ViewHolder {
        TextView date;
        TextView lesson1;
        TextView lesson2;
        TextView lesson3;
        TextView lesson4;
        TextView lesson5;
        TextView lesson6;
        TextView lesson7;
        TextView lesson8;
    }

    public attendAdapter(Context context, ArrayList<ArrayList<String>> attendance, ArrayList<String> date) {
        super(context, R.layout.attendance, date);
        this.attendance = attendance;
        this.mContext=context;
        this.date=date;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.attendance, parent, false);
            viewHolder.date = (TextView) convertView.findViewById(R.id.dateAttend);
            viewHolder.lesson1= (TextView) convertView.findViewById(R.id.lessonAttend1);
            viewHolder.lesson2= (TextView) convertView.findViewById(R.id.lessonAttend2);
            viewHolder.lesson3= (TextView) convertView.findViewById(R.id.lessonAttend3);
            viewHolder.lesson4= (TextView) convertView.findViewById(R.id.lessonAttend4);
            viewHolder.lesson5= (TextView) convertView.findViewById(R.id.lessonAttend5);
            viewHolder.lesson6= (TextView) convertView.findViewById(R.id.lessonAttend6);
            viewHolder.lesson7= (TextView) convertView.findViewById(R.id.lessonAttend7);
            viewHolder.lesson8= (TextView) convertView.findViewById(R.id.lessonAttend8);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.date.setText(date.get(position));
        viewHolder.lesson1.setText(String.valueOf(attendance.get(position).get(1)));
        viewHolder.lesson2.setText(String.valueOf(attendance.get(position).get(2)));
        viewHolder.lesson3.setText(String.valueOf(attendance.get(position).get(3)));
        viewHolder.lesson4.setText(String.valueOf(attendance.get(position).get(4)));
        viewHolder.lesson5.setText(String.valueOf(attendance.get(position).get(5)));
        viewHolder.lesson6.setText(String.valueOf(attendance.get(position).get(6)));
        viewHolder.lesson7.setText(String.valueOf(attendance.get(position).get(7)));
        viewHolder.lesson8.setText(String.valueOf(attendance.get(position).get(8)));

        return convertView;
    }
}

