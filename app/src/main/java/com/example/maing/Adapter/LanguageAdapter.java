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

import com.bumptech.glide.Glide;
import com.example.maing.Activity.SettingUpSets;
import com.example.maing.Domain.LanguageModel;
import com.example.maing.R;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder>{
    Context context;
    ArrayList<LanguageModel> arrayList = new ArrayList<>();

    public LanguageAdapter(Context context, ArrayList<LanguageModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LanguageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.language_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.ViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getLanguage());
        //holder.quantityWord.setText("Word: " + arrayList.get(position).get_quantityWord());

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

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SettingUpSets.class);
                intent.putExtra("language", arrayList.get(position).getLanguage());
                intent.putExtra("id", arrayList.get(position).getId_language());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, quantityWord;
        ImageView background_img;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTxt);
            quantityWord = itemView.findViewById(R.id.quantityWord);
            background_img = itemView.findViewById(R.id.background_img);
            layout = itemView.findViewById(R.id.dictionary_layout);
        }
    }
}
