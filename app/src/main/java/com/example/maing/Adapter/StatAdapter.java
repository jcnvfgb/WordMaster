package com.example.maing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maing.Domain.StatModel;
import com.example.maing.Domain.WordModel;
import com.example.maing.R;

import java.util.ArrayList;

public class StatAdapter extends RecyclerView.Adapter<StatAdapter.ViewHolder> {
    Context context;
    ArrayList<StatModel> arrayList = new ArrayList<>();
    public StatAdapter(Context context, ArrayList<StatModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public StatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stat_item, parent, false);
        return new StatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatAdapter.ViewHolder holder, int position) {
        holder.id_stat_view.setText(String.valueOf(arrayList.get(position).getId_stat()));
        holder.correctAnswView.setText(String.valueOf(arrayList.get(position).getCorrect()));
        holder.skipQuestV.setText(String.valueOf(arrayList.get(position).getSkipped()));
        holder.wrongAnswerView.setText(String.valueOf(arrayList.get(position).getIncorrect()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id_stat_view, correctAnswView, skipQuestV, wrongAnswerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_stat_view = itemView.findViewById(R.id.id_stat_view);
            correctAnswView = itemView.findViewById(R.id.correctAnswView);
            skipQuestV = itemView.findViewById(R.id.skipQuestV);
            wrongAnswerView = itemView.findViewById(R.id.wrongAnswerView);
        }
    }
}
