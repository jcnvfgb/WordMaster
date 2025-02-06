package com.example.maing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maing.Activity.SettingUpWords;
import com.example.maing.Activity.WordManagement;
import com.example.maing.Domain.SetModel;
import com.example.maing.Domain.WordModel;
import com.example.maing.R;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    Context context;
    ArrayList<WordModel> arrayList = new ArrayList<>();

    public WordAdapter(Context context, ArrayList<WordModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.word_item, parent, false);
        return new WordAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.ViewHolder holder, int position) {
        holder.word.setText(arrayList.get(position).getWord());
        holder.wortTrans.setText(arrayList.get(position).getTranslation());
        holder.wordAct.setText(arrayList.get(position).getWordActivity());

        switch (position) {
            case 0:
                holder.background_img.setImageResource(R.drawable.bg_1);
                holder.layout.setBackgroundResource(R.drawable.list_background_1);
                break;
            case 1:
                holder.background_img.setImageResource(R.drawable.bg_2);
                holder.layout.setBackgroundResource(R.drawable.list_background_1);
                break;
            case 2:
                holder.background_img.setImageResource(R.drawable.bg_3);
                holder.layout.setBackgroundResource(R.drawable.list_background_1);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView word, wortTrans, wordAct;
        ImageView background_img;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            word = itemView.findViewById(R.id.word_titleTxt);
            wortTrans = itemView.findViewById(R.id.word_translation);
            wordAct = itemView.findViewById(R.id.word_activity);
            background_img = itemView.findViewById(R.id.word_background_img);
            layout = itemView.findViewById(R.id.word_layout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentPosition = getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        WordModel item = arrayList.get(currentPosition);
                        Intent intent = new Intent(context, WordManagement.class);
                        intent.putExtra("id_word", item.getId_word());
                        intent.putExtra("id_set", item.getId_set());
                        intent.putExtra("word", item.getWord());
                        intent.putExtra("translation", item.getTranslation());
                        intent.putExtra("wordActivity", item.getWordActivity());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
