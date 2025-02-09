package com.example.maing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.maing.Domain.SetModel;
import com.example.maing.R;

import java.util.ArrayList;

public class SpinerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SetModel> arrayList;

    public SpinerAdapter(Context context, ArrayList<SetModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.spinner_item, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.titleTxt);
        TextView wordForTest = rootView.findViewById(R.id.wordForTest);

        txtName.setText(arrayList.get(i).getSetName());
        wordForTest.setText(String.valueOf(arrayList.get(i).getId_set()));

        return rootView;
    }
}