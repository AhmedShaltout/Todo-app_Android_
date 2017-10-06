package com.example.ubuntu.list_todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ubuntu.list_todo.controllers.DB;
import com.example.ubuntu.list_todo.controllers.Task;

public class Details extends AppCompatActivity {
    private DB db;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        id = Todo.editThis;
        db = new DB(this);
        final Task task = db.getTask(id);
        ((TextView) findViewById(R.id.title)).setText(task.getTitle());
        ((TextView) findViewById(R.id.body)).setText(task.getBody());
        ((TextView) findViewById(R.id.date)).setText(task.getDate());
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
