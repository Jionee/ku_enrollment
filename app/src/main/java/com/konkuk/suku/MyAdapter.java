package com.konkuk.suku;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.konkuk.suku.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<classData> classDataset;
    private String gradeNumber;
    private boolean isEmpty;
    public LinearLayout itemLayout;
    public LinearLayout v;
    public ViewGroup vParent;
    public int viewtype ;
    public int num = 0;
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
        vParent = parent;
        viewtype = viewType;
        v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        itemLayout = v.findViewById(R.id.layoutItem);
        System.out.println("onCreateViewHolder");
        num++;
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
            if(!classDataset.get(position).getEmpty().equals("") && !classDataset.get(position).getCurrent().equals("")){
                if(gradeNumber.equals("0")){
                    empt = Integer.parseInt(classDataset.get(position).getEmpty().trim());
                    curr = Integer.parseInt(classDataset.get(position).getCurrent().trim());
                }
                else{
                    try{
                        empt = Integer.parseInt(classDataset.get(position).getGradeEmpty().trim());
                        curr = Integer.parseInt(classDataset.get(position).getGradeCurrent().trim());
                    }catch(NumberFormatException e) {
                        empt=0;
                        curr=0;
                    }

                }
            }
            holder.textView_empty.setText(Integer.toString(curr-empt));
            holder.textView_current.setText(Integer.toString(empt)+"/"+Integer.toString(curr));

            SpannableString spannableString = new SpannableString(Integer.toString(curr-empt));
            if(curr-empt > 0){ //자리있음
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4CAF50")), 0, spannableString.length(), 0);
                //itemLayout.setSelected(false);
            }
            else{ //자리없음
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#e0704e")), 0, spannableString.length(), 0);
                //itemLayout.setSelected(true);
            }

            //임시--지워야함--
            /*if(curr-empt<=85){
                System.out.println("testTag - "+classDataset.get(position).getName()+": "+Integer.toString(curr-empt) + " - 000");
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4CAF50")), 0, spannableString.length(), 0);
                //itemLayout.setEnabled(false); //자리있음(초록색)
            }
            else{
                System.out.println("testTag - "+classDataset.get(position).getName()+": "+Integer.toString(curr-empt)+ " - 111");
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#e0704e")), 0, spannableString.length(), 0);
                //itemLayout.setEnabled(true); //자리없음
            }*/
            System.out.println("testTag - "+itemLayout.isEnabled());
            holder.textView_empty.setText(spannableString);


    }


    @Override
    public int getItemCount() {
        return classDataset.size();
    }
}
