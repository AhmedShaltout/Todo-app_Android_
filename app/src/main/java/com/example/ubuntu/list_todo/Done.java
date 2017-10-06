package com.example.ubuntu.list_todo;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ubuntu.list_todo.controllers.DB;
import com.example.ubuntu.list_todo.controllers.Task;


public class Done extends Fragment {

    private LayoutInflater inflater;
    private LinearLayout cont;
    private DB db;

    public Done() {
    }


    @Override
    public View onCreateView(LayoutInflater infl, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater = infl;
        db = new DB(getActivity());
        View view = inflater.inflate(R.layout.fragment_done, container, false);
        cont = view.findViewById(R.id.doneView);
        getData();
        return view;
    }

    private void getData() {
        Task[] done = db.getDone();
        if(done != null) {
            for (Task task:done) {
                addTask(task);
            }
        }
    }

    private void addTask(Task task){
        View inflatedView = inflater.inflate(R.layout.task, cont, false);
        LinearLayout layout = inflatedView.findViewById(R.id.task);
        setListeners(layout);
        ((TextView) layout.findViewById(R.id.idHolder)).setText(task.getId().toString());
        ((TextView) layout.findViewById(R.id.title)).setText(task.getTitle());
        ((TextView) layout.findViewById(R.id.date)).setText(task.getDate());
        cont.addView(layout);
    }
    public void addDone(LinearLayout task) {
        setListeners(task);
        cont.addView(task,0);
    }

    private void setListeners(final LinearLayout layout) {
        final Button delete = layout.findViewById(R.id.delete),
                edit = layout.findViewById(R.id.edit),
                view = layout.findViewById(R.id.details),
                done = layout.findViewById(R.id.markDone);
        done.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);

        ////////////Delete////////////
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                LinearLayout task = (LinearLayout) v.getParent().getParent();
                                String id = ((TextView)task.findViewById(R.id.idHolder)).getText().toString();
                                if(db.deleteData(Integer.parseInt(id))){
                                    cont.removeView(task);
                                }
                                break;
                        }
                    }
                };
                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });



        ///////view///////
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout task = (LinearLayout) v.getParent().getParent();
                String id = ((TextView)task.findViewById(R.id.idHolder)).getText().toString();
                Todo.editThis  = Integer.parseInt(id);
                startActivity(new Intent(getActivity(),Details.class));
            }
        });
    }
}
