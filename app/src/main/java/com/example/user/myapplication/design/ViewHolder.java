package com.example.user.myapplication.design;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.myapplication.R;

class ViewHolder extends RecyclerView.ViewHolder {

    TextView text;
    Button btn;

    TextView cardName;
    TextView cardDate;
    TextView cardCat;

    ViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.sorts, parent, false));
        text = itemView.findViewById(R.id.category_sort);
        btn = itemView.findViewById(R.id.add_category);

        cardName = itemView.findViewById(R.id.card_title);
        cardDate = itemView.findViewById(R.id.card_date_text);
        cardCat = itemView.findViewById(R.id.card_category_text);

    }


}