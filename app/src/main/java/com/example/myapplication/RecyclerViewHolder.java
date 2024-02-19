package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private final TextView textView;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.textViewItem);
    }
    public TextView getTextView() {
        return textView;
    }
}
