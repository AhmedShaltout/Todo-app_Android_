package com.example.ubuntu.list_todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Add extends AppCompatActivity {
    private DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db = new DB(this);
    }

    public void Save(View view){
        final String title = ((EditText) findViewById(R.id.add_title)).getText().toString(),
                body = ((EditText) findViewById(R.id.add_body)).getText().toString(),
                message;
        if(title == null || body == null || title.equals("") || body.equals("") ){
            message = "Please fill all the data";
        }else{
            if( db.addTask(title,body) ) {
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
