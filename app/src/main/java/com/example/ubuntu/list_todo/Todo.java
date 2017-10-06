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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ubuntu.list_todo.controllers.Task;
import com.example.ubuntu.list_todo.controllers.DB;


public class Todo extends Fragment {
    private LinearLayout cont;
    public static int editThis;
    private DB db;
    DataMover communicator;
    private LayoutInflater inflater;
    public Todo() {

    }

    public void setCommunicator(DataMover activity){
        this.communicator = activity;
    }


    @Override
    public View onCreateView(LayoutInflater infl, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater = infl;
        db = new DB(getActivity());
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        cont = view.findViewById(R.id.Todo);
        (view.findViewById(R.id.addNew) ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNew();
            }
        });
        getData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cont.removeAllViews();
        getData();
    }

    private void addNew(){
        startActivity(new Intent(getActivity(), Add.class));
    }

    private void getData() {
        //////////////// GET THE DATA ///////////////
        Task[] todo = db.getTodo();
        if(todo != null) {
            for (Task task:todo) {
                View inflatedView = inflater.inflate(R.layout.task, cont, false);
                LinearLayout layout = inflatedView.findViewById(R.id.task);
                setListeners(layout);
                ((TextView) layout.findViewById(R.id.idHolder)).setText(task.getId().toString());
                ((TextView) layout.findViewById(R.id.title)).setText(task.getTitle());
                ((TextView) layout.findViewById(R.id.date)).setText(task.getDate());
                cont.addView(layout);
            }
        }
    }

    private void setListeners(final LinearLayout layout) {
        final Button delete = layout.findViewById(R.id.delete),
                edit = layout.findViewById(R.id.edit),
                view = layout.findViewById(R.id.details);
        final Switch swi = layout.findViewById(R.id.markDone);

        swi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if( isChecked ){
                    LinearLayout task = (LinearLayout) buttonView.getParent();
                    Integer id = Integer.parseInt(((TextView)task.findViewById(R.id.idHolder)).getText().toString());
                    db.markDone(id);
                    cont.removeView(task);
                    ((TextView) task.findViewById(R.id.date)).setText(db.getTaskDate(id));
                    communicator.moveThisToFragment(task);
                }
            }
        });
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


        ////////edit///////
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout task = (LinearLayout) v.getParent().getParent();
                String id = ((TextView)task.findViewById(R.id.idHolder)).getText().toString();
                editThis  = Integer.parseInt(id);
                startActivity(new Intent(getActivity(),Edit.class));
            }
        });

        ///////view///////
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout task = (LinearLayout) v.getParent().getParent();
                String id = ((TextView)task.findViewById(R.id.idHolder)).getText().toString();
                editThis  = Integer.parseInt(id);
                startActivity(new Intent(getActivity(),Details.class));
            }
        });
    }

    interface DataMover {
        void moveThisToFragment(LinearLayout task);
    }

}
