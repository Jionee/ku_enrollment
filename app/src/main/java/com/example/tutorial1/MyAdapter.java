package com.example.tutorial1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<classData> classDataset;
    private String gradeNumber;

    //각 text,image등을 연결
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_number;
        public TextView textView_name;
        public TextView textView_professor;
        public TextView textView_time;
        public TextView textView_empty;
        public TextView textView_current;

        public MyViewHolder(View v) {
            super(v);
            textView_number = v.findViewById((R.id.textView_number));
            textView_name = v.findViewById(R.id.textView_name);
            textView_professor = v.findViewById(R.id.textView_professor);
            textView_time = v.findViewById(R.id.textView_time);
            textView_empty = v.findViewById(R.id.textView_empty);
            textView_current = v.findViewById(R.id.textView_current);
        }
    }

    public MyAdapter(ArrayList<classData> classDataset,String gradeNumber) {
        this.classDataset = classDataset;
        this.gradeNumber = gradeNumber;
    }

    // 각 줄 open
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //데이터 세팅
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView_number.setText(classDataset.get(position).getNumbers());
        holder.textView_name.setText(classDataset.get(position).getName().substring(0, classDataset.get(position).getName().indexOf("(")));
        holder.textView_professor.setText(classDataset.get(position).getProfessor());
        holder.textView_time.setText(classDataset.get(position).getTime());

        int empt=0; int curr=0;
        switch(gradeNumber){
            case "0": //전체
                empt = Integer.parseInt(classDataset.get(position).getEmpty());
                curr = Integer.parseInt(classDataset.get(position).getCurrent());
                break;
            case "1": //1학년
                empt = Integer.parseInt(classDataset.get(position).getGradeEmpty().get(0));
                curr = Integer.parseInt(classDataset.get(position).getGradeCurrent().get(0));
                break;
            case "2": //2학년
                empt = Integer.parseInt(classDataset.get(position).getGradeEmpty().get(1));
                curr = Integer.parseInt(classDataset.get(position).getGradeCurrent().get(1));
                break;
            case "3": //3학년
                empt = Integer.parseInt(classDataset.get(position).getGradeEmpty().get(2));
                curr = Integer.parseInt(classDataset.get(position).getGradeCurrent().get(2));
                break;
            case "4": //4학년
                empt = Integer.parseInt(classDataset.get(position).getGradeEmpty().get(3));
                curr = Integer.parseInt(classDataset.get(position).getGradeCurrent().get(3));
                break;
            default: return;
        }
        holder.textView_empty.setText(Integer.toString(curr-empt));
        holder.textView_current.setText(Integer.toString(empt)+"/"+Integer.toString(curr));



    }

    @Override
    public int getItemCount() {
        return classDataset.size();
    }
}
