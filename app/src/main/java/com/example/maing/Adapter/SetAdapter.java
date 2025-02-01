package com.example.maing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maing.Domain.LanguageModel;
import com.example.maing.Domain.SetModel;
import com.example.maing.R;

import java.util.ArrayList;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {
    Context context;
    ArrayList<SetModel> arrayList = new ArrayList<>();

    public SetAdapter(Context context, ArrayList<SetModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.language_item, parent, false);
        return new SetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetAdapter.ViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getSetName());

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
