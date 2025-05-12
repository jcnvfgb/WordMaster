package com.example.maing.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maing.Domain.WordPair;
import com.example.maing.R;

import java.util.List;

public class RightTranslationAdapter extends RecyclerView.Adapter<RightTranslationAdapter.ViewHolder> {
    private List<WordPair> translations;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public RightTranslationAdapter(List<WordPair> translations, OnItemClickListener listener) {
        this.translations = translations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordPair pair = translations.get(position);
        holder.tvTranslation.setText(pair.getTranslation());

        // Устанавливаем состояние элемента
        if (pair.isMatched()) {
            holder.tvTranslation.setBackgroundResource(R.drawable.matched_item_bg);
        } else {
            holder.tvTranslation.setBackgroundResource(R.drawable.item_bg);
        }

        holder.tvTranslation.setEnabled(!pair.isMatched());
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTranslation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTranslation = itemView.findViewById(R.id.tvWord);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            });
        }
    }
}
