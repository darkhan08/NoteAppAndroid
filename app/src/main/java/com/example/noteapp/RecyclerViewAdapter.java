package com.example.noteapp;


import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList <Note> arrayList;
    private OnItemClickListner mListner;

    public interface OnItemClickListner{
        void onItemClick(int position);
        void onDeleteClick(int position);

    }
    public void setOnClickListner(OnItemClickListner listner){
        mListner = listner;
        System.out.println("2");
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView editText;
        public ImageView imageView;
        public TextView textView;
        LinearLayout linearLayout, toolbar;

        public RecyclerViewHolder(@NonNull final View itemView, final OnItemClickListner listner) {
            super(itemView);
            editText = itemView.findViewById(R.id.edit_text);
            imageView = itemView.findViewById(R.id.delet);
            textView = itemView.findViewById(R.id.date);
            linearLayout = itemView.findViewById(R.id.text_part);
            toolbar = itemView.findViewById(R.id.toolbar);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onDeleteClick(position);
                        }
                        System.out.println("1");
                    }else
                    System.out.println(getAdapterPosition());
                }
            });
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

    public RecyclerViewAdapter(ArrayList<Note> array) {
        arrayList = array;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_recycl_items,parent,false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view,mListner);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.editText.setText(arrayList.get(position).getText());
        holder.textView.setText(arrayList.get(position).getCurrent_date());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
