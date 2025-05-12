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

public class LeftWordAdapter extends RecyclerView.Adapter<LeftWordAdapter.ViewHolder> {
    private List<WordPair> wordPairs;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public LeftWordAdapter(List<WordPair> wordPairs, OnItemClickListener listener) {
        this.wordPairs = wordPairs;
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
        WordPair pair = wordPairs.get(position);
        holder.tvWord.setText(pair.getOriginal());

        // Изменение внешнего вида при выборе и блокировке
        if (pair.isSelected()) {
            holder.tvWord.setBackgroundResource(R.drawable.selected_item_bg);
        } else if (pair.isMatched()) {
            holder.tvWord.setBackgroundResource(R.drawable.matched_item_bg);
        } else {
            holder.tvWord.setBackgroundResource(R.drawable.item_bg);
        }

        holder.tvWord.setEnabled(!pair.isMatched());
    }

    @Override
    public int getItemCount() { return wordPairs.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tvWord);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            });
        }
    }
}

