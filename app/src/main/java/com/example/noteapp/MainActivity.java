package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Note> list;
    DatabaseHelper helper;
    String TAG  = "States";
    Date date;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_veiw);
        helper = new DatabaseHelper(this);
        list = new ArrayList<>();
        list = helper.getNote();
        buildRecyclerView();

    }
    private void buildRecyclerView(){
        myAdapter = new RecyclerViewAdapter(list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
        onItemClickListner();
    }

    private void onItemClickListner(){
        myAdapter.setOnClickListner(new RecyclerViewAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                 changeText(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

        });
    }



    private void changeText(final int position){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_layout,null);
        final EditText editText = view.findViewById(R.id.edit_text_add);
        final String text = list.get(position).getText();
        editText.setText(text);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!editText.getText().toString().equals(text)){
                        date = new Date();
                        helper.Update(text,editText.getText().toString(),formatter.format(date));
                        list.clear();
                        for(int i = 0; i<helper.getNote().size();i++){
                            list.add(i,helper.getNote().get(i));
                        }
                        myAdapter.notifyDataSetChanged();
                }
                else {
                    dialog.dismiss();
                }
            }
        }).setNegativeButton("cansel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        myAdapter.notifyItemChanged(position);
    }

    private void removeItem(int position){
        helper.remove(list.get(position).getText());
        list.remove(position);
        myAdapter.notifyItemRemoved(position);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.my_menu){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View view = getLayoutInflater().inflate(R.layout.dialog_layout,null);
            final EditText editText = view.findViewById(R.id.edit_text_add);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(editText.getText().toString().replace(" ","").length()>0){
                        date = new Date();
                        Note note = new Note(editText.getText().toString(),formatter.format(date));
                        helper.insertNote(note);
                            list.add(0,helper.getNote().get(helper.getNote().size()-1));
                            myAdapter.notifyItemInserted(0);
                        }
                    }
            });
                builder.setNegativeButton("cansel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy()");
    }
}
