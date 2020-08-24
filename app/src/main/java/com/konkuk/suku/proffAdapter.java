package com.konkuk.suku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.konkuk.suku.R;

import java.util.ArrayList;

public class proffAdapter extends RecyclerView.Adapter<proffAdapter.MyViewHolder> {
    private ArrayList<professorData> professorArr = new ArrayList<>();

    //각 text,image등을 연결
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ArrayList<professorData> professors;

        public TextView textView_name;
        public TextView textview_email;
        public TextView textview_room;
        public TextView textview_phone;

        public MyViewHolder(View v) {
            super(v);
            textView_name = v.findViewById(R.id.textView_name);
            textview_email = v.findViewById(R.id.textview_email);
            textview_room = v.findViewById(R.id.textview_room);
            textview_phone = v.findViewById(R.id.textview_phone);
        }
    }

    public proffAdapter(ArrayList<professorData> professorArr) {
        this.professorArr = professorArr;
    }

    // 각 줄 open
    @Override
    public proffAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_professor, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //데이터 세팅
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView_name.setText(professorArr.get(position).getName());
        holder.textview_email.setText(professorArr.get(position).getEmail());
        holder.textview_room.setText(professorArr.get(position).getRoom());
        holder.textview_phone.setText(professorArr.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return professorArr.size();
    }
}
