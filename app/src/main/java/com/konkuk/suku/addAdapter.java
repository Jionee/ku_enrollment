package com.konkuk.suku;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.konkuk.suku.R;

import java.util.ArrayList;

public class addAdapter extends RecyclerView.Adapter<addAdapter.MyViewHolder> {
    private ArrayList<classData> classes = new ArrayList<>();
    int gradeNumber = 0;
    public LinearLayout itemLayout;

    //각 text,image등을 연결
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textview_grade;
        public TextView textView_number;
        public TextView textView_name;
        public TextView textView_professor;
        public TextView textView_time;
        public TextView textView_empty;
        public TextView textView_current;

        public MyViewHolder(View v) {
            super(v);
            textview_grade = v.findViewById((R.id.textview_grade));
            textView_number = v.findViewById((R.id.textView_number));
            textView_name = v.findViewById(R.id.textView_name);
            textView_professor = v.findViewById(R.id.textView_professor);
            textView_time = v.findViewById(R.id.textView_time);
            textView_empty = v.findViewById(R.id.textView_empty);
            textView_current = v.findViewById(R.id.textView_current);
        }
    }

    public addAdapter(ArrayList<classData> classes,int gradeNumber) {
        this.classes = classes;
        this.gradeNumber = gradeNumber;
    }

    // 각 줄 open
    @Override
    public addAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        itemLayout = v.findViewById(R.id.layoutItem);
        return vh;
    }

    //데이터 세팅
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView_number.setText(classes.get(position).getNumbers());
        holder.textView_name.setText(classes.get(position).getName().substring(0, classes.get(position).getName().indexOf("(")));
        holder.textView_professor.setText(classes.get(position).getProfessor());
        holder.textView_time.setText(classes.get(position).getTime());

        int empt=0; int curr=0;
        if(classes.get(position).getGradeNumber()==0){
            empt = Integer.parseInt(classes.get(position).getEmpty());
            curr = Integer.parseInt(classes.get(position).getCurrent());
            holder.textview_grade.setText("전체");
        }
        else{
            empt = Integer.parseInt(classes.get(position).getGradeEmpty());
            curr = Integer.parseInt(classes.get(position).getGradeCurrent());
            holder.textview_grade.setText(classes.get(position).getGradeNumber()+"학년");
        }

        holder.textView_empty.setText(Integer.toString(curr-empt));
        holder.textView_current.setText(Integer.toString(empt)+"/"+Integer.toString(curr));

        SpannableString spannableString = new SpannableString(Integer.toString(curr-empt));
        if(curr-empt > 0){
            System.out.println("testTag : 자리잇음");
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4CAF50")), 0, spannableString.length(), 0);
            //itemLayout.setEnabled(false); //자리있음(초록색)
        }
        else{ //남으면 초록색
            System.out.println("testTag : 자리없음");
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#e0704e")), 0, spannableString.length(), 0);
            //itemLayout.setEnabled(true); //자리없음
        }
        holder.textView_empty.setText(spannableString);
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }
}
