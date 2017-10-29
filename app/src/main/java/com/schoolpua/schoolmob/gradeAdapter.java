package com.schoolpua.schoolmob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LENOVO on 10/11/2017.
 */

public class gradeAdapter extends ArrayAdapter<String> {
    private ArrayList<ArrayList<String>> grade;
    private ArrayList<String> subject;
    Context mContext;

    private static class ViewHolder {
        TextView subject;
        TextView final_exam;
        TextView mid;
        TextView quizzes;
    }

    public gradeAdapter(Context context, ArrayList<ArrayList<String>> grade, ArrayList<String> subject) {
        super(context, R.layout.grades,subject);
        this.mContext=context;
        this.grade=grade;
        this.subject=subject;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.grades, parent, false);
            viewHolder.subject = (TextView) convertView.findViewById(R.id.gradeSubject);
            viewHolder.final_exam= (TextView) convertView.findViewById(R.id.gradefinal);
            viewHolder.mid= (TextView) convertView.findViewById(R.id.grademid);
            viewHolder.quizzes= (TextView) convertView.findViewById(R.id.gradequiz);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.subject.setText(subject.get(position));
        viewHolder.final_exam.setText(""+grade.get(position).get(0));
        viewHolder.mid.setText(""+grade.get(position).get(1));
        viewHolder.quizzes.setText(""+grade.get(position).get(2));

        return convertView;
    }

}
