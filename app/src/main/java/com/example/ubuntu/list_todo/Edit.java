package com.example.ubuntu.list_todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ubuntu.list_todo.controllers.DB;
import com.example.ubuntu.list_todo.controllers.Task;

public class Edit extends AppCompatActivity {
    private DB db;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db = new DB(this);
        id = Todo.editThis;
        final Task t = db.getTask(id);
        ((EditText)findViewById(R.id.edit_title)).setText(t.getTitle());
        ((EditText)findViewById(R.id.edit_body)).setText(t.getBody());
    }


    public void Save(View view){
        final String title = ((EditText) findViewById(R.id.edit_title)).getText().toString(),
                body = ((EditText) findViewById(R.id.edit_body)).getText().toString(),
                message;
        if(title.equals("") || body.equals("") ){
            message = "Please fill all the data";
        }else{
            if( db.updateTask(id, title,body) ) {
                message = "Added Successfully :)";
                onBackPressed();
            }else{
                message = "Something went wrong :(";
            }
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void Cancel(View view){
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
